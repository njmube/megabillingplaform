package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Free_concept.
 */
@javax.persistence.Entity
@Table(name = "free_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "no_identification")
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

    @NotNull
    @Column(name = "amount", precision=32, scale=6, nullable = false)
    private BigDecimal amount;

    @Column(name = "discount", precision=10, scale=2)
    private BigDecimal discount;

    @ManyToOne
    private Free_cfdi free_cfdi;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Free_cfdi getFree_cfdi() {
        return free_cfdi;
    }

    public void setFree_cfdi(Free_cfdi free_cfdi) {
        this.free_cfdi = free_cfdi;
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
        Free_concept free_concept = (Free_concept) o;
        if(free_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, free_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Free_concept{" +
            "id=" + id +
            ", no_identification='" + no_identification + "'" +
            ", quantity='" + quantity + "'" +
            ", description='" + description + "'" +
            ", unit_value='" + unit_value + "'" +
            ", predial_number='" + predial_number + "'" +
            ", amount='" + amount + "'" +
            ", discount='" + discount + "'" +
            '}';
    }
}
