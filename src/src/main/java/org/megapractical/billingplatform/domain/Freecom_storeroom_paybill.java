package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_storeroom_paybill.
 */
@Entity
@Table(name = "freecom_storeroom_paybill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_storeroom_paybill implements Serializable {

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

    @Size(max = 20)
    @Column(name = "employer_registration", length = 20)
    private String employer_registration;

    @NotNull
    @Size(max = 20)
    @Column(name = "account_number", length = 20, nullable = false)
    private String account_number;

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

    public String getEmployer_registration() {
        return employer_registration;
    }

    public void setEmployer_registration(String employer_registration) {
        this.employer_registration = employer_registration;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
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
        Freecom_storeroom_paybill freecom_storeroom_paybill = (Freecom_storeroom_paybill) o;
        if(freecom_storeroom_paybill.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_storeroom_paybill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_storeroom_paybill{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", type_operation='" + type_operation + "'" +
            ", employer_registration='" + employer_registration + "'" +
            ", account_number='" + account_number + "'" +
            ", total='" + total + "'" +
            '}';
    }
}
