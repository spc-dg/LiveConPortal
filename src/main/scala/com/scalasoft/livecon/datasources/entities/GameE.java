/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created at 9/14/13 7:10 PM.
 */
@SuppressWarnings("ALL")
@javax.persistence.Table(name = "game", schema = "", catalog = "LIVECON")
@Entity
public class GameE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Timestamp dateStart;

    @javax.persistence.Column(name = "date_start")
    @Basic
    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    private Timestamp dateEnd;

    @javax.persistence.Column(name = "date_end")
    @Basic
    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameE gameE = (GameE) o;

        if (dateEnd != null ? !dateEnd.equals(gameE.dateEnd) : gameE.dateEnd != null) return false;
        if (dateStart != null ? !dateStart.equals(gameE.dateStart) : gameE.dateStart != null) return false;
        if (id != null ? !id.equals(gameE.id) : gameE.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        return result;
    }

    private Set<DrecE> drecsById;

    @OneToMany(mappedBy = "gameByGameId")
    public Set<DrecE> getDrecsById() {
        return drecsById;
    }

    public void setDrecsById(Set<DrecE> drecsById) {
        this.drecsById = drecsById;
    }

    private ServerE serverByServerId;

    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
    public ServerE getServerByServerId() {
        return serverByServerId;
    }

    public void setServerByServerId(ServerE serverByServerId) {
        this.serverByServerId = serverByServerId;
    }

    private Set<GameHasPlayerE> gameHasPlayersById;

    @OneToMany(mappedBy = "gameByGameId")
    public Set<GameHasPlayerE> getGameHasPlayersById() {
        return gameHasPlayersById;
    }

    public void setGameHasPlayersById(Set<GameHasPlayerE> gameHasPlayersById) {
        this.gameHasPlayersById = gameHasPlayersById;
    }
}
