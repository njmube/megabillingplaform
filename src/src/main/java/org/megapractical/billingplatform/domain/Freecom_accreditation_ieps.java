package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_accreditation_ieps.
 */
@javax.persistence.Entity
@Table(name = "freecom_accreditation_ieps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_accreditation_ieps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @ManyToOne
    private C_tar c_tar;

    @OneToOne
    @JoinColumn(unique = true)
    private Free_cfdi free_cfdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public C_tar getC_tar() {
        return c_tar;
    }

    public void setC_tar(C_tar c_tar) {
        this.c_tar = c_tar;
    }

    public Free_cfdi getFree_cfdi() {
        return free_cfdi;
    }

    public void setFree_cfdi(Free_cfdi free_cfdi) {
        this.free_cfdi = free_cfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_accreditation_ieps freecom_accreditation_ieps = (Freecom_accreditation_ieps) o;
        if(freecom_accreditation_ieps.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_accreditation_ieps.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_accreditation_ieps{" +
            "id=" + id +
            ", version='" + version + "'" +
            '}';
    }
}
