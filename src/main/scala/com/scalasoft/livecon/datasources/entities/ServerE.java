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
@javax.persistence.Table(name = "server", schema = "", catalog = "LIVECON")
@Entity
public class ServerE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String uniqueName;

    @javax.persistence.Column(name = "unique_name")
    @Basic
    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
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

    private Integer numPlayers;

    @javax.persistence.Column(name = "num_players")
    @Basic
    public Integer getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(Integer numPlayers) {
        this.numPlayers = numPlayers;
    }

    private String dir;

    @javax.persistence.Column(name = "dir")
    @Basic
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    private Integer archived;

    @javax.persistence.Column(name = "archived")
    @Basic
    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    private Integer diplo;

    @javax.persistence.Column(name = "diplo")
    @Basic
    public Integer getDiplo() {
        return diplo;
    }

    public void setDiplo(Integer diplo) {
        this.diplo = diplo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerE serverE = (ServerE) o;

        if (archived != null ? !archived.equals(serverE.archived) : serverE.archived != null) return false;
        if (diplo != null ? !diplo.equals(serverE.diplo) : serverE.diplo != null) return false;
        if (dir != null ? !dir.equals(serverE.dir) : serverE.dir != null) return false;
        if (id != null ? !id.equals(serverE.id) : serverE.id != null) return false;
        if (name != null ? !name.equals(serverE.name) : serverE.name != null) return false;
        if (numPlayers != null ? !numPlayers.equals(serverE.numPlayers) : serverE.numPlayers != null) return false;
        if (uniqueName != null ? !uniqueName.equals(serverE.uniqueName) : serverE.uniqueName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uniqueName != null ? uniqueName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (numPlayers != null ? numPlayers.hashCode() : 0);
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        result = 31 * result + (archived != null ? archived.hashCode() : 0);
        result = 31 * result + (diplo != null ? diplo.hashCode() : 0);
        return result;
    }

    private Set<GameE> gamesById;

    @OneToMany(mappedBy = "serverByServerId")
    public Set<GameE> getGamesById() {
        return gamesById;
    }

    public void setGamesById(Set<GameE> gamesById) {
        this.gamesById = gamesById;
    }
}
