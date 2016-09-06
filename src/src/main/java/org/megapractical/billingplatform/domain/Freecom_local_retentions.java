package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_local_retentions.
 */
@Entity
@Table(name = "freecom_local_retentions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_local_retentions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "implocretentions", nullable = false)
    private String implocretentions;

    @NotNull
    @Column(name = "retentionrate", precision=10, scale=2, nullable = false)
    private BigDecimal retentionrate;

    @NotNull
    @Column(name = "amountretentions", precision=10, scale=2, nullable = false)
    private BigDecimal amountretentions;

    @ManyToOne
    private Freecom_local_taxes freecom_local_taxes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImplocretentions() {
        return implocretentions;
    }

    public void setImplocretentions(String implocretentions) {
        this.implocretentions = implocretentions;
    }

    public BigDecimal getRetentionrate() {
        return retentionrate;
    }

    public void setRetentionrate(BigDecimal retentionrate) {
        this.retentionrate = retentionrate;
    }

    public BigDecimal getAmountretentions() {
        return amountretentions;
    }

    public void setAmountretentions(BigDecimal amountretentions) {
        this.amountretentions = amountretentions;
    }

    public Freecom_local_taxes getFreecom_local_taxes() {
        return freecom_local_taxes;
    }

    public void setFreecom_local_taxes(Freecom_local_taxes freecom_local_taxes) {
        this.freecom_local_taxes = freecom_local_taxes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_local_retentions freecom_local_retentions = (Freecom_local_retentions) o;
        if(freecom_local_retentions.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_local_retentions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_local_retentions{" +
            "id=" + id +
            ", implocretentions='" + implocretentions + "'" +
            ", retentionrate='" + retentionrate + "'" +
            ", amountretentions='" + amountretentions + "'" +
            '}';
    }
}
