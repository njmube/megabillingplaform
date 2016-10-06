package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Transactions_history.
 */
@Entity
@Table(name = "transactions_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transactions_history implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date_transaction", nullable = false)
    private ZonedDateTime date_transaction;

    @Size(max = 10)
    @Column(name = "quantity", length = 10)
    private String quantity;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private C_system c_system;

    @ManyToOne
    private User user;

    @ManyToOne
    private Type_transaction type_transaction;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

    @ManyToOne
    private Ring_pack ring_pack;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate_transaction() {
        return date_transaction;
    }

    public void setDate_transaction(ZonedDateTime date_transaction) {
        this.date_transaction = date_transaction;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public C_system getC_system() {
        return c_system;
    }

    public void setC_system(C_system c_system) {
        this.c_system = c_system;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type_transaction getType_transaction() {
        return type_transaction;
    }

    public void setType_transaction(Type_transaction type_transaction) {
        this.type_transaction = type_transaction;
    }

    public Taxpayer_account getTaxpayer_account() {
        return taxpayer_account;
    }

    public void setTaxpayer_account(Taxpayer_account taxpayer_account) {
        this.taxpayer_account = taxpayer_account;
    }

    public Ring_pack getRing_pack() {
        return ring_pack;
    }

    public void setRing_pack(Ring_pack ring_pack) {
        this.ring_pack = ring_pack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transactions_history transactions_history = (Transactions_history) o;
        if(transactions_history.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transactions_history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transactions_history{" +
            "id=" + id +
            ", date_transaction='" + date_transaction + "'" +
            ", quantity='" + quantity + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
