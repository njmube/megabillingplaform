package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_used_vehicle.
 */
@Entity
@Table(name = "com_used_vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_used_vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "acquisition_amount", precision=10, scale=2, nullable = false)
    private BigDecimal acquisition_amount;

    @NotNull
    @Column(name = "monto_enajenacion", precision=10, scale=2, nullable = false)
    private BigDecimal monto_enajenacion;

    @NotNull
    @Size(max = 50)
    @Column(name = "key_vehicle", length = 50, nullable = false)
    private String key_vehicle;

    @NotNull
    @Size(max = 50)
    @Column(name = "brand", length = 50, nullable = false)
    private String brand;

    @NotNull
    @Size(max = 50)
    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @NotNull
    @Size(max = 50)
    @Column(name = "model", length = 50, nullable = false)
    private String model;

    @Size(max = 17)
    @Column(name = "number_engine", length = 17)
    private String number_engine;

    @NotNull
    @Size(max = 17)
    @Column(name = "no_serie", length = 17, nullable = false)
    private String no_serie;

    @NotNull
    @Size(max = 17)
    @Column(name = "niv", length = 17, nullable = false)
    private String niv;

    @Column(name = "value", precision=10, scale=2)
    private BigDecimal value;

    @ManyToOne
    private Cfdi cfdi;

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

    public BigDecimal getAcquisition_amount() {
        return acquisition_amount;
    }

    public void setAcquisition_amount(BigDecimal acquisition_amount) {
        this.acquisition_amount = acquisition_amount;
    }

    public BigDecimal getMonto_enajenacion() {
        return monto_enajenacion;
    }

    public void setMonto_enajenacion(BigDecimal monto_enajenacion) {
        this.monto_enajenacion = monto_enajenacion;
    }

    public String getKey_vehicle() {
        return key_vehicle;
    }

    public void setKey_vehicle(String key_vehicle) {
        this.key_vehicle = key_vehicle;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber_engine() {
        return number_engine;
    }

    public void setNumber_engine(String number_engine) {
        this.number_engine = number_engine;
    }

    public String getNo_serie() {
        return no_serie;
    }

    public void setNo_serie(String no_serie) {
        this.no_serie = no_serie;
    }

    public String getNiv() {
        return niv;
    }

    public void setNiv(String niv) {
        this.niv = niv;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_used_vehicle com_used_vehicle = (Com_used_vehicle) o;
        if(com_used_vehicle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_used_vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_used_vehicle{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", acquisition_amount='" + acquisition_amount + "'" +
            ", monto_enajenacion='" + monto_enajenacion + "'" +
            ", key_vehicle='" + key_vehicle + "'" +
            ", brand='" + brand + "'" +
            ", type='" + type + "'" +
            ", model='" + model + "'" +
            ", number_engine='" + number_engine + "'" +
            ", no_serie='" + no_serie + "'" +
            ", niv='" + niv + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
