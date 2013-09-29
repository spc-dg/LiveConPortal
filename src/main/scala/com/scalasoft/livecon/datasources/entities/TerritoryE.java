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
@javax.persistence.Table(name = "territory", schema = "", catalog = "LIVECON")
@Entity
public class TerritoryE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;

    @javax.persistence.Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String showName;

    @javax.persistence.Column(name = "show_name")
    @Basic
    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TerritoryE that = (TerritoryE) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (showName != null ? !showName.equals(that.showName) : that.showName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (showName != null ? showName.hashCode() : 0);
        return result;
    }

    private Set<GameHasPlayerE> gameHasPlayersById;

    @OneToMany(mappedBy = "territoryByTerritoryId")
    public Set<GameHasPlayerE> getGameHasPlayersById() {
        return gameHasPlayersById;
    }

    public void setGameHasPlayersById(Set<GameHasPlayerE> gameHasPlayersById) {
        this.gameHasPlayersById = gameHasPlayersById;
    }
}
