package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_determined.
 */
@Entity
@Table(name = "com_determined")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_determined implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "rate", precision=10, scale=2, nullable = false)
    private BigDecimal rate;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Com_concept_fuel com_concept_fuel;

    @ManyToOne
    private Freecom_tax_type freecom_tax_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Com_concept_fuel getCom_concept_fuel() {
        return com_concept_fuel;
    }

    public void setCom_concept_fuel(Com_concept_fuel com_concept_fuel) {
        this.com_concept_fuel = com_concept_fuel;
    }

    public Freecom_tax_type getFreecom_tax_type() {
        return freecom_tax_type;
    }

    public void setFreecom_tax_type(Freecom_tax_type freecom_tax_type) {
        this.freecom_tax_type = freecom_tax_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_determined com_determined = (Com_determined) o;
        if(com_determined.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_determined.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_determined{" +
            "id=" + id +
            ", rate='" + rate + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
