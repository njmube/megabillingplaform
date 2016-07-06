package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A C_colony.
 */
@Entity
@Table(name = "c_colony")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQuery(name = "C_colony.findByM",
    query = "select c from C_colony c where c.c_municipality = ?1")
public class C_colony implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "code", length = 255, nullable = false)
    private String code;

    @Column(name = "value")
    private String value;

    @ManyToOne
    private C_municipality c_municipality;

    @ManyToOne
    private C_zip_code c_zip_code;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        C_colony c_colony = (C_colony) o;
        if(c_colony.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c_colony.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "C_colony{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
