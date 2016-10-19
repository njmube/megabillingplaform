package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Tax_concept.
 */
@Entity
@Table(name = "tax_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tax_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "rate", precision=10, scale=2, nullable = false)
    private BigDecimal rate;

    @ManyToOne
    @NotNull
    private Tax_types tax_types;

    @ManyToOne
    @NotNull
    private Taxpayer_concept taxpayer_concept;

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

    public Tax_types getTax_types() {
        return tax_types;
    }

    public void setTax_types(Tax_types tax_types) {
        this.tax_types = tax_types;
    }

    public Taxpayer_concept getTaxpayer_concept() {
        return taxpayer_concept;
    }

    public void setTaxpayer_concept(Taxpayer_concept taxpayer_concept) {
        this.taxpayer_concept = taxpayer_concept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tax_concept tax_concept = (Tax_concept) o;
        if(tax_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tax_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tax_concept{" +
            "id=" + id +
            ", rate='" + rate + "'" +
            '}';
    }
}
