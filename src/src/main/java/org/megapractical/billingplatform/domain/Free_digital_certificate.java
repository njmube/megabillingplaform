package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Free_digital_certificate.
 */
@Entity
@Table(name = "free_digital_certificate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_digital_certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "path_certificate", nullable = false)
    private String path_certificate;

    @NotNull
    @Column(name = "path_private_key", nullable = false)
    private String path_private_key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath_certificate() {
        return path_certificate;
    }

    public void setPath_certificate(String path_certificate) {
        this.path_certificate = path_certificate;
    }

    public String getPath_private_key() {
        return path_private_key;
    }

    public void setPath_private_key(String path_private_key) {
        this.path_private_key = path_private_key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Free_digital_certificate free_digital_certificate = (Free_digital_certificate) o;
        if(free_digital_certificate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, free_digital_certificate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Free_digital_certificate{" +
            "id=" + id +
            ", path_certificate='" + path_certificate + "'" +
            ", path_private_key='" + path_private_key + "'" +
            '}';
    }
}
