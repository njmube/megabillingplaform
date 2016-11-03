package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Com_donees.
 */
@Entity
@Table(name = "com_donees")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_donees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "version", length = 50, nullable = false)
    private String version;

    @NotNull
    @Column(name = "no_authorization", nullable = false)
    private String no_authorization;

    @NotNull
    @Column(name = "date_authorization", nullable = false)
    private ZonedDateTime date_authorization;

    @NotNull
    @Column(name = "legend", nullable = false)
    private String legend;

    @OneToOne
    @JoinColumn(unique = true)
    private Cfdi cfdi;

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

    public String getNo_authorization() {
        return no_authorization;
    }

    public void setNo_authorization(String no_authorization) {
        this.no_authorization = no_authorization;
    }

    public ZonedDateTime getDate_authorization() {
        return date_authorization;
    }

    public void setDate_authorization(ZonedDateTime date_authorization) {
        this.date_authorization = date_authorization;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_donees com_donees = (Com_donees) o;
        if(com_donees.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_donees.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_donees{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", no_authorization='" + no_authorization + "'" +
            ", date_authorization='" + date_authorization + "'" +
            ", legend='" + legend + "'" +
            '}';
    }
}
