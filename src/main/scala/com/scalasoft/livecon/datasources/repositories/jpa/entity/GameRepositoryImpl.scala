/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.repositories.jpa.entity


import javax.persistence.{PersistenceContext, EntityManager}
import com.scalasoft.livecon.beans.json.request.searchgames.SearchGamesRequestBean
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.hibernate.Session
import org.hibernate.jdbc.Work
import java.sql.Connection
import com.scalasoft.scalalib.persistence.db.jdbc.JDBCDataSource
import scala.collection.mutable.ListBuffer
import com.scalasoft.scalalib.CoreImplicits._
import com.scalasoft.livecon.beans.json.response.searchgames.{PlayerResp, SearchGamesResponseBean, ServerResp, GameResp}
import com.scalasoft.scalalib.CoreExcCodes
import com.scalasoft.scalalib.DataImplicits._

/**
 * JPA Game repository. In reality, it uses jdbc. The query is complicated and needs an example so here it is:
 *
{{{
    select
    g.*,
    s.archived,
    s.diplo,
    s.dir,
    s.name,
    s.num_players,
    s.unique_name,
    d.full_path dcrec
from
    game g,
    server s,
    dcrec d
where
    g.server_id = s.id
        and g.id = dcrec.game_id
        and s.id in (1, 2, 3, 4, 5, 6)
        and g.id in (select
            s1.id
        from
            (select
                g1.id
            from
                game g1, game_has_player ghp, ingame_name ign, territory t
            where
                g1.id = ghp.game_id
                    and ghp.id = ign.game_has_player_id
                    and ghp.territory_id = t.id
                    and ign.name = 'nooblet 1'
					and t.name = 'NA') s1
        where
            s1.id in (select
                    g1.id
                from
                    game g1,
                    game_has_player ghp,
                    ingame_name ign,
                    territory t
                where
                    g1.id = ghp.game_id
                        and ghp.id = ign.game_has_player_id
                        and ghp.territory_id = t.id
                        and ign.name = 'nooblet 4')
                and s1.id in (select
                    g1.id
                from
                    game g1,
                    game_has_player ghp,
                    ingame_name ign,
                    territory t
                where
                    g1.id = ghp.game_id
                        and ghp.id = ign.game_has_player_id
                        and ghp.territory_id = t.id
                        and ign.name = 'nooblet 3'))
}}}
 *
 */
@Repository
class GameRepositoryImpl extends GameRepository {

  @PersistenceContext
  var em: EntityManager = _

  @Transactional
  override def findBySearchBean(searchBean: SearchGamesRequestBean) = {

    /*
     * Work with the jdbc connection, this query is too complicated for jpa (imo).
     * Inside joke: find a jpa version for this and you get 10 bonus noob tears.
     */
    val session = em.unwrap(classOf[Session])
    val responseBean = new SearchGamesResponseBean

    session.doWork(new Work() {

      override def execute(conn: Connection) {

        // use the scalalib jdbc api
        val db = new JDBCDataSource(conn)

        // Decide what query to use
        val players = searchBean.players
        val searchPlayers = !(players == null || players.length == 0)

        val query = new StringBuilder()
          .append(" select g.*, s.archived, s.diplo, s.dir, s.name, s.num_players, s.unique_name, d.full_path dcrec ")
          .append(" from game g, server s, dcrec d ")
          .append(" where g.server_id = s.id and g.id = d.game_id ")

        // Make a number of ? for each server
        val serversQm = searchBean.servers.map(_ => "?").mkString(",")
        query.append(" and s.id in ( ").append(serversQm).append(" ) ")

        // now only if there is player filtering, add players to the mix
        if (searchPlayers) {
          query.append(" and g.id in (")

          var firstTime = true
          var firstTimeWhere = true
          for (player <- players) {
            if (firstTime) {
              firstTime = false
              query.append(" select s1.id from ( ")
                .append(" select g1.id from ")
                .append(" game g1, game_has_player ghp, ingame_name ign, territory t ")
                .append(" where g1.id = ghp.game_id ")
                .append(" and ghp.id = ign.game_has_player_id ")
                .append(" and ghp.territory_id = t.id ")
                .append(" and ign.name = ? ")

              // only if there is territory filtering
              if (player.territory != "---")
                query.append(" and t.name = ? ")

              query.append(" ) s1 ")
            } else {
              if (firstTimeWhere) {
                firstTimeWhere = false
                query.append(" where ")
              } else
                query.append(" and ")

              query.append("s1.id in ( ")
                .append(" select g1.id from ")
                .append(" game g1, game_has_player ghp, ingame_name ign, territory t ")
                .append(" where g1.id = ghp.game_id ")
                .append(" and ghp.id = ign.game_has_player_id ")
                .append(" and ghp.territory_id = t.id ")
                .append(" and ign.name = ? ")

              // only if there is territory filtering
              if (player.territory != "---")
                query.append(" and t.name = ? ")

              query.append(" ) ")
            }
          }

          query.append(" )")
        }


        // Create the parameter list
        val params = new ListBuffer[Any]
        searchBean.servers.foreach(s => params += s.toInt)

        if (searchPlayers) {
          for (player <- players) {
            params += player.name
            if (player.territory != "---")
              params += player.territory
          }
        }

        // Before anything, find out pagination
        var page = searchBean.page
        val numPerPage = searchBean.numPerPage
        var total = 0
        var found = false
        db.sql("select count(*) nr from (" + query + ") bq")(params: _*).each {
          rsc =>
            total = rsc.int("nr") ?! CoreExcCodes.OBJ_NULL
            found = true
        }

        // pagination logic
        val maxPages = Math.max(Math.abs(Math.ceil(total / numPerPage)), 1).asInstanceOf[Int]
        if (page * numPerPage > total)
          page = maxPages

        val limitStart = if (page == 1) 0 else (page - 1) * numPerPage + 1
        val limitEnd = Math.min(page * numPerPage, total)

        // now limit the query
        query.append(" limit ").append(limitStart).append(",").append(limitEnd)

        // Start constructing the response
        responseBean.setPage(page)
        responseBean.setMaxPages(maxPages)
        val gameRespList = new java.util.ArrayList[GameResp]

        // Execute the query, do for each row
        db.sql(query.toString())(params: _*).each {
          case rs =>

            // each response has sub structures
            val gameResp = new GameResp
            val serverResp = new ServerResp
            val playerRespList = new java.util.ArrayList[PlayerResp]


            // first add top - level game data
            gameResp.startDate = rs.date("date_start") ?! CoreExcCodes.OBJ_NULL
            gameResp.endDate = rs.date("date_end") ?! CoreExcCodes.OBJ_NULL
            gameResp.dcrec = rs.getString("dcrec")

            // now add some server data
            serverResp.name = rs.str("name") ?! CoreExcCodes.OBJ_NULL
            serverResp.archived = rs.int("archived") ?! CoreExcCodes.OBJ_NULL != 0
            serverResp.diplo = rs.int("diplo") ?! CoreExcCodes.OBJ_NULL != 0
            serverResp.numPlayers = rs.int("num_players") ?! CoreExcCodes.OBJ_NULL
            gameResp.serverData = serverResp

            /*
             * We will require an extra query for each game to find player info.
             * Search for it and set it if found.
             *
             * This query also needs a solid example.
             *
              select
                  g.id,
                  ghp.score,
                  t.name territory,
                  (select
                          GROUP_CONCAT(ign1.name
                                  order by ign1.orderdir asc
                                  separator ',') names
                      from
                          ingame_name ign1,
                          game_has_player ghp1
                      where
                          ign1.game_has_player_id = ghp1.id
                              and ghp1.id = ghp.id
                      group by ign1.game_has_player_id) player_names,
                  (select
                          group_concat(t.color
                                  order by ghpht.orderdir asc
                                  separator ',') teams
                      from
                          game_has_player_has_team ghpht,
                          team t,
                          game_has_player ghp1
                      where
                          ghp1.id = ghpht.game_has_player_id
                              and ghpht.team_id = t.id
                              and ghp1.id = ghp.id
                      group by ghpht.game_has_player_id) player_teams
              from
                  game g,
                  game_has_player ghp,
                  territory t
              where
                  g.id = ghp.game_id
                      and ghp.territory_id = t.id
                      and g.id = 2
             */

            val playerInfoQuery = new StringBuilder().append(" select g.id, ghp.score, t.name territory, ")
              .append(" (select group_concat(ign1.name order by ign1.orderdir asc separator ',') names ")
              .append(" from ingame_name ign1, game_has_player ghp1 ")
              .append(" where ign1.game_has_player_id = ghp1.id and ghp1.id = ghp.id ")
              .append(" group by ign1.game_has_player_id) player_names, ")
              .append(" (select group_concat(t.color order by ghpht.orderdir asc separator ',') teams ")
              .append(" from game_has_player_has_team ghpht, team t, game_has_player ghp1 ")
              .append(" where ghp1.id = ghpht.game_has_player_id and ghpht.team_id = t.id and ghp1.id = ghp.id ")
              .append(" group by ghpht.game_has_player_id) player_teams ")
              .append(" from game g, game_has_player ghp, territory t ")
              .append(" where g.id = ghp.game_id and ghp.territory_id = t.id and g.id = ?")

            db.sql(playerInfoQuery.toString())(rs.int("id").get).each {
              rsp =>

              // score
                val playerResp = new PlayerResp
                val score = rsp.str("score").get
                playerResp.hadScore = score != null && score.trim != "" && score.trim.toLowerCase != "null"
                if (playerResp.hadScore)
                  playerResp.score = rsp.int("score").get
                else
                  playerResp.score = -999

                // territory
                playerResp.territory = rsp.str("territory") ?! CoreExcCodes.OBJ_NULL

                // names
                val playerNames = new java.util.ArrayList[String]
                (rsp.str("player_names") ?! CoreExcCodes.OBJ_NULL).split(',').foreach(s => playerNames.add(s.trim()))
                playerResp.names = playerNames

                // teams
                val playerTeams = new java.util.ArrayList[String]
                (rsp.str("player_teams") ?! CoreExcCodes.OBJ_NULL).split(',').foreach(s => playerTeams.add(s.trim()))
                playerResp.teams = playerTeams

                // in the end add this player response to the player response list
                playerRespList.add(playerResp)
            }

            // Add the player resp list to the response
            gameResp.playerData = playerRespList

            // Add the gameResp to the game resp list
            gameRespList.add(gameResp)
        }

        // add the response list to the bean
        responseBean.games = gameRespList
      }
    })
    responseBean
  }
}