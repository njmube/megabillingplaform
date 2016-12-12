package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Freecom_data_operation.
 */
@Entity
@Table(name = "freecom_data_operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_data_operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "notarialinstrument", nullable = false)
    private Integer notarialinstrument;

    @NotNull
    @Column(name = "dateinstnotarial", nullable = false)
    private LocalDate dateinstnotarial;

    @NotNull
    @Column(name = "amountofoperation", precision=10, scale=2, nullable = false)
    private BigDecimal amountofoperation;

    @NotNull
    @Column(name = "subtotal", precision=10, scale=2, nullable = false)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "iva", precision=10, scale=2, nullable = false)
    private BigDecimal iva;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_public_notaries freecom_public_notaries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNotarialinstrument() {
        return notarialinstrument;
    }

    public void setNotarialinstrument(Integer notarialinstrument) {
        this.notarialinstrument = notarialinstrument;
    }

    public LocalDate getDateinstnotarial() {
        return dateinstnotarial;
    }

    public void setDateinstnotarial(LocalDate dateinstnotarial) {
        this.dateinstnotarial = dateinstnotarial;
    }

    public BigDecimal getAmountofoperation() {
        return amountofoperation;
    }

    public void setAmountofoperation(BigDecimal amountofoperation) {
        this.amountofoperation = amountofoperation;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Freecom_public_notaries getFreecom_public_notaries() {
        return freecom_public_notaries;
    }

    public void setFreecom_public_notaries(Freecom_public_notaries freecom_public_notaries) {
        this.freecom_public_notaries = freecom_public_notaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_data_operation freecom_data_operation = (Freecom_data_operation) o;
        if(freecom_data_operation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_data_operation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_data_operation{" +
            "id=" + id +
            ", notarialinstrument='" + notarialinstrument + "'" +
            ", dateinstnotarial='" + dateinstnotarial + "'" +
            ", amountofoperation='" + amountofoperation + "'" +
            ", subtotal='" + subtotal + "'" +
            ", iva='" + iva + "'" +
            '}';
    }
}
