package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_local_taxes.
 */
@Entity
@Table(name = "freecom_local_taxes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_local_taxes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "total_local_retentions", precision=10, scale=2, nullable = false)
    private BigDecimal total_local_retentions;

    @NotNull
    @Column(name = "total_local_transfered", precision=10, scale=2, nullable = false)
    private BigDecimal total_local_transfered;

    @ManyToOne
    private Free_cfdi free_cfdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BigDecimal getTotal_local_retentions() {
        return total_local_retentions;
    }

    public void setTotal_local_retentions(BigDecimal total_local_retentions) {
        this.total_local_retentions = total_local_retentions;
    }

    public BigDecimal getTotal_local_transfered() {
        return total_local_transfered;
    }

    public void setTotal_local_transfered(BigDecimal total_local_transfered) {
        this.total_local_transfered = total_local_transfered;
    }

    public Free_cfdi getFree_cfdi() {
        return free_cfdi;
    }

    public void setFree_cfdi(Free_cfdi free_cfdi) {
        this.free_cfdi = free_cfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_local_taxes freecom_local_taxes = (Freecom_local_taxes) o;
        if(freecom_local_taxes.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_local_taxes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_local_taxes{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", total_local_retentions='" + total_local_retentions + "'" +
            ", total_local_transfered='" + total_local_transfered + "'" +
            '}';
    }
}
