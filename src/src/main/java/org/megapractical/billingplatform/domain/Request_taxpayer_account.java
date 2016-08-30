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
 * A Request_taxpayer_account.
 */
@Entity
@Table(name = "request_taxpayer_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Request_taxpayer_account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_request")
    private ZonedDateTime date_request;

    @NotNull
    @Column(name = "firtsurname", nullable = false)
    private String firtsurname;

    @NotNull
    @Column(name = "secondsurname", nullable = false)
    private String secondsurname;

    @NotNull
    @Pattern(regexp = "^[0-9]{1,15}$")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9])+([_\\.\\-]([a-zA-Z0-9])+)*@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "date_born", nullable = false)
    private LocalDate date_born;

    @Size(max = 50)
    @Column(name = "gender", length = 50)
    private String gender;

    @NotNull
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}$")
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @NotNull
    @Column(name = "bussines_name", nullable = false)
    private String bussines_name;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9])+([_\\.\\-]([a-zA-Z0-9])+)*@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$")
    @Column(name = "account_email", nullable = false)
    private String account_email;

    @ManyToOne
    @NotNull
    private Request_state request_state;

    @ManyToOne
    private Tax_address_request tax_address_request;

    @ManyToOne
    private User user;

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

    public ZonedDateTime getDate_request() {
        return date_request;
    }

    public void setDate_request(ZonedDateTime date_request) {
        this.date_request = date_request;
    }

    public String getFirtsurname() {
        return firtsurname;
    }

    public void setFirtsurname(String firtsurname) {
        this.firtsurname = firtsurname;
    }

    public String getSecondsurname() {
        return secondsurname;
    }

    public void setSecondsurname(String secondsurname) {
        this.secondsurname = secondsurname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate_born() {
        return date_born;
    }

    public void setDate_born(LocalDate date_born) {
        this.date_born = date_born;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAccount_email() {
        return account_email;
    }

    public void setAccount_email(String account_email) {
        this.account_email = account_email;
    }

    public Request_state getRequest_state() {
        return request_state;
    }

    public void setRequest_state(Request_state request_state) {
        this.request_state = request_state;
    }

    public Tax_address_request getTax_address_request() {
        return tax_address_request;
    }

    public void setTax_address_request(Tax_address_request tax_address_request) {
        this.tax_address_request = tax_address_request;
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
        Request_taxpayer_account request_taxpayer_account = (Request_taxpayer_account) o;
        if(request_taxpayer_account.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, request_taxpayer_account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Request_taxpayer_account{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", date_request='" + date_request + "'" +
            ", firtsurname='" + firtsurname + "'" +
            ", secondsurname='" + secondsurname + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", date_born='" + date_born + "'" +
            ", gender='" + gender + "'" +
            ", rfc='" + rfc + "'" +
            ", bussines_name='" + bussines_name + "'" +
            ", account_email='" + account_email + "'" +
            '}';
    }
}
