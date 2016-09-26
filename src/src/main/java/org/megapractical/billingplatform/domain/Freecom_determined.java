package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_determined.
 */
@Entity
@Table(name = "freecom_determined")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_determined implements Serializable {

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
    private Freecom_concept_fuel freecom_concept_fuel;

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

    public Freecom_concept_fuel getFreecom_concept_fuel() {
        return freecom_concept_fuel;
    }

    public void setFreecom_concept_fuel(Freecom_concept_fuel freecom_concept_fuel) {
        this.freecom_concept_fuel = freecom_concept_fuel;
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
        Freecom_determined freecom_determined = (Freecom_determined) o;
        if(freecom_determined.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_determined.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_determined{" +
            "id=" + id +
            ", rate='" + rate + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
