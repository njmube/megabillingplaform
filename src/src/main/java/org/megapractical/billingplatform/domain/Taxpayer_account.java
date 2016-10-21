package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Taxpayer_account.
 */
@Entity
@Table(name = "taxpayer_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}$")
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @NotNull
    @Size(max = 100)
    @Column(name = "bussines_name", length = 100, nullable = false)
    private String bussines_name;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9])+([_\\.\\-]([a-zA-Z0-9])+)*@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fax")
    private String fax;

    @Column(name = "phone_1")
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @NotNull
    @Column(name = "accuracy", nullable = false)
    private Integer accuracy;

    @Column(name = "path_logo")
    private String path_logo;

    @Lob
    @Column(name = "file_logo")
    private byte[] file_logo;

    @Column(name = "file_logo_content_type")
    private String file_logoContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Tax_address tax_address;

    @ManyToOne
    private Taxpayer_certificate taxpayer_certificate;

    @ManyToOne
    private Type_taxpayer type_taxpayer;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "taxpayer_account_tax_regime",
               joinColumns = @JoinColumn(name="taxpayer_accounts_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="tax_regimes_id", referencedColumnName="ID"))
    private Set<Tax_regime> tax_regimes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "taxpayer_account_user",
               joinColumns = @JoinColumn(name="taxpayer_accounts_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="ID"))
    private Set<User> users = new HashSet<>();

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

    public String getBussines_name() {
        return bussines_name;
    }

    public void setBussines_name(String bussines_name) {
        this.bussines_name = bussines_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getPath_logo() {
        return path_logo;
    }

    public void setPath_logo(String path_logo) {
        this.path_logo = path_logo;
    }

    public byte[] getFile_logo() {
        return file_logo;
    }

    public void setFile_logo(byte[] file_logo) {
        this.file_logo = file_logo;
    }

    public String getFile_logoContentType() {
        return file_logoContentType;
    }

    public void setFile_logoContentType(String file_logoContentType) {
        this.file_logoContentType = file_logoContentType;
    }

    public Tax_address getTax_address() {
        return tax_address;
    }

    public void setTax_address(Tax_address tax_address) {
        this.tax_address = tax_address;
    }

    public Taxpayer_certificate getTaxpayer_certificate() {
        return taxpayer_certificate;
    }

    public void setTaxpayer_certificate(Taxpayer_certificate taxpayer_certificate) {
        this.taxpayer_certificate = taxpayer_certificate;
    }

    public Type_taxpayer getType_taxpayer() {
        return type_taxpayer;
    }

    public void setType_taxpayer(Type_taxpayer type_taxpayer) {
        this.type_taxpayer = type_taxpayer;
    }

    public Set<Tax_regime> getTax_regimes() {
        return tax_regimes;
    }

    public void setTax_regimes(Set<Tax_regime> tax_regimes) {
        this.tax_regimes = tax_regimes;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taxpayer_account taxpayer_account = (Taxpayer_account) o;
        if(taxpayer_account.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_account{" +
            "id=" + id +
            ", rfc='" + rfc + "'" +
            ", bussines_name='" + bussines_name + "'" +
            ", email='" + email + "'" +
            ", fax='" + fax + "'" +
            ", phone1='" + phone1 + "'" +
            ", phone2='" + phone2 + "'" +
            ", accuracy='" + accuracy + "'" +
            ", path_logo='" + path_logo + "'" +
            ", file_logo='" + file_logo + "'" +
            ", file_logoContentType='" + file_logoContentType + "'" +
            '}';
    }
}
