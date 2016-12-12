package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_dataunacquiring.
 */
@Entity
@Table(name = "freecom_dataunacquiring")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_dataunacquiring implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "mother_last_name")
    private String mother_last_name;

    @NotNull
    @Pattern(regexp = "undefined")
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @Column(name = "curp")
    private String curp;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_acquiring_data freecom_acquiring_data;

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

    public Freecom_acquiring_data getFreecom_acquiring_data() {
        return freecom_acquiring_data;
    }

    public void setFreecom_acquiring_data(Freecom_acquiring_data freecom_acquiring_data) {
        this.freecom_acquiring_data = freecom_acquiring_data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_dataunacquiring freecom_dataunacquiring = (Freecom_dataunacquiring) o;
        if(freecom_dataunacquiring.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_dataunacquiring.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_dataunacquiring{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", last_name='" + last_name + "'" +
            ", mother_last_name='" + mother_last_name + "'" +
            ", rfc='" + rfc + "'" +
            ", curp='" + curp + "'" +
            '}';
    }
}
