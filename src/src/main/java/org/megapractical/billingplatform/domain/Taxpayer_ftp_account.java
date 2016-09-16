package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Taxpayer_ftp_account.
 */
@Entity
@Table(name = "taxpayer_ftp_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_ftp_account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "server_type", nullable = false)
    private String server_type;

    @NotNull
    @Column(name = "server_name_ip", nullable = false)
    private String server_name_ip;

    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;

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

    public String getServer_type() {
        return server_type;
    }

    public void setServer_type(String server_type) {
        this.server_type = server_type;
    }

    public String getServer_name_ip() {
        return server_name_ip;
    }

    public void setServer_name_ip(String server_name_ip) {
        this.server_name_ip = server_name_ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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
        Taxpayer_ftp_account taxpayer_ftp_account = (Taxpayer_ftp_account) o;
        if(taxpayer_ftp_account.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_ftp_account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_ftp_account{" +
            "id=" + id +
            ", server_type='" + server_type + "'" +
            ", server_name_ip='" + server_name_ip + "'" +
            ", port='" + port + "'" +
            ", username='" + username + "'" +
            ", password='" + password + "'" +
            '}';
    }
}
