/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created at 9/14/13 7:10 PM.
 */
@SuppressWarnings("ALL")
@javax.persistence.Table(name = "player", schema = "", catalog = "LIVECON")
@Entity
public class PlayerE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String keyid;

    @javax.persistence.Column(name = "keyid")
    @Basic
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerE playerE = (PlayerE) o;

        if (id != null ? !id.equals(playerE.id) : playerE.id != null) return false;
        if (keyid != null ? !keyid.equals(playerE.keyid) : playerE.keyid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (keyid != null ? keyid.hashCode() : 0);
        return result;
    }

    private Set<GameHasPlayerE> gameHasPlayersById;

    @OneToMany(mappedBy = "playerByPlayerId")
    public Set<GameHasPlayerE> getGameHasPlayersById() {
        return gameHasPlayersById;
    }

    public void setGameHasPlayersById(Set<GameHasPlayerE> gameHasPlayersById) {
        this.gameHasPlayersById = gameHasPlayersById;
    }
}
