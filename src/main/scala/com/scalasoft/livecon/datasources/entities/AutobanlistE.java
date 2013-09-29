/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created at 9/14/13 7:10 PM.
 */
@SuppressWarnings("ALL")
@javax.persistence.Table(name = "autobanlist", schema = "", catalog = "LIVECON")
@Entity
public class AutobanlistE {
    private Long id;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String sqlPattern;

    @javax.persistence.Column(name = "sql_pattern")
    @Basic
    public String getSqlPattern() {
        return sqlPattern;
    }

    public void setSqlPattern(String sqlPattern) {
        this.sqlPattern = sqlPattern;
    }

    private String regex;

    @javax.persistence.Column(name = "regex")
    @Basic
    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutobanlistE that = (AutobanlistE) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (regex != null ? !regex.equals(that.regex) : that.regex != null) return false;
        if (sqlPattern != null ? !sqlPattern.equals(that.sqlPattern) : that.sqlPattern != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sqlPattern != null ? sqlPattern.hashCode() : 0);
        result = 31 * result + (regex != null ? regex.hashCode() : 0);
        return result;
    }
}
