package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_notary_data.
 */
@Entity
@Table(name = "com_notary_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_notary_data implements Serializable {

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
    private Com_public_notaries com_public_notaries;

    @ManyToOne
    private Public_notaries_federal_entity public_notaries_federal_entity;

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

    public Com_public_notaries getCom_public_notaries() {
        return com_public_notaries;
    }

    public void setCom_public_notaries(Com_public_notaries com_public_notaries) {
        this.com_public_notaries = com_public_notaries;
    }

    public Public_notaries_federal_entity getPublic_notaries_federal_entity() {
        return public_notaries_federal_entity;
    }

    public void setPublic_notaries_federal_entity(Public_notaries_federal_entity public_notaries_federal_entity) {
        this.public_notaries_federal_entity = public_notaries_federal_entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_notary_data com_notary_data = (Com_notary_data) o;
        if(com_notary_data.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_notary_data.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_notary_data{" +
            "id=" + id +
            ", curp='" + curp + "'" +
            ", notarynumber='" + notarynumber + "'" +
            ", ascription='" + ascription + "'" +
            '}';
    }
}
