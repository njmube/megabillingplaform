package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_desc_state.
 */
@Entity
@Table(name = "com_desc_state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_desc_state implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "noext")
    private String noext;

    @Column(name = "noint")
    private String noint;

    @Column(name = "locale")
    private String locale;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    private Com_public_notaries com_public_notaries;

    @ManyToOne
    private Type_state type_state;

    @ManyToOne
    private C_colony c_colony;

    @ManyToOne
    private C_municipality c_municipality;

    @ManyToOne
    private C_state c_state;

    @ManyToOne
    private C_country c_country;

    @ManyToOne
    private C_zip_code c_zip_code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Com_public_notaries getCom_public_notaries() {
        return com_public_notaries;
    }

    public void setCom_public_notaries(Com_public_notaries com_public_notaries) {
        this.com_public_notaries = com_public_notaries;
    }

    public Type_state getType_state() {
        return type_state;
    }

    public void setType_state(Type_state type_state) {
        this.type_state = type_state;
    }

    public C_colony getC_colony() {
        return c_colony;
    }

    public void setC_colony(C_colony c_colony) {
        this.c_colony = c_colony;
    }

    public C_municipality getC_municipality() {
        return c_municipality;
    }

    public void setC_municipality(C_municipality c_municipality) {
        this.c_municipality = c_municipality;
    }

    public C_state getC_state() {
        return c_state;
    }

    public void setC_state(C_state c_state) {
        this.c_state = c_state;
    }

    public C_country getC_country() {
        return c_country;
    }

    public void setC_country(C_country c_country) {
        this.c_country = c_country;
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
        Com_desc_state com_desc_state = (Com_desc_state) o;
        if(com_desc_state.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_desc_state.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_desc_state{" +
            "id=" + id +
            ", street='" + street + "'" +
            ", noext='" + noext + "'" +
            ", noint='" + noint + "'" +
            ", locale='" + locale + "'" +
            ", reference='" + reference + "'" +
            '}';
    }
}
