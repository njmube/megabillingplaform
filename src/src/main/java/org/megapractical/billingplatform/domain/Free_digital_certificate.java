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
    @Lob
    @Column(name = "adrees", nullable = false)
    private byte[] adrees;

    @Column(name = "adrees_content_type", nullable = false)    
    private String adreesContentType;

    @NotNull
    @Lob
    @Column(name = "private_key", nullable = false)
    private byte[] private_key;

    @Column(name = "private_key_content_type", nullable = false)    
    private String private_keyContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAdrees() {
        return adrees;
    }

    public void setAdrees(byte[] adrees) {
        this.adrees = adrees;
    }

    public String getAdreesContentType() {
        return adreesContentType;
    }

    public void setAdreesContentType(String adreesContentType) {
        this.adreesContentType = adreesContentType;
    }

    public byte[] getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(byte[] private_key) {
        this.private_key = private_key;
    }

    public String getPrivate_keyContentType() {
        return private_keyContentType;
    }

    public void setPrivate_keyContentType(String private_keyContentType) {
        this.private_keyContentType = private_keyContentType;
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
            ", adrees='" + adrees + "'" +
            ", adreesContentType='" + adreesContentType + "'" +
            ", private_key='" + private_key + "'" +
            ", private_keyContentType='" + private_keyContentType + "'" +
            '}';
    }
}
