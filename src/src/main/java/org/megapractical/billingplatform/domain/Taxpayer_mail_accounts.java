package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Taxpayer_mail_accounts.
 */
@Entity
@Table(name = "taxpayer_mail_accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_mail_accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "incoming_mail_type", nullable = false)
    private String incoming_mail_type;

    @NotNull
    @Column(name = "incoming_server_name", nullable = false)
    private String incoming_server_name;

    @NotNull
    @Column(name = "incoming_port", nullable = false)
    private Integer incoming_port;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncoming_mail_type() {
        return incoming_mail_type;
    }

    public void setIncoming_mail_type(String incoming_mail_type) {
        this.incoming_mail_type = incoming_mail_type;
    }

    public String getIncoming_server_name() {
        return incoming_server_name;
    }

    public void setIncoming_server_name(String incoming_server_name) {
        this.incoming_server_name = incoming_server_name;
    }

    public Integer getIncoming_port() {
        return incoming_port;
    }

    public void setIncoming_port(Integer incoming_port) {
        this.incoming_port = incoming_port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        Taxpayer_mail_accounts taxpayer_mail_accounts = (Taxpayer_mail_accounts) o;
        if(taxpayer_mail_accounts.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_mail_accounts.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_mail_accounts{" +
            "id=" + id +
            ", incoming_mail_type='" + incoming_mail_type + "'" +
            ", incoming_server_name='" + incoming_server_name + "'" +
            ", incoming_port='" + incoming_port + "'" +
            ", username='" + username + "'" +
            ", password='" + password + "'" +
            '}';
    }
}
