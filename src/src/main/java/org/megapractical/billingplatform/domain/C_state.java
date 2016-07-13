package org.megapractical.billingplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A C_state.
 */
@javax.persistence.Entity
@Table(name = "c_state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQuery(name = "C_state.findByC",
    query = "select s from C_state s where s.c_country = ?1")
public class C_state implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "abrev")
    private String abrev;

    @ManyToOne
    private C_country c_country;

    @OneToMany(mappedBy = "c_state")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<C_municipality> c_municipalitys = new HashSet<>();

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

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public C_country getC_country() {
        return c_country;
    }

    public void setC_country(C_country c_country) {
        this.c_country = c_country;
    }

    public Set<C_municipality> getC_municipalitys() {
        return c_municipalitys;
    }

    public void setC_municipalitys(Set<C_municipality> c_municipalitys) {
        this.c_municipalitys = c_municipalitys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        C_state c_state = (C_state) o;
        if(c_state.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c_state.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "C_state{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", abrev='" + abrev + "'" +
            '}';
    }
}
