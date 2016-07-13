package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A C_municipality.
 */
@javax.persistence.Entity
@Table(name = "c_municipality")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQuery(name = "C_municipality.findByS",
    query = "select m from C_municipality m where m.c_state = ?1")
public class C_municipality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @ManyToOne
    private C_state c_state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public C_state getC_state() {
        return c_state;
    }

    public void setC_state(C_state c_state) {
        this.c_state = c_state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        C_municipality c_municipality = (C_municipality) o;
        if(c_municipality.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c_municipality.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "C_municipality{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", code='" + code + "'" +
            '}';
    }
}
