package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_local_transfered.
 */
@Entity
@Table(name = "com_local_transfered")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_local_transfered implements Serializable {

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
    private Com_local_taxes com_local_taxes;

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

    public Com_local_taxes getCom_local_taxes() {
        return com_local_taxes;
    }

    public void setCom_local_taxes(Com_local_taxes com_local_taxes) {
        this.com_local_taxes = com_local_taxes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_local_transfered com_local_transfered = (Com_local_transfered) o;
        if(com_local_transfered.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_local_transfered.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_local_transfered{" +
            "id=" + id +
            ", imploctransfered='" + imploctransfered + "'" +
            ", transferedrate='" + transferedrate + "'" +
            ", amounttransfered='" + amounttransfered + "'" +
            '}';
    }
}
