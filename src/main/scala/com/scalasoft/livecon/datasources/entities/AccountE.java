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
@javax.persistence.Table(name = "account", schema = "", catalog = "LIVECON")
@Entity
public class AccountE {
    private Long id;
    private String username;
    private String password;
    private AccountLevelE accountLevelByAccountLevelId;

    @javax.persistence.Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @javax.persistence.Column(name = "username")
    @Basic
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

        AccountE accountE = (AccountE) o;

        if (id != null ? !id.equals(accountE.id) : accountE.id != null) return false;
        if (password != null ? !password.equals(accountE.password) : accountE.password != null) return false;
        if (username != null ? !username.equals(accountE.username) : accountE.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @javax.persistence.JoinColumn(name = "account_level_id", referencedColumnName = "id", nullable = false)
    public AccountLevelE getAccountLevelByAccountLevelId() {
        return accountLevelByAccountLevelId;
    }

    public void setAccountLevelByAccountLevelId(AccountLevelE accountLevelByAccountLevelId) {
        this.accountLevelByAccountLevelId = accountLevelByAccountLevelId;
    }
}
