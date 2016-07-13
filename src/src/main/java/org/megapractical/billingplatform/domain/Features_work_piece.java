package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Features_work_piece.
 */
@javax.persistence.Entity
@Table(name = "features_work_piece")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Features_work_piece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Features_work_piece features_work_piece = (Features_work_piece) o;
        if(features_work_piece.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, features_work_piece.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Features_work_piece{" +
            "id=" + id +
            ", code='" + code + "'" +
            '}';
    }
}
