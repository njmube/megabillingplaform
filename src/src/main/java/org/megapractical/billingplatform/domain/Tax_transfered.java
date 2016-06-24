package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Tax_transfered.
 */
@Entity
@Table(name = "tax_transfered")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tax_transfered implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Tax_types tax_types;

    @ManyToOne
    private Rate_type rate_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Tax_types getTax_types() {
        return tax_types;
    }

    public void setTax_types(Tax_types tax_types) {
        this.tax_types = tax_types;
    }

    public Rate_type getRate_type() {
        return rate_type;
    }

    public void setRate_type(Rate_type rate_type) {
        this.rate_type = rate_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tax_transfered tax_transfered = (Tax_transfered) o;
        if(tax_transfered.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tax_transfered.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tax_transfered{" +
            "id=" + id +
            ", amount='" + amount + "'" +
            '}';
    }
}
