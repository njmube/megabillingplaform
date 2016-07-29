package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_partial_construction_services.
 */
@Entity
@Table(name = "freecom_partial_construction_services")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_partial_construction_services implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "noext")
    private String noext;

    @Column(name = "noint")
    private String noint;

    @Column(name = "colony")
    private String colony;

    @Column(name = "location")
    private String location;

    @Column(name = "reference")
    private String reference;

    @NotNull
    @Column(name = "municipality", nullable = false)
    private String municipality;

    @NotNull
    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @NotNull
    @Column(name = "numperlicoaut", nullable = false)
    private String numperlicoaut;

    @ManyToOne
    private Free_cfdi free_cfdi;

    @ManyToOne
    private C_federal_entity c_federal_entity;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNoext() {
        return noext;
    }

    public void setNoext(String noext) {
        this.noext = noext;
    }

    public String getNoint() {
        return noint;
    }

    public void setNoint(String noint) {
        this.noint = noint;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getNumperlicoaut() {
        return numperlicoaut;
    }

    public void setNumperlicoaut(String numperlicoaut) {
        this.numperlicoaut = numperlicoaut;
    }

    public Free_cfdi getFree_cfdi() {
        return free_cfdi;
    }

    public void setFree_cfdi(Free_cfdi free_cfdi) {
        this.free_cfdi = free_cfdi;
    }

    public C_federal_entity getC_federal_entity() {
        return c_federal_entity;
    }

    public void setC_federal_entity(C_federal_entity c_federal_entity) {
        this.c_federal_entity = c_federal_entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_partial_construction_services freecom_partial_construction_services = (Freecom_partial_construction_services) o;
        if(freecom_partial_construction_services.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_partial_construction_services.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_partial_construction_services{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", street='" + street + "'" +
            ", noext='" + noext + "'" +
            ", noint='" + noint + "'" +
            ", colony='" + colony + "'" +
            ", location='" + location + "'" +
            ", reference='" + reference + "'" +
            ", municipality='" + municipality + "'" +
            ", zipcode='" + zipcode + "'" +
            ", numperlicoaut='" + numperlicoaut + "'" +
            '}';
    }
}
