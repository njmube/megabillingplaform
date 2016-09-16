package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Taxpayer_transactions.
 */
@Entity
@Table(name = "taxpayer_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "transactions_available", nullable = false)
    private Integer transactions_available;

    @NotNull
    @Column(name = "transactions_spent", nullable = false)
    private Integer transactions_spent;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTransactions_available() {
        return transactions_available;
    }

    public void setTransactions_available(Integer transactions_available) {
        this.transactions_available = transactions_available;
    }

    public Integer getTransactions_spent() {
        return transactions_spent;
    }

    public void setTransactions_spent(Integer transactions_spent) {
        this.transactions_spent = transactions_spent;
    }

    public Taxpayer_account getTaxpayer_account() {
        return taxpayer_account;
    }

    public void setTaxpayer_account(Taxpayer_account taxpayer_account) {
        this.taxpayer_account = taxpayer_account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taxpayer_transactions taxpayer_transactions = (Taxpayer_transactions) o;
        if(taxpayer_transactions.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_transactions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_transactions{" +
            "id=" + id +
            ", transactions_available='" + transactions_available + "'" +
            ", transactions_spent='" + transactions_spent + "'" +
            '}';
    }
}
