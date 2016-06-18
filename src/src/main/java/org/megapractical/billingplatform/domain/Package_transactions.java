package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Package_transactions.
 */
@Entity
@Table(name = "package_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Package_transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @Column(name = "expiry_time", precision=10, scale=2)
    private BigDecimal expiry_time;

    @Column(name = "unit_time")
    private String unit_time;

    @ManyToOne
    private Package_state package_state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getExpiry_time() {
        return expiry_time;
    }

    public void setExpiry_time(BigDecimal expiry_time) {
        this.expiry_time = expiry_time;
    }

    public String getUnit_time() {
        return unit_time;
    }

    public void setUnit_time(String unit_time) {
        this.unit_time = unit_time;
    }

    public Package_state getPackage_state() {
        return package_state;
    }

    public void setPackage_state(Package_state package_state) {
        this.package_state = package_state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Package_transactions package_transactions = (Package_transactions) o;
        if(package_transactions.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, package_transactions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Package_transactions{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", price='" + price + "'" +
            ", expiry_time='" + expiry_time + "'" +
            ", unit_time='" + unit_time + "'" +
            '}';
    }
}
