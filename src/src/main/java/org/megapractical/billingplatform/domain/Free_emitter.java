package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @NotNull
    @Size(min = 12, max = 13)
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}$")
    @Column(name = "rfc", length = 13, nullable = false)
    private String rfc;

    @NotNull
    @Size(min = 6, max = 254)
    @Pattern(regexp = "^([a-zA-Z0-9])+([_\\.\\-]([a-zA-Z0-9])+)*@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$")
    @Column(name = "email", length = 254, nullable = false)
    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "bussines_name", length = 50, nullable = false)
    private String bussines_name;

    @Column(name = "location")
    private String location;

    @Column(name = "intersection")
    private String intersection;

    @Column(name = "fax")
    private String fax;

    @NotNull
    @Size(min = 1, max = 15)
    @Pattern(regexp = "^[0-9]{1,15}$")
    @Column(name = "phone_1", length = 15, nullable = false)
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @Column(name = "path_certificate")
    private String path_certificate;

    @Column(name = "path_key")
    private String path_key;

    @Column(name = "path_logo")
    private String path_logo;

    @Lob
    @Column(name = "filecertificate")
    private byte[] filecertificate;

    @Column(name = "filecertificate_content_type")    
    private String filecertificateContentType;

    @Lob
    @Column(name = "filekey")
    private byte[] filekey;

    @Column(name = "filekey_content_type")    
    private String filekeyContentType;

    @Lob
    @Column(name = "filelogo")
    private byte[] filelogo;

    @Column(name = "filelogo_content_type")    
    private String filelogoContentType;

    @NotNull
    @Column(name = "accuracy", nullable = false)
    private Integer accuracy;

    @Column(name = "valid_certificate")
    private Boolean valid_certificate;

    @Column(name = "info_certificate")
    private String info_certificate;

    @Column(name = "date_certificate")
    private LocalDate date_certificate;

    @Column(name = "pass_certificate")
    private String pass_certificate;

    @ManyToOne
    private Tax_regime tax_regime;

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

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private Type_taxpayer type_taxpayer;

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

    public ZonedDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(ZonedDateTime create_date) {
        this.create_date = create_date;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBussines_name() {
        return bussines_name;
    }

    public void setBussines_name(String bussines_name) {
        this.bussines_name = bussines_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIntersection() {
        return intersection;
    }

    public void setIntersection(String intersection) {
        this.intersection = intersection;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPath_certificate() {
        return path_certificate;
    }

    public void setPath_certificate(String path_certificate) {
        this.path_certificate = path_certificate;
    }

    public String getPath_key() {
        return path_key;
    }

    public void setPath_key(String path_key) {
        this.path_key = path_key;
    }

    public String getPath_logo() {
        return path_logo;
    }

    public void setPath_logo(String path_logo) {
        this.path_logo = path_logo;
    }

    public byte[] getFilecertificate() {
        return filecertificate;
    }

    public void setFilecertificate(byte[] filecertificate) {
        this.filecertificate = filecertificate;
    }

    public String getFilecertificateContentType() {
        return filecertificateContentType;
    }

    public void setFilecertificateContentType(String filecertificateContentType) {
        this.filecertificateContentType = filecertificateContentType;
    }

    public byte[] getFilekey() {
        return filekey;
    }

    public void setFilekey(byte[] filekey) {
        this.filekey = filekey;
    }

    public String getFilekeyContentType() {
        return filekeyContentType;
    }

    public void setFilekeyContentType(String filekeyContentType) {
        this.filekeyContentType = filekeyContentType;
    }

    public byte[] getFilelogo() {
        return filelogo;
    }

    public void setFilelogo(byte[] filelogo) {
        this.filelogo = filelogo;
    }

    public String getFilelogoContentType() {
        return filelogoContentType;
    }

    public void setFilelogoContentType(String filelogoContentType) {
        this.filelogoContentType = filelogoContentType;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Boolean isValid_certificate() {
        return valid_certificate;
    }

    public void setValid_certificate(Boolean valid_certificate) {
        this.valid_certificate = valid_certificate;
    }

    public String getInfo_certificate() {
        return info_certificate;
    }

    public void setInfo_certificate(String info_certificate) {
        this.info_certificate = info_certificate;
    }

    public LocalDate getDate_certificate() {
        return date_certificate;
    }

    public void setDate_certificate(LocalDate date_certificate) {
        this.date_certificate = date_certificate;
    }

    public String getPass_certificate() {
        return pass_certificate;
    }

    public void setPass_certificate(String pass_certificate) {
        this.pass_certificate = pass_certificate;
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

    public Type_taxpayer getType_taxpayer() {
        return type_taxpayer;
    }

    public void setType_taxpayer(Type_taxpayer type_taxpayer) {
        this.type_taxpayer = type_taxpayer;
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
            ", create_date='" + create_date + "'" +
            ", activated='" + activated + "'" +
            ", rfc='" + rfc + "'" +
            ", email='" + email + "'" +
            ", bussines_name='" + bussines_name + "'" +
            ", location='" + location + "'" +
            ", intersection='" + intersection + "'" +
            ", fax='" + fax + "'" +
            ", phone1='" + phone1 + "'" +
            ", phone2='" + phone2 + "'" +
            ", path_certificate='" + path_certificate + "'" +
            ", path_key='" + path_key + "'" +
            ", path_logo='" + path_logo + "'" +
            ", filecertificate='" + filecertificate + "'" +
            ", filecertificateContentType='" + filecertificateContentType + "'" +
            ", filekey='" + filekey + "'" +
            ", filekeyContentType='" + filekeyContentType + "'" +
            ", filelogo='" + filelogo + "'" +
            ", filelogoContentType='" + filelogoContentType + "'" +
            ", accuracy='" + accuracy + "'" +
            ", valid_certificate='" + valid_certificate + "'" +
            ", info_certificate='" + info_certificate + "'" +
            ", date_certificate='" + date_certificate + "'" +
            ", pass_certificate='" + pass_certificate + "'" +
            '}';
    }
}
