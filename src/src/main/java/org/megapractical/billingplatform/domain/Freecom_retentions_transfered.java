package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_retentions_transfered.
 */
@Entity
@Table(name = "freecom_retentions_transfered")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_retentions_transfered implements Serializable {

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

    @NotNull
    @Column(name = "imploctransfered", nullable = false)
    private String imploctransfered;

    @NotNull
    @Column(name = "transferedrate", precision=10, scale=2, nullable = false)
    private BigDecimal transferedrate;

    @NotNull
    @Column(name = "amounttransfered", precision=10, scale=2, nullable = false)
    private BigDecimal amounttransfered;

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

    public String getImploctransfered() {
        return imploctransfered;
    }

    public void setImploctransfered(String imploctransfered) {
        this.imploctransfered = imploctransfered;
    }

    public BigDecimal getTransferedrate() {
        return transferedrate;
    }

    public void setTransferedrate(BigDecimal transferedrate) {
        this.transferedrate = transferedrate;
    }

    public BigDecimal getAmounttransfered() {
        return amounttransfered;
    }

    public void setAmounttransfered(BigDecimal amounttransfered) {
        this.amounttransfered = amounttransfered;
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
        Freecom_retentions_transfered freecom_retentions_transfered = (Freecom_retentions_transfered) o;
        if(freecom_retentions_transfered.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_retentions_transfered.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_retentions_transfered{" +
            "id=" + id +
            ", implocretentions='" + implocretentions + "'" +
            ", retentionrate='" + retentionrate + "'" +
            ", amountretentions='" + amountretentions + "'" +
            ", imploctransfered='" + imploctransfered + "'" +
            ", transferedrate='" + transferedrate + "'" +
            ", amounttransfered='" + amounttransfered + "'" +
            '}';
    }
}
