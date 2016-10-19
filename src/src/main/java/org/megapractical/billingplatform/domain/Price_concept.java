package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Price_concept.
 */
@Entity
@Table(name = "price_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Price_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "value", precision=10, scale=2, nullable = false)
    private BigDecimal value;

    @ManyToOne
    @NotNull
    private Taxpayer_concept taxpayer_concept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
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
        Price_concept price_concept = (Price_concept) o;
        if(price_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, price_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Price_concept{" +
            "id=" + id +
            ", value='" + value + "'" +
            '}';
    }
}
