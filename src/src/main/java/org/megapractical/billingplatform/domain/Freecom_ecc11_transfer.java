package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_ecc11_transfer.
 */
@Entity
@Table(name = "freecom_ecc_11_transfer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_ecc11_transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 4)
    @Column(name = "type_tax", length = 4, nullable = false)
    private String type_tax;

    @NotNull
    @Column(name = "rate", precision=10, scale=6, nullable = false)
    private BigDecimal rate;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Freecom_ecc11_concept freecom_ecc_11_concept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType_tax() {
        return type_tax;
    }

    public void setType_tax(String type_tax) {
        this.type_tax = type_tax;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Freecom_ecc11_concept getFreecom_ecc_11_concept() {
        return freecom_ecc_11_concept;
    }

    public void setFreecom_ecc_11_concept(Freecom_ecc11_concept freecom_ecc_11_concept) {
        this.freecom_ecc_11_concept = freecom_ecc_11_concept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_ecc11_transfer freecom_ecc11_transfer = (Freecom_ecc11_transfer) o;
        if(freecom_ecc11_transfer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_ecc11_transfer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_ecc11_transfer{" +
            "id=" + id +
            ", type_tax='" + type_tax + "'" +
            ", rate='" + rate + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
