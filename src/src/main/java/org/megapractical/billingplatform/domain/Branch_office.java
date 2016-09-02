package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Branch_office.
 */
@Entity
@Table(name = "branch_office")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Branch_office implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "bussines_name", nullable = false)
    private String bussines_name;

    @ManyToOne
    private Tax_address tax_address;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBussines_name() {
        return bussines_name;
    }

    public void setBussines_name(String bussines_name) {
        this.bussines_name = bussines_name;
    }

    public Tax_address getTax_address() {
        return tax_address;
    }

    public void setTax_address(Tax_address tax_address) {
        this.tax_address = tax_address;
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
        Branch_office branch_office = (Branch_office) o;
        if(branch_office.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, branch_office.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Branch_office{" +
            "id=" + id +
            ", bussines_name='" + bussines_name + "'" +
            '}';
    }
}
