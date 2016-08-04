package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Freecom_info_customs_destruction.
 */
@Entity
@Table(name = "freecom_info_customs_destruction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_info_customs_destruction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "numpedimp", length = 40, nullable = false)
    private String numpedimp;

    @NotNull
    @Column(name = "date_expedition", nullable = false)
    private LocalDate date_expedition;

    @NotNull
    @Column(name = "customs", nullable = false)
    private String customs;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_destruction_certificate freecom_destruction_certificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumpedimp() {
        return numpedimp;
    }

    public void setNumpedimp(String numpedimp) {
        this.numpedimp = numpedimp;
    }

    public LocalDate getDate_expedition() {
        return date_expedition;
    }

    public void setDate_expedition(LocalDate date_expedition) {
        this.date_expedition = date_expedition;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public Freecom_destruction_certificate getFreecom_destruction_certificate() {
        return freecom_destruction_certificate;
    }

    public void setFreecom_destruction_certificate(Freecom_destruction_certificate freecom_destruction_certificate) {
        this.freecom_destruction_certificate = freecom_destruction_certificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_info_customs_destruction freecom_info_customs_destruction = (Freecom_info_customs_destruction) o;
        if(freecom_info_customs_destruction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_info_customs_destruction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_info_customs_destruction{" +
            "id=" + id +
            ", numpedimp='" + numpedimp + "'" +
            ", date_expedition='" + date_expedition + "'" +
            ", customs='" + customs + "'" +
            '}';
    }
}
