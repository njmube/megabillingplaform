package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_dataunenajenante.
 */
@Entity
@Table(name = "freecom_dataunenajenante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_dataunenajenante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "mother_last_name")
    private String mother_last_name;

    @NotNull
    @Pattern(regexp = "undefined")
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @NotNull
    @Column(name = "curp", nullable = false)
    private String curp;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_data_enajenante freecom_data_enajenante;

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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMother_last_name() {
        return mother_last_name;
    }

    public void setMother_last_name(String mother_last_name) {
        this.mother_last_name = mother_last_name;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Freecom_data_enajenante getFreecom_data_enajenante() {
        return freecom_data_enajenante;
    }

    public void setFreecom_data_enajenante(Freecom_data_enajenante freecom_data_enajenante) {
        this.freecom_data_enajenante = freecom_data_enajenante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_dataunenajenante freecom_dataunenajenante = (Freecom_dataunenajenante) o;
        if(freecom_dataunenajenante.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_dataunenajenante.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_dataunenajenante{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", last_name='" + last_name + "'" +
            ", mother_last_name='" + mother_last_name + "'" +
            ", rfc='" + rfc + "'" +
            ", curp='" + curp + "'" +
            '}';
    }
}
