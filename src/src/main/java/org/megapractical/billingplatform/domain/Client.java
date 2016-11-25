package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 12, max = 13)
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}$")
    @Column(name = "rfc", length = 13, nullable = false)
    private String rfc;

    @Column(name = "bussinesname")
    private String bussinesname;

    @NotNull
    @Size(min = 6, max = 254)
    @Pattern(regexp = "^([a-zA-Z0-9])+([_\\.\\-]([a-zA-Z0-9])+)*@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$")
    @Column(name = "email", length = 254, nullable = false)
    private String email;

    @Column(name = "email_2")
    private String email2;

    @Pattern(regexp = "^[0-9]{1,15}$")
    @Column(name = "phone")
    private String phone;

    @OneToOne
    @JoinColumn(unique = true)
    private Client_address client_address;

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

    public String getBussinesname() {
        return bussinesname;
    }

    public void setBussinesname(String bussinesname) {
        this.bussinesname = bussinesname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Client_address getClient_address() {
        return client_address;
    }

    public void setClient_address(Client_address client_address) {
        this.client_address = client_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if(client.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", rfc='" + rfc + "'" +
            ", bussinesname='" + bussinesname + "'" +
            ", email='" + email + "'" +
            ", email2='" + email2 + "'" +
            ", phone='" + phone + "'" +
            '}';
    }
}
