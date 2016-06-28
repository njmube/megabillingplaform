package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Free_receiver.
 */
@Entity
@Table(name = "free_receiver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_receiver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 12, max = 13)
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?$")
    @Column(name = "rfc", length = 13, nullable = false)
    private String rfc;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "business_name", length = 50, nullable = false)
    private String business_name;

    @Size(min = 6, max = 254)
    @Pattern(regexp = "^([a-zA-Z0-9])+([_\\.\\-]([a-zA-Z0-9])+)*@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$")
    @Column(name = "email", length = 254)
    private String email;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date;

    @Column(name = "street")
    private String street;

    @Column(name = "no_ext")
    private String no_ext;

    @Column(name = "no_int")
    private String no_int;

    @Column(name = "reference")
    private String reference;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ZonedDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(ZonedDateTime create_date) {
        this.create_date = create_date;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Free_receiver free_receiver = (Free_receiver) o;
        if(free_receiver.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, free_receiver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Free_receiver{" +
            "id=" + id +
            ", rfc='" + rfc + "'" +
            ", business_name='" + business_name + "'" +
            ", email='" + email + "'" +
            ", activated='" + activated + "'" +
            ", create_date='" + create_date + "'" +
            ", street='" + street + "'" +
            ", no_ext='" + no_ext + "'" +
            ", no_int='" + no_int + "'" +
            ", reference='" + reference + "'" +
            '}';
    }
}
