/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created at 9/14/13 7:10 PM.
 */
@SuppressWarnings("ALL")
@javax.persistence.Table(name = "game_has_player", schema = "", catalog = "LIVECON")
@Entity
public class GameHasPlayerE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String ip;

    @javax.persistence.Column(name = "ip")
    @Basic
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private Integer score;

    @javax.persistence.Column(name = "score")
    @Basic
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameHasPlayerE that = (GameHasPlayerE) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    private GameE gameByGameId;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false)
    public GameE getGameByGameId() {
        return gameByGameId;
    }

    public void setGameByGameId(GameE gameByGameId) {
        this.gameByGameId = gameByGameId;
    }

    private PlayerE playerByPlayerId;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    public PlayerE getPlayerByPlayerId() {
        return playerByPlayerId;
    }

    public void setPlayerByPlayerId(PlayerE playerByPlayerId) {
        this.playerByPlayerId = playerByPlayerId;
    }

    private TerritoryE territoryByTerritoryId;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "territory_id", referencedColumnName = "id", nullable = false)
    public TerritoryE getTerritoryByTerritoryId() {
        return territoryByTerritoryId;
    }

    public void setTerritoryByTerritoryId(TerritoryE territoryByTerritoryId) {
        this.territoryByTerritoryId = territoryByTerritoryId;
    }

    private Set<GameHasPlayerHasTeamE> gameHasPlayerHasTeamsById;

    @OneToMany(mappedBy = "gameHasPlayerByGameHasPlayerId")
    public Set<GameHasPlayerHasTeamE> getGameHasPlayerHasTeamsById() {
        return gameHasPlayerHasTeamsById;
    }

    public void setGameHasPlayerHasTeamsById(Set<GameHasPlayerHasTeamE> gameHasPlayerHasTeamsById) {
        this.gameHasPlayerHasTeamsById = gameHasPlayerHasTeamsById;
    }

    private Set<IngameNameE> ingameNamesById;

    @OneToMany(mappedBy = "gameHasPlayerByGameHasPlayerId")
    public Set<IngameNameE> getIngameNamesById() {
        return ingameNamesById;
    }

    public void setIngameNamesById(Set<IngameNameE> ingameNamesById) {
        this.ingameNamesById = ingameNamesById;
    }
}
