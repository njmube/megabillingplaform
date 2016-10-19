package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Measure_unit_concept.
 */
@Entity
@Table(name = "measure_unit_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Measure_unit_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private Measure_unit measure_unit;

    @ManyToOne
    @NotNull
    private Taxpayer_concept taxpayer_concept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Measure_unit getMeasure_unit() {
        return measure_unit;
    }

    public void setMeasure_unit(Measure_unit measure_unit) {
        this.measure_unit = measure_unit;
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
        Measure_unit_concept measure_unit_concept = (Measure_unit_concept) o;
        if(measure_unit_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, measure_unit_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Measure_unit_concept{" +
            "id=" + id +
            '}';
    }
}
