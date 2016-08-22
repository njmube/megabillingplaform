package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_legend.
 */
@Entity
@Table(name = "legend")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_legend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tax_provision")
    private String tax_provision;

    @Column(name = "norm")
    private String norm;

    @NotNull
    @Column(name = "text_legend", nullable = false)
    private String text_legend;

    @ManyToOne
    private Freecom_taxlegends freecom_taxlegends;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTax_provision() {
        return tax_provision;
    }

    public void setTax_provision(String tax_provision) {
        this.tax_provision = tax_provision;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public String getText_legend() {
        return text_legend;
    }

    public void setText_legend(String text_legend) {
        this.text_legend = text_legend;
    }

    public Freecom_taxlegends getFreecom_taxlegends() {
        return freecom_taxlegends;
    }

    public void setFreecom_taxlegends(Freecom_taxlegends freecom_taxlegends) {
        this.freecom_taxlegends = freecom_taxlegends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_legend freecom_legend = (Freecom_legend) o;
        if(freecom_legend.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_legend.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_legend{" +
            "id=" + id +
            ", tax_provision='" + tax_provision + "'" +
            ", norm='" + norm + "'" +
            ", text_legend='" + text_legend + "'" +
            '}';
    }
}
