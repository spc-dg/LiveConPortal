/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created at 9/14/13 7:10 PM.
 */
@SuppressWarnings("ALL")
@javax.persistence.Table(name = "game_has_player_has_team", schema = "", catalog = "LIVECON")
@Entity
public class GameHasPlayerHasTeamE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Integer orderdir;

    @javax.persistence.Column(name = "orderdir")
    @Basic
    public Integer getOrderdir() {
        return orderdir;
    }

    public void setOrderdir(Integer orderdir) {
        this.orderdir = orderdir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameHasPlayerHasTeamE that = (GameHasPlayerHasTeamE) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderdir != null ? !orderdir.equals(that.orderdir) : that.orderdir != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderdir != null ? orderdir.hashCode() : 0);
        return result;
    }

    private GameHasPlayerE gameHasPlayerByGameHasPlayerId;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "game_has_player_id", referencedColumnName = "id", nullable = false)
    public GameHasPlayerE getGameHasPlayerByGameHasPlayerId() {
        return gameHasPlayerByGameHasPlayerId;
    }

    public void setGameHasPlayerByGameHasPlayerId(GameHasPlayerE gameHasPlayerByGameHasPlayerId) {
        this.gameHasPlayerByGameHasPlayerId = gameHasPlayerByGameHasPlayerId;
    }

    private TeamE teamByTeamId;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    public TeamE getTeamByTeamId() {
        return teamByTeamId;
    }

    public void setTeamByTeamId(TeamE teamByTeamId) {
        this.teamByTeamId = teamByTeamId;
    }
}
