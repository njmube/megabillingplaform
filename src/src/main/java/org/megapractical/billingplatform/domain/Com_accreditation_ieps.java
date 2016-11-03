package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_accreditation_ieps.
 */
@Entity
@Table(name = "com_accreditation_ieps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_accreditation_ieps implements Serializable {

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
    private Cfdi cfdi;

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

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_accreditation_ieps com_accreditation_ieps = (Com_accreditation_ieps) o;
        if(com_accreditation_ieps.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_accreditation_ieps.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_accreditation_ieps{" +
            "id=" + id +
            ", version='" + version + "'" +
            '}';
    }
}
