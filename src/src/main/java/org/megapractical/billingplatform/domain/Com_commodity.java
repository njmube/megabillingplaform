package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_commodity.
 */
@Entity
@Table(name = "com_commodity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_commodity implements Serializable {

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
    private Com_foreign_trade com_foreign_trade;

    @ManyToOne
    private Com_tariff_fraction com_tariff_fraction;

    @ManyToOne
    private Com_custom_unit com_custom_unit;

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

    public Com_foreign_trade getCom_foreign_trade() {
        return com_foreign_trade;
    }

    public void setCom_foreign_trade(Com_foreign_trade com_foreign_trade) {
        this.com_foreign_trade = com_foreign_trade;
    }

    public Com_tariff_fraction getCom_tariff_fraction() {
        return com_tariff_fraction;
    }

    public void setCom_tariff_fraction(Com_tariff_fraction com_tariff_fraction) {
        this.com_tariff_fraction = com_tariff_fraction;
    }

    public Com_custom_unit getCom_custom_unit() {
        return com_custom_unit;
    }

    public void setCom_custom_unit(Com_custom_unit com_custom_unit) {
        this.com_custom_unit = com_custom_unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_commodity com_commodity = (Com_commodity) o;
        if(com_commodity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_commodity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_commodity{" +
            "id=" + id +
            ", noidentification='" + noidentification + "'" +
            ", customs_quantity='" + customs_quantity + "'" +
            ", customs_unit_value='" + customs_unit_value + "'" +
            ", dollar_value='" + dollar_value + "'" +
            '}';
    }
}
