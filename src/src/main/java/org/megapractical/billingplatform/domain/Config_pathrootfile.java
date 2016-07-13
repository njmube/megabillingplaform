package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Config_pathrootfile.
 */
@javax.persistence.Entity
@Table(name = "config_pathrootfile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Config_pathrootfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "pathroot_free_certificate", nullable = false)
    private String pathrootFreeCertificate;

    @NotNull
    @Column(name = "pathroot_free_logo", nullable = false)
    private String pathrootFreeLogo;

    @NotNull
    @Column(name = "pathroot_free_cfdi", nullable = false)
    private String pathrootFreeCfdi;

    @NotNull
    @Column(name = "pathroot_certificate", nullable = false)
    private String pathrootCertificate;

    @NotNull
    @Column(name = "pathroot_logo", nullable = false)
    private String pathrootLogo;

    @NotNull
    @Column(name = "pathroot_cfdi", nullable = false)
    private String pathrootCfdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathrootFreeCertificate() {
        return pathrootFreeCertificate;
    }

    public void setPathrootFreeCertificate(String pathrootFreeCertificate) {
        this.pathrootFreeCertificate = pathrootFreeCertificate;
    }

    public String getPathrootFreeLogo() {
        return pathrootFreeLogo;
    }

    public void setPathrootFreeLogo(String pathrootFreeLogo) {
        this.pathrootFreeLogo = pathrootFreeLogo;
    }

    public String getPathrootFreeCfdi() {
        return pathrootFreeCfdi;
    }

    public void setPathrootFreeCfdi(String pathrootFreeCfdi) {
        this.pathrootFreeCfdi = pathrootFreeCfdi;
    }

    public String getPathrootCertificate() {
        return pathrootCertificate;
    }

    public void setPathrootCertificate(String pathrootCertificate) {
        this.pathrootCertificate = pathrootCertificate;
    }

    public String getPathrootLogo() {
        return pathrootLogo;
    }

    public void setPathrootLogo(String pathrootLogo) {
        this.pathrootLogo = pathrootLogo;
    }

    public String getPathrootCfdi() {
        return pathrootCfdi;
    }

    public void setPathrootCfdi(String pathrootCfdi) {
        this.pathrootCfdi = pathrootCfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Config_pathrootfile config_pathrootfile = (Config_pathrootfile) o;
        if(config_pathrootfile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, config_pathrootfile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Config_pathrootfile{" +
            "id=" + id +
            ", pathrootFreeCertificate='" + pathrootFreeCertificate + "'" +
            ", pathrootFreeLogo='" + pathrootFreeLogo + "'" +
            ", pathrootFreeCfdi='" + pathrootFreeCfdi + "'" +
            ", pathrootCertificate='" + pathrootCertificate + "'" +
            ", pathrootLogo='" + pathrootLogo + "'" +
            ", pathrootCfdi='" + pathrootCfdi + "'" +
            '}';
    }
}
