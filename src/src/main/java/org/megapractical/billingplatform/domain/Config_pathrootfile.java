package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Config_pathrootfile.
 */
@Entity
@Table(name = "config_pathrootfile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Config_pathrootfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "pathrootdev", nullable = false)
    private String pathrootdev;

    @NotNull
    @Column(name = "pathrootprod", nullable = false)
    private String pathrootprod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathrootdev() {
        return pathrootdev;
    }

    public void setPathrootdev(String pathrootdev) {
        this.pathrootdev = pathrootdev;
    }

    public String getPathrootprod() {
        return pathrootprod;
    }

    public void setPathrootprod(String pathrootprod) {
        this.pathrootprod = pathrootprod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Config_pathrootfile config_pathrootfile = (Config_pathrootfile) o;
        if(config_pathrootfile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, config_pathrootfile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Config_pathrootfile{" +
            "id=" + id +
            ", pathrootdev='" + pathrootdev + "'" +
            ", pathrootprod='" + pathrootprod + "'" +
            '}';
    }
}
