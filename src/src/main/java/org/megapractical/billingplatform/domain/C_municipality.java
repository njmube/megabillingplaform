package org.megapractical.billingplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A C_municipality.
 */
@Entity
@Table(name = "c_municipality")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @OneToMany(mappedBy = "c_municipality")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<C_location> c_locations = new HashSet<>();

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

    public Set<C_location> getC_locations() {
        return c_locations;
    }

    public void setC_locations(Set<C_location> c_locations) {
        this.c_locations = c_locations;
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
