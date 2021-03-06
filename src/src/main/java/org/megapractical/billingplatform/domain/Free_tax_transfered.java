package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Free_tax_transfered.
 */
@javax.persistence.Entity
@Table(name = "free_tax_transfered")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_tax_transfered implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rate", precision=10, scale=2)
    private BigDecimal rate;

    @NotNull
    @Column(name = "amount", precision=32, scale=6, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Free_concept free_concept;

    @ManyToOne
    private Tax_types tax_types;

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

    public Free_concept getFree_concept() {
        return free_concept;
    }

    public void setFree_concept(Free_concept free_concept) {
        this.free_concept = free_concept;
    }

    public Tax_types getTax_types() {
        return tax_types;
    }

    public void setTax_types(Tax_types tax_types) {
        this.tax_types = tax_types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Free_tax_transfered free_tax_transfered = (Free_tax_transfered) o;
        if(free_tax_transfered.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, free_tax_transfered.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Free_tax_transfered{" +
            "id=" + id +
            ", rate='" + rate + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
