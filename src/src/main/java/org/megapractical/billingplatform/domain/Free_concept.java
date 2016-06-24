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
@Entity
@Table(name = "free_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "no_identification", nullable = false)
    private String no_identification;

    @NotNull
    @Column(name = "quantity", precision=10, scale=2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "unit_value", precision=10, scale=2, nullable = false)
    private BigDecimal unit_value;

    @Column(name = "predial_number")
    private String predial_number;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Free_cfdi free_cfdi;

    @ManyToOne
    private Measure_unit measure_unit;

    @ManyToOne
    private Tax_transfered tax_transfered;

    @ManyToOne
    private Tax_retentions tax_retentions;

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

    public Tax_transfered getTax_transfered() {
        return tax_transfered;
    }

    public void setTax_transfered(Tax_transfered tax_transfered) {
        this.tax_transfered = tax_transfered;
    }

    public Tax_retentions getTax_retentions() {
        return tax_retentions;
    }

    public void setTax_retentions(Tax_retentions tax_retentions) {
        this.tax_retentions = tax_retentions;
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
            '}';
    }
}
