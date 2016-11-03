package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_destruction_certificate.
 */
@Entity
@Table(name = "com_destruction_certificate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_destruction_certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Size(max = 20)
    @Column(name = "numfoldesveh", length = 20, nullable = false)
    private String numfoldesveh;

    @NotNull
    @Size(max = 50)
    @Column(name = "brand", length = 50, nullable = false)
    private String brand;

    @NotNull
    @Size(max = 50)
    @Column(name = "class_dc", length = 50, nullable = false)
    private String class_dc;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "model")
    private String model;

    @Column(name = "niv")
    private String niv;

    @Size(max = 17)
    @Column(name = "no_serie", length = 17)
    private String no_serie;

    @NotNull
    @Size(max = 10)
    @Column(name = "number_plates", length = 10, nullable = false)
    private String number_plates;

    @Size(max = 17)
    @Column(name = "number_engine", length = 17)
    private String number_engine;

    @NotNull
    @Size(max = 40)
    @Column(name = "numfoltarjcir", length = 40, nullable = false)
    private String numfoltarjcir;

    @ManyToOne
    private Cfdi cfdi;

    @ManyToOne
    private C_type_series c_type_series;

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

    public String getNumfoldesveh() {
        return numfoldesveh;
    }

    public void setNumfoldesveh(String numfoldesveh) {
        this.numfoldesveh = numfoldesveh;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getClass_dc() {
        return class_dc;
    }

    public void setClass_dc(String class_dc) {
        this.class_dc = class_dc;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNiv() {
        return niv;
    }

    public void setNiv(String niv) {
        this.niv = niv;
    }

    public String getNo_serie() {
        return no_serie;
    }

    public void setNo_serie(String no_serie) {
        this.no_serie = no_serie;
    }

    public String getNumber_plates() {
        return number_plates;
    }

    public void setNumber_plates(String number_plates) {
        this.number_plates = number_plates;
    }

    public String getNumber_engine() {
        return number_engine;
    }

    public void setNumber_engine(String number_engine) {
        this.number_engine = number_engine;
    }

    public String getNumfoltarjcir() {
        return numfoltarjcir;
    }

    public void setNumfoltarjcir(String numfoltarjcir) {
        this.numfoltarjcir = numfoltarjcir;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    public C_type_series getC_type_series() {
        return c_type_series;
    }

    public void setC_type_series(C_type_series c_type_series) {
        this.c_type_series = c_type_series;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_destruction_certificate com_destruction_certificate = (Com_destruction_certificate) o;
        if(com_destruction_certificate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_destruction_certificate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_destruction_certificate{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", numfoldesveh='" + numfoldesveh + "'" +
            ", brand='" + brand + "'" +
            ", class_dc='" + class_dc + "'" +
            ", year='" + year + "'" +
            ", model='" + model + "'" +
            ", niv='" + niv + "'" +
            ", no_serie='" + no_serie + "'" +
            ", number_plates='" + number_plates + "'" +
            ", number_engine='" + number_engine + "'" +
            ", numfoltarjcir='" + numfoltarjcir + "'" +
            '}';
    }
}
