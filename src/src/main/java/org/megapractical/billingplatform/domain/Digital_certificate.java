package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Digital_certificate.
 */
@javax.persistence.Entity
@Table(name = "digital_certificate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Digital_certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @NotNull
    @Column(name = "adrees")
    private byte[] adrees;

    @NotNull
    @Column(name = "adrees_content_type")
    private String adreesContentType;

    @NotNull
    @Lob
    @Column(name = "private_key")
    private byte[] private_key;

    @NotNull
    @Column(name = "private_key_content_type")
    private String private_keyContentType;

    @NotNull
    @Column(name = "possword")
    private String possword;

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

    public String getPossword() {
        return possword;
    }

    public void setPossword(String possword) {
        this.possword = possword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Digital_certificate digital_certificate = (Digital_certificate) o;
        if(digital_certificate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, digital_certificate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Digital_certificate{" +
            "id=" + id +
            ", adrees='" + adrees + "'" +
            ", adreesContentType='" + adreesContentType + "'" +
            ", private_key='" + private_key + "'" +
            ", private_keyContentType='" + private_keyContentType + "'" +
            ", possword='" + possword + "'" +
            '}';
    }
}
