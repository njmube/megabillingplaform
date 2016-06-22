package org.megapractical.billingplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A C_location.
 */
@Entity
@Table(name = "c_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class C_location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private C_municipality c_municipality;

    @OneToOne
    @JoinColumn(unique = true)
    private C_zip_code c_zip_code;

    @OneToMany(mappedBy = "c_location")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<C_colony> c_colonies = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public C_municipality getC_municipality() {
        return c_municipality;
    }

    public void setC_municipality(C_municipality c_municipality) {
        this.c_municipality = c_municipality;
    }

    public C_zip_code getC_zip_code() {
        return c_zip_code;
    }

    public void setC_zip_code(C_zip_code c_zip_code) {
        this.c_zip_code = c_zip_code;
    }

    public Set<C_colony> getC_colonies() {
        return c_colonies;
    }

    public void setC_colonies(Set<C_colony> c_colonies) {
        this.c_colonies = c_colonies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        C_location c_location = (C_location) o;
        if(c_location.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c_location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "C_location{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
