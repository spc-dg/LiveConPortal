/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created at 9/14/13 7:10 PM.
 */
@SuppressWarnings("ALL")
@javax.persistence.Table(name = "banlist", schema = "", catalog = "LIVECON")
@Entity
public class BanlistE {
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

    private String keyid;

    @javax.persistence.Column(name = "keyid")
    @Basic
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    private String ipOrRange;

    @javax.persistence.Column(name = "ip_or_range")
    @Basic
    public String getIpOrRange() {
        return ipOrRange;
    }

    public void setIpOrRange(String ipOrRange) {
        this.ipOrRange = ipOrRange;
    }

    private String reason;

    @javax.persistence.Column(name = "reason")
    @Basic
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BanlistE banlistE = (BanlistE) o;

        if (dateEnd != null ? !dateEnd.equals(banlistE.dateEnd) : banlistE.dateEnd != null) return false;
        if (dateStart != null ? !dateStart.equals(banlistE.dateStart) : banlistE.dateStart != null) return false;
        if (id != null ? !id.equals(banlistE.id) : banlistE.id != null) return false;
        if (ipOrRange != null ? !ipOrRange.equals(banlistE.ipOrRange) : banlistE.ipOrRange != null) return false;
        if (keyid != null ? !keyid.equals(banlistE.keyid) : banlistE.keyid != null) return false;
        if (reason != null ? !reason.equals(banlistE.reason) : banlistE.reason != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (keyid != null ? keyid.hashCode() : 0);
        result = 31 * result + (ipOrRange != null ? ipOrRange.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        return result;
    }
}
