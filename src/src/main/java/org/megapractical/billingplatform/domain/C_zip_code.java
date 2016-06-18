package org.megapractical.billingplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A C_zip_code.
 */
@Entity
@Table(name = "c_zip_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class C_zip_code implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "postcode")
    private String postcode;

    @OneToOne(mappedBy = "c_zip_code")
    @JsonIgnore
    private C_location c_location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public C_location getC_location() {
        return c_location;
    }

    public void setC_location(C_location c_location) {
        this.c_location = c_location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        C_zip_code c_zip_code = (C_zip_code) o;
        if(c_zip_code.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c_zip_code.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "C_zip_code{" +
            "id=" + id +
            ", postcode='" + postcode + "'" +
            '}';
    }
}
