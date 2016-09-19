package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Free_part_concept.
 */
@javax.persistence.Entity
@Table(name = "free_part_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_part_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "no_identification")
    private String no_identification;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "unit_value", precision=32, scale=6)
    private BigDecimal unit_value;

    @Column(name = "amount", precision=32, scale=6)
    private BigDecimal amount;

    @ManyToOne
    private Free_concept free_concept;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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
        Free_part_concept free_part_concept = (Free_part_concept) o;
        if(free_part_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, free_part_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Free_part_concept{" +
            "id=" + id +
            ", no_identification='" + no_identification + "'" +
            ", quantity='" + quantity + "'" +
            ", description='" + description + "'" +
            ", unit_value='" + unit_value + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
