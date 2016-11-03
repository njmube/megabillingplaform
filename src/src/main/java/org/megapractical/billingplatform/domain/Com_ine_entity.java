package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_ine_entity.
 */
@Entity
@Table(name = "com_ine_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_ine_entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Key_entity key_entity;

    @ManyToOne
    private Com_ine com_ine;

    @ManyToOne
    private C_scope_type c_scope_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key_entity getKey_entity() {
        return key_entity;
    }

    public void setKey_entity(Key_entity key_entity) {
        this.key_entity = key_entity;
    }

    public Com_ine getCom_ine() {
        return com_ine;
    }

    public void setCom_ine(Com_ine com_ine) {
        this.com_ine = com_ine;
    }

    public C_scope_type getC_scope_type() {
        return c_scope_type;
    }

    public void setC_scope_type(C_scope_type c_scope_type) {
        this.c_scope_type = c_scope_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_ine_entity com_ine_entity = (Com_ine_entity) o;
        if(com_ine_entity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_ine_entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_ine_entity{" +
            "id=" + id +
            '}';
    }
}
