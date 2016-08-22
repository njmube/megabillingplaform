package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_commodity.
 */
@Entity
@Table(name = "freecom_commodity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "noidentification", length = 100, nullable = false)
    private String noidentification;

    @Column(name = "customs_quantity", precision=10, scale=2)
    private BigDecimal customs_quantity;

    @Column(name = "customs_unit_value", precision=10, scale=2)
    private BigDecimal customs_unit_value;

    @NotNull
    @Column(name = "dollar_value", precision=10, scale=2, nullable = false)
    private BigDecimal dollar_value;

    @ManyToOne
    private Freecom_foreign_trade freecom_foreign_trade;

    @ManyToOne
    private Freecom_tariff_fraction freecom_tariff_fraction;

    @ManyToOne
    private Freecom_custom_unit freecom_custom_unit;

    @ManyToOne
    private Freecom_specific_descriptions freecom_specific_descriptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoidentification() {
        return noidentification;
    }

    public void setNoidentification(String noidentification) {
        this.noidentification = noidentification;
    }

    public BigDecimal getCustoms_quantity() {
        return customs_quantity;
    }

    public void setCustoms_quantity(BigDecimal customs_quantity) {
        this.customs_quantity = customs_quantity;
    }

    public BigDecimal getCustoms_unit_value() {
        return customs_unit_value;
    }

    public void setCustoms_unit_value(BigDecimal customs_unit_value) {
        this.customs_unit_value = customs_unit_value;
    }

    public BigDecimal getDollar_value() {
        return dollar_value;
    }

    public void setDollar_value(BigDecimal dollar_value) {
        this.dollar_value = dollar_value;
    }

    public Freecom_foreign_trade getFreecom_foreign_trade() {
        return freecom_foreign_trade;
    }

    public void setFreecom_foreign_trade(Freecom_foreign_trade freecom_foreign_trade) {
        this.freecom_foreign_trade = freecom_foreign_trade;
    }

    public Freecom_tariff_fraction getFreecom_tariff_fraction() {
        return freecom_tariff_fraction;
    }

    public void setFreecom_tariff_fraction(Freecom_tariff_fraction freecom_tariff_fraction) {
        this.freecom_tariff_fraction = freecom_tariff_fraction;
    }

    public Freecom_custom_unit getFreecom_custom_unit() {
        return freecom_custom_unit;
    }

    public void setFreecom_custom_unit(Freecom_custom_unit freecom_custom_unit) {
        this.freecom_custom_unit = freecom_custom_unit;
    }

    public Freecom_specific_descriptions getFreecom_specific_descriptions() {
        return freecom_specific_descriptions;
    }

    public void setFreecom_specific_descriptions(Freecom_specific_descriptions freecom_specific_descriptions) {
        this.freecom_specific_descriptions = freecom_specific_descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_commodity freecom_commodity = (Freecom_commodity) o;
        if(freecom_commodity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_commodity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_commodity{" +
            "id=" + id +
            ", noidentification='" + noidentification + "'" +
            ", customs_quantity='" + customs_quantity + "'" +
            ", customs_unit_value='" + customs_unit_value + "'" +
            ", dollar_value='" + dollar_value + "'" +
            '}';
    }
}
