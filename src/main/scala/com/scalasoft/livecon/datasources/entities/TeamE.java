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
@javax.persistence.Table(name = "team", schema = "", catalog = "LIVECON")
@Entity
public class TeamE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String color;

    @javax.persistence.Column(name = "color")
    @Basic
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String showColor;

    @javax.persistence.Column(name = "show_color")
    @Basic
    public String getShowColor() {
        return showColor;
    }

    public void setShowColor(String showColor) {
        this.showColor = showColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamE teamE = (TeamE) o;

        if (color != null ? !color.equals(teamE.color) : teamE.color != null) return false;
        if (id != null ? !id.equals(teamE.id) : teamE.id != null) return false;
        if (showColor != null ? !showColor.equals(teamE.showColor) : teamE.showColor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (showColor != null ? showColor.hashCode() : 0);
        return result;
    }

    private Set<GameHasPlayerHasTeamE> gameHasPlayerHasTeamsById;

    @OneToMany(mappedBy = "teamByTeamId")
    public Set<GameHasPlayerHasTeamE> getGameHasPlayerHasTeamsById() {
        return gameHasPlayerHasTeamsById;
    }

    public void setGameHasPlayerHasTeamsById(Set<GameHasPlayerHasTeamE> gameHasPlayerHasTeamsById) {
        this.gameHasPlayerHasTeamsById = gameHasPlayerHasTeamsById;
    }
}
