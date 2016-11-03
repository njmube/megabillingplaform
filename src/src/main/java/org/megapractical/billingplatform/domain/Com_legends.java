package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_legends.
 */
@Entity
@Table(name = "com_legends")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_legends implements Serializable {

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
    private Com_taxlegends com_taxlegends;

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

    public Com_taxlegends getCom_taxlegends() {
        return com_taxlegends;
    }

    public void setCom_taxlegends(Com_taxlegends com_taxlegends) {
        this.com_taxlegends = com_taxlegends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_legends com_legends = (Com_legends) o;
        if(com_legends.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_legends.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_legends{" +
            "id=" + id +
            ", tax_provision='" + tax_provision + "'" +
            ", norm='" + norm + "'" +
            ", text_legend='" + text_legend + "'" +
            '}';
    }
}
