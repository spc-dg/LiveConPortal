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
@javax.persistence.Table(name = "admin", schema = "", catalog = "LIVECON")
@Entity
public class AdminE {
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

    private String keyid;

    @javax.persistence.Column(name = "keyid")
    @Basic
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    private Integer level;

    @javax.persistence.Column(name = "level")
    @Basic
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    private String password;

    @javax.persistence.Column(name = "password")
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminE adminE = (AdminE) o;

        if (id != null ? !id.equals(adminE.id) : adminE.id != null) return false;
        if (keyid != null ? !keyid.equals(adminE.keyid) : adminE.keyid != null) return false;
        if (level != null ? !level.equals(adminE.level) : adminE.level != null) return false;
        if (name != null ? !name.equals(adminE.name) : adminE.name != null) return false;
        if (password != null ? !password.equals(adminE.password) : adminE.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (keyid != null ? keyid.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
