package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_partial_construction_services.
 */
@Entity
@Table(name = "com_partial_construction_services")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_partial_construction_services implements Serializable {

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

    @Column(name = "location")
    private String location;

    @Column(name = "reference")
    private String reference;

    @NotNull
    @Column(name = "numperlicoaut", nullable = false)
    private String numperlicoaut;

    @ManyToOne
    private Cfdi cfdi;

    @ManyToOne
    private C_federal_entity c_federal_entity;

    @ManyToOne
    @NotNull
    private C_municipality c_municipality;

    @ManyToOne
    private C_colony c_colony;

    @ManyToOne
    private C_zip_code c_zip_code;

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

    public String getNumperlicoaut() {
        return numperlicoaut;
    }

    public void setNumperlicoaut(String numperlicoaut) {
        this.numperlicoaut = numperlicoaut;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    public C_federal_entity getC_federal_entity() {
        return c_federal_entity;
    }

    public void setC_federal_entity(C_federal_entity c_federal_entity) {
        this.c_federal_entity = c_federal_entity;
    }

    public C_municipality getC_municipality() {
        return c_municipality;
    }

    public void setC_municipality(C_municipality c_municipality) {
        this.c_municipality = c_municipality;
    }

    public C_colony getC_colony() {
        return c_colony;
    }

    public void setC_colony(C_colony c_colony) {
        this.c_colony = c_colony;
    }

    public C_zip_code getC_zip_code() {
        return c_zip_code;
    }

    public void setC_zip_code(C_zip_code c_zip_code) {
        this.c_zip_code = c_zip_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_partial_construction_services com_partial_construction_services = (Com_partial_construction_services) o;
        if(com_partial_construction_services.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_partial_construction_services.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_partial_construction_services{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", street='" + street + "'" +
            ", noext='" + noext + "'" +
            ", noint='" + noint + "'" +
            ", location='" + location + "'" +
            ", reference='" + reference + "'" +
            ", numperlicoaut='" + numperlicoaut + "'" +
            '}';
    }
}
