package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_beneficiary.
 */
@Entity
@Table(name = "com_beneficiary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_beneficiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "receiver_bank", length = 40, nullable = false)
    private String receiver_bank;

    @NotNull
    @Size(max = 40)
    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @NotNull
    @Column(name = "type_account", precision=10, scale=2, nullable = false)
    private BigDecimal type_account;

    @NotNull
    @Column(name = "account", precision=10, scale=2, nullable = false)
    private BigDecimal account;

    @NotNull
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @NotNull
    @Size(max = 40)
    @Column(name = "concept", length = 40, nullable = false)
    private String concept;

    @NotNull
    @Column(name = "iva", precision=10, scale=2, nullable = false)
    private BigDecimal iva;

    @NotNull
    @Column(name = "payment_amount", precision=10, scale=2, nullable = false)
    private BigDecimal payment_amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver_bank() {
        return receiver_bank;
    }

    public void setReceiver_bank(String receiver_bank) {
        this.receiver_bank = receiver_bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getType_account() {
        return type_account;
    }

    public void setType_account(BigDecimal type_account) {
        this.type_account = type_account;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(BigDecimal payment_amount) {
        this.payment_amount = payment_amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_beneficiary com_beneficiary = (Com_beneficiary) o;
        if(com_beneficiary.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_beneficiary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_beneficiary{" +
            "id=" + id +
            ", receiver_bank='" + receiver_bank + "'" +
            ", name='" + name + "'" +
            ", type_account='" + type_account + "'" +
            ", account='" + account + "'" +
            ", rfc='" + rfc + "'" +
            ", concept='" + concept + "'" +
            ", iva='" + iva + "'" +
            ", payment_amount='" + payment_amount + "'" +
            '}';
    }
}
