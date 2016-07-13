package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_taxlegends.
 */
@javax.persistence.Entity
@Table(name = "freecom_taxlegends")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_taxlegends implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

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
        Freecom_taxlegends freecom_taxlegends = (Freecom_taxlegends) o;
        if(freecom_taxlegends.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_taxlegends.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_taxlegends{" +
            "id=" + id +
            ", version='" + version + "'" +
            '}';
    }
}
