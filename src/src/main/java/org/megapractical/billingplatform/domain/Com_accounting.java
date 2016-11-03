package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_accounting.
 */
@Entity
@Table(name = "com_accounting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_accounting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "keyaccounting", nullable = false)
    private Integer keyaccounting;

    @ManyToOne
    private Com_ine_entity com_ine_entity;

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

    public Com_ine_entity getCom_ine_entity() {
        return com_ine_entity;
    }

    public void setCom_ine_entity(Com_ine_entity com_ine_entity) {
        this.com_ine_entity = com_ine_entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_accounting com_accounting = (Com_accounting) o;
        if(com_accounting.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_accounting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_accounting{" +
            "id=" + id +
            ", keyaccounting='" + keyaccounting + "'" +
            '}';
    }
}
