package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Accounting.
 */
@Entity
@Table(name = "accounting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Accounting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "keyaccounting", nullable = false)
    private Integer keyaccounting;

    @ManyToOne
    private Freecom_ine_entity freecom_ine_entity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKeyaccounting() {
        return keyaccounting;
    }

    public void setKeyaccounting(Integer keyaccounting) {
        this.keyaccounting = keyaccounting;
    }

    public Freecom_ine_entity getFreecom_ine_entity() {
        return freecom_ine_entity;
    }

    public void setFreecom_ine_entity(Freecom_ine_entity freecom_ine_entity) {
        this.freecom_ine_entity = freecom_ine_entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Accounting accounting = (Accounting) o;
        if(accounting.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, accounting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Accounting{" +
            "id=" + id +
            ", keyaccounting='" + keyaccounting + "'" +
            '}';
    }
}
