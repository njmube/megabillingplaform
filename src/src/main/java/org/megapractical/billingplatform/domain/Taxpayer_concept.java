package org.megapractical.billingplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Taxpayer_concept.
 */
@Entity
@Table(name = "taxpayer_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "no_identification", nullable = false)
    private String no_identification;

    @Column(name = "description")
    private String description;

    @Column(name = "predial_number")
    private String predial_number;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

    @OneToMany(mappedBy = "taxpayer_concept")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Measure_unit_concept> measure_unit_concepts = new HashSet<>();

    @OneToMany(mappedBy = "taxpayer_concept")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Discount_concept> discount_concepts = new HashSet<>();

    @OneToMany(mappedBy = "taxpayer_concept")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tax_concept> tax_concepts = new HashSet<>();

    @OneToMany(mappedBy = "taxpayer_concept")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Price_concept> price_concepts = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPredial_number() {
        return predial_number;
    }

    public void setPredial_number(String predial_number) {
        this.predial_number = predial_number;
    }

    public Taxpayer_account getTaxpayer_account() {
        return taxpayer_account;
    }

    public void setTaxpayer_account(Taxpayer_account taxpayer_account) {
        this.taxpayer_account = taxpayer_account;
    }

    public Set<Measure_unit_concept> getMeasure_unit_concepts() {
        return measure_unit_concepts;
    }

    public void setMeasure_unit_concepts(Set<Measure_unit_concept> measure_unit_concepts) {
        this.measure_unit_concepts = measure_unit_concepts;
    }

    public Set<Discount_concept> getDiscount_concepts() {
        return discount_concepts;
    }

    public void setDiscount_concepts(Set<Discount_concept> discount_concepts) {
        this.discount_concepts = discount_concepts;
    }

    public Set<Tax_concept> getTax_concepts() {
        return tax_concepts;
    }

    public void setTax_concepts(Set<Tax_concept> tax_concepts) {
        this.tax_concepts = tax_concepts;
    }

    public Set<Price_concept> getPrice_concepts() {
        return price_concepts;
    }

    public void setPrice_concepts(Set<Price_concept> price_concepts) {
        this.price_concepts = price_concepts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taxpayer_concept taxpayer_concept = (Taxpayer_concept) o;
        if(taxpayer_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_concept{" +
            "id=" + id +
            ", no_identification='" + no_identification + "'" +
            ", description='" + description + "'" +
            ", predial_number='" + predial_number + "'" +
            '}';
    }
}
