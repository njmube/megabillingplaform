package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Taxpayer_client.
 */
@Entity
@Table(name = "taxpayer_client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 12, max = 13)
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}$")
    @Column(name = "rfc", length = 13, nullable = false)
    private String rfc;

    @NotNull
    @Column(name = "bussinesname", nullable = false)
    private String bussinesname;

    @NotNull
    @Size(min = 6, max = 254)
    @Pattern(regexp = "^([a-zA-Z0-9])+([_\\.\\-]([a-zA-Z0-9])+)*@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$")
    @Column(name = "email", length = 254, nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private Client_address client_address;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

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

    public Client_address getClient_address() {
        return client_address;
    }

    public void setClient_address(Client_address client_address) {
        this.client_address = client_address;
    }

    public Taxpayer_account getTaxpayer_account() {
        return taxpayer_account;
    }

    public void setTaxpayer_account(Taxpayer_account taxpayer_account) {
        this.taxpayer_account = taxpayer_account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taxpayer_client taxpayer_client = (Taxpayer_client) o;
        if(taxpayer_client.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_client{" +
            "id=" + id +
            ", rfc='" + rfc + "'" +
            ", bussinesname='" + bussinesname + "'" +
            ", email='" + email + "'" +
            '}';
    }
}
