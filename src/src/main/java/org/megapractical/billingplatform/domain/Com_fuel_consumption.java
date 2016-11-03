package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_fuel_consumption.
 */
@Entity
@Table(name = "com_fuel_consumption")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_fuel_consumption implements Serializable {

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
    @Column(name = "account_number", nullable = false)
    private String account_number;

    @Column(name = "subtotal", precision=10, scale=2)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "total", precision=10, scale=2, nullable = false)
    private BigDecimal total;

    @ManyToOne
    private Cfdi cfdi;

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

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
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

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_fuel_consumption com_fuel_consumption = (Com_fuel_consumption) o;
        if(com_fuel_consumption.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_fuel_consumption.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_fuel_consumption{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", type_operation='" + type_operation + "'" +
            ", account_number='" + account_number + "'" +
            ", subtotal='" + subtotal + "'" +
            ", total='" + total + "'" +
            '}';
    }
}
