package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Mail_configuration.
 */
@javax.persistence.Entity
@Table(name = "mail_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mail_configuration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private Integer port;

    @Column(name = "user_mail")
    private String user_mail;

    @Column(name = "password_mail")
    private String password_mail;

    @Column(name = "protocol")
    private String protocol;

    @Column(name = "tls")
    private Boolean tls;

    @Column(name = "smtp_auth")
    private Boolean smtp_auth;

    @Column(name = "starttls_enable")
    private Boolean starttls_enable;

    @Column(name = "ssl_trust")
    private String ssl_trust;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getPassword_mail() {
        return password_mail;
    }

    public void setPassword_mail(String password_mail) {
        this.password_mail = password_mail;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Boolean isTls() {
        return tls;
    }

    public void setTls(Boolean tls) {
        this.tls = tls;
    }

    public Boolean isSmtp_auth() {
        return smtp_auth;
    }

    public void setSmtp_auth(Boolean smtp_auth) {
        this.smtp_auth = smtp_auth;
    }

    public Boolean isStarttls_enable() {
        return starttls_enable;
    }

    public void setStarttls_enable(Boolean starttls_enable) {
        this.starttls_enable = starttls_enable;
    }

    public String getSsl_trust() {
        return ssl_trust;
    }

    public void setSsl_trust(String ssl_trust) {
        this.ssl_trust = ssl_trust;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mail_configuration mail_configuration = (Mail_configuration) o;
        if(mail_configuration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mail_configuration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mail_configuration{" +
            "id=" + id +
            ", host='" + host + "'" +
            ", port='" + port + "'" +
            ", user_mail='" + user_mail + "'" +
            ", password_mail='" + password_mail + "'" +
            ", protocol='" + protocol + "'" +
            ", tls='" + tls + "'" +
            ", smtp_auth='" + smtp_auth + "'" +
            ", starttls_enable='" + starttls_enable + "'" +
            ", ssl_trust='" + ssl_trust + "'" +
            '}';
    }
}
