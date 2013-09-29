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
@javax.persistence.Table(name = "drec", schema = "", catalog = "LIVECON")
@Entity
public class DrecE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    private String fullPath;

    @javax.persistence.Column(name = "full_path")
    @Basic
    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrecE drecE = (DrecE) o;

        if (dir != null ? !dir.equals(drecE.dir) : drecE.dir != null) return false;
        if (fullPath != null ? !fullPath.equals(drecE.fullPath) : drecE.fullPath != null) return false;
        if (id != null ? !id.equals(drecE.id) : drecE.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        result = 31 * result + (fullPath != null ? fullPath.hashCode() : 0);
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
}
