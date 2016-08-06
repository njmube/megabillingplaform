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
 * A Freecom_concept_fuel.
 */
@Entity
@Table(name = "freecom_concept_fuel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_concept_fuel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @NotNull
    @Column(name = "date_expedition", nullable = false)
    private ZonedDateTime date_expedition;

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

    @NotNull
    @Column(name = "fuel_name", nullable = false)
    private String fuel_name;

    @NotNull
    @Column(name = "folio_operation", nullable = false)
    private String folio_operation;

    @NotNull
    @Column(name = "unit_value", precision=10, scale=2, nullable = false)
    private BigDecimal unit_value;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Freecom_fuel_consumption freecom_fuel_consumption;

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

    public ZonedDateTime getDate_expedition() {
        return date_expedition;
    }

    public void setDate_expedition(ZonedDateTime date_expedition) {
        this.date_expedition = date_expedition;
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

    public Freecom_fuel_consumption getFreecom_fuel_consumption() {
        return freecom_fuel_consumption;
    }

    public void setFreecom_fuel_consumption(Freecom_fuel_consumption freecom_fuel_consumption) {
        this.freecom_fuel_consumption = freecom_fuel_consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_concept_fuel freecom_concept_fuel = (Freecom_concept_fuel) o;
        if(freecom_concept_fuel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_concept_fuel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_concept_fuel{" +
            "id=" + id +
            ", identifier='" + identifier + "'" +
            ", date_expedition='" + date_expedition + "'" +
            ", rfc='" + rfc + "'" +
            ", key_station='" + key_station + "'" +
            ", quantity='" + quantity + "'" +
            ", fuel_name='" + fuel_name + "'" +
            ", folio_operation='" + folio_operation + "'" +
            ", unit_value='" + unit_value + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
