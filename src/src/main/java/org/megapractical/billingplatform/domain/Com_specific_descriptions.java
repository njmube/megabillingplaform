package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_specific_descriptions.
 */
@Entity
@Table(name = "com_specific_descriptions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_specific_descriptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 35)
    @Column(name = "brand", length = 35, nullable = false)
    private String brand;

    @Size(max = 80)
    @Column(name = "model", length = 80)
    private String model;

    @Size(max = 50)
    @Column(name = "submodel", length = 50)
    private String submodel;

    @Size(max = 40)
    @Column(name = "serial_number", length = 40)
    private String serial_number;

    @ManyToOne
    private Com_commodity com_commodity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSubmodel() {
        return submodel;
    }

    public void setSubmodel(String submodel) {
        this.submodel = submodel;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public Com_commodity getCom_commodity() {
        return com_commodity;
    }

    public void setCom_commodity(Com_commodity com_commodity) {
        this.com_commodity = com_commodity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_specific_descriptions com_specific_descriptions = (Com_specific_descriptions) o;
        if(com_specific_descriptions.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_specific_descriptions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_specific_descriptions{" +
            "id=" + id +
            ", brand='" + brand + "'" +
            ", model='" + model + "'" +
            ", submodel='" + submodel + "'" +
            ", serial_number='" + serial_number + "'" +
            '}';
    }
}
