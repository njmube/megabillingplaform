package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_.
 */
@Entity
@Table(name = "freecom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_ implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "street", length = 100, nullable = false)
    private String street;

    @Size(max = 55)
    @Column(name = "no_ext", length = 55)
    private String no_ext;

    @Size(max = 55)
    @Column(name = "no_int", length = 55)
    private String no_int;

    @Size(max = 120)
    @Column(name = "locate", length = 120)
    private String locate;

    @Size(max = 250)
    @Column(name = "reference", length = 250)
    private String reference;

    @Size(max = 40)
    @Column(name = "numregidtrib", length = 40)
    private String numregidtrib;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "curp")
    private String curp;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private C_country c_country;

    @ManyToOne
    private C_state c_state;

    @ManyToOne
    private C_municipality c_municipality;

    @ManyToOne
    private C_colony c_colony;

    @ManyToOne
    private C_zip_code c_zip_code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNo_ext() {
        return no_ext;
    }

    public void setNo_ext(String no_ext) {
        this.no_ext = no_ext;
    }

    public String getNo_int() {
        return no_int;
    }

    public void setNo_int(String no_int) {
        this.no_int = no_int;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNumregidtrib() {
        return numregidtrib;
    }

    public void setNumregidtrib(String numregidtrib) {
        this.numregidtrib = numregidtrib;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public C_country getC_country() {
        return c_country;
    }

    public void setC_country(C_country c_country) {
        this.c_country = c_country;
    }

    public C_state getC_state() {
        return c_state;
    }

    public void setC_state(C_state c_state) {
        this.c_state = c_state;
    }

    public C_municipality getC_municipality() {
        return c_municipality;
    }

    public void setC_municipality(C_municipality c_municipality) {
        this.c_municipality = c_municipality;
    }

    public C_colony getC_colony() {
        return c_colony;
    }

    public void setC_colony(C_colony c_colony) {
        this.c_colony = c_colony;
    }

    public C_zip_code getC_zip_code() {
        return c_zip_code;
    }

    public void setC_zip_code(C_zip_code c_zip_code) {
        this.c_zip_code = c_zip_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_ freecom_ = (Freecom_) o;
        if(freecom_.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_{" +
            "id=" + id +
            ", street='" + street + "'" +
            ", no_ext='" + no_ext + "'" +
            ", no_int='" + no_int + "'" +
            ", locate='" + locate + "'" +
            ", reference='" + reference + "'" +
            ", numregidtrib='" + numregidtrib + "'" +
            ", rfc='" + rfc + "'" +
            ", curp='" + curp + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
