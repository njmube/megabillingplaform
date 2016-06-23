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

    @NotNull
    @Size(min = 12, max = 13)
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?")
    @Column(name = "rfc", length = 13, nullable = false)
    private String rfc;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(min = 6, max = 254)
    @Pattern(regexp = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$")
    @Column(name = "email", length = 254, nullable = false)
    private String email;

    @NotNull
    @Lob
    @Column(name = "path_certificate", nullable = false)
    private byte[] path_certificate;

    @Column(name = "path_certificate_content_type", nullable = false)    
    private String path_certificateContentType;

    @NotNull
    @Lob
    @Column(name = "private_key", nullable = false)
    private byte[] private_key;

    @Column(name = "private_key_content_type", nullable = false)    
    private String private_keyContentType;

    @Column(name = "reference")
    private String reference;

    @Column(name = "num_in")
    private String num_in;

    @Column(name = "num_out")
    private String num_out;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPath_certificate() {
        return path_certificate;
    }

    public void setPath_certificate(byte[] path_certificate) {
        this.path_certificate = path_certificate;
    }

    public String getPath_certificateContentType() {
        return path_certificateContentType;
    }

    public void setPath_certificateContentType(String path_certificateContentType) {
        this.path_certificateContentType = path_certificateContentType;
    }

    public byte[] getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(byte[] private_key) {
        this.private_key = private_key;
    }

    public String getPrivate_keyContentType() {
        return private_keyContentType;
    }

    public void setPrivate_keyContentType(String private_keyContentType) {
        this.private_keyContentType = private_keyContentType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNum_in() {
        return num_in;
    }

    public void setNum_in(String num_in) {
        this.num_in = num_in;
    }

    public String getNum_out() {
        return num_out;
    }

    public void setNum_out(String num_out) {
        this.num_out = num_out;
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
            ", rfc='" + rfc + "'" +
            ", name='" + name + "'" +
            ", email='" + email + "'" +
            ", path_certificate='" + path_certificate + "'" +
            ", path_certificateContentType='" + path_certificateContentType + "'" +
            ", private_key='" + private_key + "'" +
            ", private_keyContentType='" + private_keyContentType + "'" +
            ", reference='" + reference + "'" +
            ", num_in='" + num_in + "'" +
            ", num_out='" + num_out + "'" +
            ", street='" + street + "'" +
            '}';
    }
}
