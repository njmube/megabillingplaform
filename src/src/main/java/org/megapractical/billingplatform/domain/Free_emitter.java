package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Free_emitter.
 */
@Entity
@Table(name = "free_emitter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_emitter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "num_int")
    private String num_int;

    @Column(name = "num_ext")
    private String num_ext;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @ManyToOne
    private Tax_regime tax_regime;

    @ManyToOne
    private C_country c_country;

    @ManyToOne
    private C_state c_state;

    @ManyToOne
    private C_municipality c_municipality;

    @ManyToOne
    private C_location c_location;

    @ManyToOne
    private C_colony c_colony;

    @ManyToOne
    private C_zip_code c_zip_code;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Free_digital_certificate free_digital_certificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNum_int() {
        return num_int;
    }

    public void setNum_int(String num_int) {
        this.num_int = num_int;
    }

    public String getNum_ext() {
        return num_ext;
    }

    public void setNum_ext(String num_ext) {
        this.num_ext = num_ext;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Tax_regime getTax_regime() {
        return tax_regime;
    }

    public void setTax_regime(Tax_regime tax_regime) {
        this.tax_regime = tax_regime;
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

    public C_location getC_location() {
        return c_location;
    }

    public void setC_location(C_location c_location) {
        this.c_location = c_location;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Free_digital_certificate getFree_digital_certificate() {
        return free_digital_certificate;
    }

    public void setFree_digital_certificate(Free_digital_certificate free_digital_certificate) {
        this.free_digital_certificate = free_digital_certificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Free_emitter free_emitter = (Free_emitter) o;
        if(free_emitter.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, free_emitter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Free_emitter{" +
            "id=" + id +
            ", reference='" + reference + "'" +
            ", num_int='" + num_int + "'" +
            ", num_ext='" + num_ext + "'" +
            ", street='" + street + "'" +
            '}';
    }
}
