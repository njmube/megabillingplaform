package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_local_transfered.
 */
@Entity
@Table(name = "freecom_local_transfered")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_local_transfered implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
        Freecom_local_transfered freecom_local_transfered = (Freecom_local_transfered) o;
        if(freecom_local_transfered.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_local_transfered.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_local_transfered{" +
            "id=" + id +
            ", imploctransfered='" + imploctransfered + "'" +
            ", transferedrate='" + transferedrate + "'" +
            ", amounttransfered='" + amounttransfered + "'" +
            '}';
    }
}
