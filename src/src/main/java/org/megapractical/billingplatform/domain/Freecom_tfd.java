package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Freecom_tfd.
 */
@Entity
@Table(name = "freecom_tfd")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_tfd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @NotNull
    @Column(name = "stamp_date", nullable = false)
    private ZonedDateTime stamp_date;

    @NotNull
    @Column(name = "stamp_cfd", nullable = false)
    private String stamp_cfd;

    @NotNull
    @Size(max = 20)
    @Column(name = "sat_number_certificate", length = 20, nullable = false)
    private String sat_number_certificate;

    @NotNull
    @Column(name = "stamp_sat", nullable = false)
    private String stamp_sat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getStamp_date() {
        return stamp_date;
    }

    public void setStamp_date(ZonedDateTime stamp_date) {
        this.stamp_date = stamp_date;
    }

    public String getStamp_cfd() {
        return stamp_cfd;
    }

    public void setStamp_cfd(String stamp_cfd) {
        this.stamp_cfd = stamp_cfd;
    }

    public String getSat_number_certificate() {
        return sat_number_certificate;
    }

    public void setSat_number_certificate(String sat_number_certificate) {
        this.sat_number_certificate = sat_number_certificate;
    }

    public String getStamp_sat() {
        return stamp_sat;
    }

    public void setStamp_sat(String stamp_sat) {
        this.stamp_sat = stamp_sat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_tfd freecom_tfd = (Freecom_tfd) o;
        if(freecom_tfd.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_tfd.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_tfd{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", uuid='" + uuid + "'" +
            ", stamp_date='" + stamp_date + "'" +
            ", stamp_cfd='" + stamp_cfd + "'" +
            ", sat_number_certificate='" + sat_number_certificate + "'" +
            ", stamp_sat='" + stamp_sat + "'" +
            '}';
    }
}
