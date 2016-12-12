package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_data_enajenante.
 */
@Entity
@Table(name = "freecom_data_enajenante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_data_enajenante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "coprosocconyugaie", length = 10, nullable = false)
    private String coprosocconyugaie;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_public_notaries freecom_public_notaries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoprosocconyugaie() {
        return coprosocconyugaie;
    }

    public void setCoprosocconyugaie(String coprosocconyugaie) {
        this.coprosocconyugaie = coprosocconyugaie;
    }

    public Freecom_public_notaries getFreecom_public_notaries() {
        return freecom_public_notaries;
    }

    public void setFreecom_public_notaries(Freecom_public_notaries freecom_public_notaries) {
        this.freecom_public_notaries = freecom_public_notaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_data_enajenante freecom_data_enajenante = (Freecom_data_enajenante) o;
        if(freecom_data_enajenante.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_data_enajenante.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_data_enajenante{" +
            "id=" + id +
            ", coprosocconyugaie='" + coprosocconyugaie + "'" +
            '}';
    }
}
