package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Com_paybill_concept.
 */
@Entity
@Table(name = "com_paybill_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_paybill_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "identification_number", length = 20, nullable = false)
    private String identification_number;

    @NotNull
    @Column(name = "date_expedition", nullable = false)
    private ZonedDateTime date_expedition;

    @NotNull
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @NotNull
    @Column(name = "curp", nullable = false)
    private String curp;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 15)
    @Column(name = "social_security_number", length = 15)
    private String social_security_number;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Com_storeroom_paybill com_storeroom_paybill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentification_number() {
        return identification_number;
    }

    public void setIdentification_number(String identification_number) {
        this.identification_number = identification_number;
    }

    public ZonedDateTime getDate_expedition() {
        return date_expedition;
    }

    public void setDate_expedition(ZonedDateTime date_expedition) {
        this.date_expedition = date_expedition;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocial_security_number() {
        return social_security_number;
    }

    public void setSocial_security_number(String social_security_number) {
        this.social_security_number = social_security_number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Com_storeroom_paybill getCom_storeroom_paybill() {
        return com_storeroom_paybill;
    }

    public void setCom_storeroom_paybill(Com_storeroom_paybill com_storeroom_paybill) {
        this.com_storeroom_paybill = com_storeroom_paybill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_paybill_concept com_paybill_concept = (Com_paybill_concept) o;
        if(com_paybill_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_paybill_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_paybill_concept{" +
            "id=" + id +
            ", identification_number='" + identification_number + "'" +
            ", date_expedition='" + date_expedition + "'" +
            ", rfc='" + rfc + "'" +
            ", curp='" + curp + "'" +
            ", name='" + name + "'" +
            ", social_security_number='" + social_security_number + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
