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
 * A Freecom_ecc11_concept.
 */
@Entity
@Table(name = "freecom_ecc_11_concept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_ecc11_concept implements Serializable {

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
    private Freecom_ecc11 freecom_ecc_11;

    @ManyToOne
    private Freecom_product_key freecom_product_key;

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

    public Freecom_ecc11 getFreecom_ecc_11() {
        return freecom_ecc_11;
    }

    public void setFreecom_ecc_11(Freecom_ecc11 freecom_ecc_11) {
        this.freecom_ecc_11 = freecom_ecc_11;
    }

    public Freecom_product_key getFreecom_product_key() {
        return freecom_product_key;
    }

    public void setFreecom_product_key(Freecom_product_key freecom_product_key) {
        this.freecom_product_key = freecom_product_key;
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
        Freecom_ecc11_concept freecom_ecc11_concept = (Freecom_ecc11_concept) o;
        if(freecom_ecc11_concept.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_ecc11_concept.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_ecc11_concept{" +
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
            ", freecom_ecc11.id='" + freecom_ecc_11.getId() + "'" +
            '}';
    }
}
