package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Com_foreign_tourist_passenger.
 */
@Entity
@Table(name = "com_foreign_tourist_passenger")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_foreign_tourist_passenger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "date_traffic", nullable = false)
    private ZonedDateTime date_traffic;

    @NotNull
    @Column(name = "typeid", nullable = false)
    private String typeid;

    @NotNull
    @Column(name = "numerid", nullable = false)
    private String numerid;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotNull
    @Column(name = "transportcompany", nullable = false)
    private String transportcompany;

    @Column(name = "idtransport")
    private String idtransport;

    @ManyToOne
    private C_transit_type c_transit_type;

    @ManyToOne
    private C_type_road c_type_road;

    @ManyToOne
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

    public ZonedDateTime getDate_traffic() {
        return date_traffic;
    }

    public void setDate_traffic(ZonedDateTime date_traffic) {
        this.date_traffic = date_traffic;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getNumerid() {
        return numerid;
    }

    public void setNumerid(String numerid) {
        this.numerid = numerid;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTransportcompany() {
        return transportcompany;
    }

    public void setTransportcompany(String transportcompany) {
        this.transportcompany = transportcompany;
    }

    public String getIdtransport() {
        return idtransport;
    }

    public void setIdtransport(String idtransport) {
        this.idtransport = idtransport;
    }

    public C_transit_type getC_transit_type() {
        return c_transit_type;
    }

    public void setC_transit_type(C_transit_type c_transit_type) {
        this.c_transit_type = c_transit_type;
    }

    public C_type_road getC_type_road() {
        return c_type_road;
    }

    public void setC_type_road(C_type_road c_type_road) {
        this.c_type_road = c_type_road;
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
        Com_foreign_tourist_passenger com_foreign_tourist_passenger = (Com_foreign_tourist_passenger) o;
        if(com_foreign_tourist_passenger.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_foreign_tourist_passenger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_foreign_tourist_passenger{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", date_traffic='" + date_traffic + "'" +
            ", typeid='" + typeid + "'" +
            ", numerid='" + numerid + "'" +
            ", nationality='" + nationality + "'" +
            ", transportcompany='" + transportcompany + "'" +
            ", idtransport='" + idtransport + "'" +
            '}';
    }
}
