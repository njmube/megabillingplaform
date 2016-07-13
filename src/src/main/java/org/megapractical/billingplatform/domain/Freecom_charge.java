package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_charge.
 */
@javax.persistence.Entity
@Table(name = "freecom_charge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_charge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "codecharge", nullable = false)
    private String codecharge;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Freecom_airline freecom_airline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodecharge() {
        return codecharge;
    }

    public void setCodecharge(String codecharge) {
        this.codecharge = codecharge;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Freecom_airline getFreecom_airline() {
        return freecom_airline;
    }

    public void setFreecom_airline(Freecom_airline freecom_airline) {
        this.freecom_airline = freecom_airline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_charge freecom_charge = (Freecom_charge) o;
        if(freecom_charge.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_charge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_charge{" +
            "id=" + id +
            ", codecharge='" + codecharge + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
