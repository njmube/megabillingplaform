package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_payer.
 */
@Entity
@Table(name = "freecom_payer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_payer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "emitter_bank", length = 40, nullable = false)
    private String emitter_bank;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmitter_bank() {
        return emitter_bank;
    }

    public void setEmitter_bank(String emitter_bank) {
        this.emitter_bank = emitter_bank;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_payer freecom_payer = (Freecom_payer) o;
        if(freecom_payer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_payer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_payer{" +
            "id=" + id +
            ", emitter_bank='" + emitter_bank + "'" +
            ", name='" + name + "'" +
            ", type_account='" + type_account + "'" +
            ", account='" + account + "'" +
            ", rfc='" + rfc + "'" +
            '}';
    }
}
