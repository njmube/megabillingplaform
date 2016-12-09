package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_data_enajenante.
 */
@Entity
@Table(name = "com_data_enajenante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_data_enajenante implements Serializable {

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
    private Com_public_notaries com_public_notaries;

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

    public Com_public_notaries getCom_public_notaries() {
        return com_public_notaries;
    }

    public void setCom_public_notaries(Com_public_notaries com_public_notaries) {
        this.com_public_notaries = com_public_notaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_data_enajenante com_data_enajenante = (Com_data_enajenante) o;
        if(com_data_enajenante.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_data_enajenante.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_data_enajenante{" +
            "id=" + id +
            ", coprosocconyugaie='" + coprosocconyugaie + "'" +
            '}';
    }
}
