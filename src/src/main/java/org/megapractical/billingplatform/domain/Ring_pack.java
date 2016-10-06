package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Ring_pack.
 */
@Entity
@Table(name = "ring_pack")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ring_pack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "currency", length = 255, nullable = false)
    private String currency;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @NotNull
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Size(max = 255)
    @Column(name = "reference_code", length = 255, nullable = false)
    private String reference_code;

    @NotNull
    @Column(name = "rings", nullable = false)
    private Integer rings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }

    public Integer getRings() {
        return rings;
    }

    public void setRings(Integer rings) {
        this.rings = rings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ring_pack ring_pack = (Ring_pack) o;
        if(ring_pack.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ring_pack.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ring_pack{" +
            "id=" + id +
            ", currency='" + currency + "'" +
            ", description='" + description + "'" +
            ", price='" + price + "'" +
            ", reference_code='" + reference_code + "'" +
            ", rings='" + rings + "'" +
            '}';
    }
}
