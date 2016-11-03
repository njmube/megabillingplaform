package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_charge.
 */
@Entity
@Table(name = "com_charge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_charge implements Serializable {

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
    private Com_airline com_airline;

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

    public Com_airline getCom_airline() {
        return com_airline;
    }

    public void setCom_airline(Com_airline com_airline) {
        this.com_airline = com_airline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_charge com_charge = (Com_charge) o;
        if(com_charge.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_charge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_charge{" +
            "id=" + id +
            ", codecharge='" + codecharge + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
