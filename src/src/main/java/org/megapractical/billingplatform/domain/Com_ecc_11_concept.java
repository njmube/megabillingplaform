package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Com_ecc_11_concept.
 */
@Entity
@Table(name = "com_ecc_11_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_ecc_11_concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @NotNull
    @Size(max = 10)
    @Column(name = "key_station", length = 10, nullable = false)
    private String key_station;

    @NotNull
    @Column(name = "quantity", precision=10, scale=2, nullable = false)
    private BigDecimal quantity;

    @Size(max = 25)
    @Column(name = "unit", length = 25)
    private String unit;

    @NotNull
    @Size(max = 300)
    @Column(name = "fuel_name", length = 300, nullable = false)
    private String fuel_name;

    @NotNull
    @Size(max = 50)
    @Column(name = "folio_operation", length = 50, nullable = false)
    private String folio_operation;

    @NotNull
    @Column(name = "unit_value", precision=10, scale=2, nullable = false)
    private BigDecimal unit_value;

    @Column(name = "amount", precision=10, scale=2)
    private BigDecimal amount;

    @ManyToOne
    private Com_ecc_11 com_ecc_11;

    @ManyToOne
    private Com_product_key com_product_key;

    @ManyToOne
    private C_tar c_tar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getKey_station() {
        return key_station;
    }

    public void setKey_station(String key_station) {
        this.key_station = key_station;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFuel_name() {
        return fuel_name;
    }

    public void setFuel_name(String fuel_name) {
        this.fuel_name = fuel_name;
    }

    public String getFolio_operation() {
        return folio_operation;
    }

    public void setFolio_operation(String folio_operation) {
        this.folio_operation = folio_operation;
    }

    public BigDecimal getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(BigDecimal unit_value) {
        this.unit_value = unit_value;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Com_ecc_11 getCom_ecc_11() {
        return com_ecc_11;
    }

    public void setCom_ecc_11(Com_ecc_11 com_ecc_11) {
        this.com_ecc_11 = com_ecc_11;
    }

    public Com_product_key getCom_product_key() {
        return com_product_key;
    }

    public void setCom_product_key(Com_product_key com_product_key) {
        this.com_product_key = com_product_key;
    }

    public C_tar getC_tar() {
        return c_tar;
    }

    public void setC_tar(C_tar c_tar) {
        this.c_tar = c_tar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_ecc_11_concept com_ecc_11_concept = (Com_ecc_11_concept) o;
        if(com_ecc_11_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_ecc_11_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_ecc_11_concept{" +
            "id=" + id +
            ", identifier='" + identifier + "'" +
            ", date='" + date + "'" +
            ", rfc='" + rfc + "'" +
            ", key_station='" + key_station + "'" +
            ", quantity='" + quantity + "'" +
            ", unit='" + unit + "'" +
            ", fuel_name='" + fuel_name + "'" +
            ", folio_operation='" + folio_operation + "'" +
            ", unit_value='" + unit_value + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
