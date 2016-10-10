package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Concept.
 */
@Entity
@Table(name = "concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "no_identification", nullable = false)
    private String no_identification;

    @NotNull
    @Column(name = "quantity", precision=32, scale=6, nullable = false)
    private BigDecimal quantity;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "unit_value", precision=32, scale=6, nullable = false)
    private BigDecimal unit_value;

    @Column(name = "predial_number")
    private String predial_number;

    @Column(name = "discount", precision=10, scale=2)
    private BigDecimal discount;

    @Column(name = "amount", precision=32, scale=6)
    private BigDecimal amount;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

    @ManyToOne
    private Measure_unit measure_unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo_identification() {
        return no_identification;
    }

    public void setNo_identification(String no_identification) {
        this.no_identification = no_identification;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(BigDecimal unit_value) {
        this.unit_value = unit_value;
    }

    public String getPredial_number() {
        return predial_number;
    }

    public void setPredial_number(String predial_number) {
        this.predial_number = predial_number;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Taxpayer_account getTaxpayer_account() {
        return taxpayer_account;
    }

    public void setTaxpayer_account(Taxpayer_account taxpayer_account) {
        this.taxpayer_account = taxpayer_account;
    }

    public Measure_unit getMeasure_unit() {
        return measure_unit;
    }

    public void setMeasure_unit(Measure_unit measure_unit) {
        this.measure_unit = measure_unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Concept concept = (Concept) o;
        if(concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Concept{" +
            "id=" + id +
            ", no_identification='" + no_identification + "'" +
            ", quantity='" + quantity + "'" +
            ", description='" + description + "'" +
            ", unit_value='" + unit_value + "'" +
            ", predial_number='" + predial_number + "'" +
            ", discount='" + discount + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
