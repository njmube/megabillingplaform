package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_notary_data.
 */
@Entity
@Table(name = "freecom_notary_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_notary_data implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "curp", nullable = false)
    private String curp;

    @NotNull
    @Column(name = "notarynumber", nullable = false)
    private Integer notarynumber;

    @Column(name = "ascription")
    private String ascription;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_public_notaries freecom_public_notaries;

    @ManyToOne
    private C_pn_federal_entity c_pn_federal_entity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Integer getNotarynumber() {
        return notarynumber;
    }

    public void setNotarynumber(Integer notarynumber) {
        this.notarynumber = notarynumber;
    }

    public String getAscription() {
        return ascription;
    }

    public void setAscription(String ascription) {
        this.ascription = ascription;
    }

    public Freecom_public_notaries getFreecom_public_notaries() {
        return freecom_public_notaries;
    }

    public void setFreecom_public_notaries(Freecom_public_notaries freecom_public_notaries) {
        this.freecom_public_notaries = freecom_public_notaries;
    }

    public C_pn_federal_entity getC_pn_federal_entity() {
        return c_pn_federal_entity;
    }

    public void setC_pn_federal_entity(C_pn_federal_entity c_pn_federal_entity) {
        this.c_pn_federal_entity = c_pn_federal_entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_notary_data freecom_notary_data = (Freecom_notary_data) o;
        if(freecom_notary_data.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_notary_data.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_notary_data{" +
            "id=" + id +
            ", curp='" + curp + "'" +
            ", notarynumber='" + notarynumber + "'" +
            ", ascription='" + ascription + "'" +
            '}';
    }
}
