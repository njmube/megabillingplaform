package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_ecc11.
 */
@Entity
@Table(name = "freecom_ecc_11")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_ecc11 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "type_operation", nullable = false)
    private String type_operation;

    @NotNull
    @Size(max = 50)
    @Column(name = "number_account", length = 50, nullable = false)
    private String number_account;

    @NotNull
    @Column(name = "subtotal", precision=10, scale=2, nullable = false)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "total", precision=10, scale=2, nullable = false)
    private BigDecimal total;

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

    public String getType_operation() {
        return type_operation;
    }

    public void setType_operation(String type_operation) {
        this.type_operation = type_operation;
    }

    public String getNumber_account() {
        return number_account;
    }

    public void setNumber_account(String number_account) {
        this.number_account = number_account;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
        Freecom_ecc11 freecom_ecc11 = (Freecom_ecc11) o;
        if(freecom_ecc11.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_ecc11.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_ecc11{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", type_operation='" + type_operation + "'" +
            ", number_account='" + number_account + "'" +
            ", subtotal='" + subtotal + "'" +
            ", total='" + total + "'" +
            '}';
    }
}
