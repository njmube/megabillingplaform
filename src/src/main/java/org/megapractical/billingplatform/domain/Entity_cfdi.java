package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Entity_cfdi.
 */
@javax.persistence.Entity
@Table(name = "entity_cfdi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entity_cfdi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Accounting accounting;

    @ManyToOne
    private Key_entity key_entity;

    @ManyToOne
    private Scope scope;

    @ManyToOne
    private Freecom_ine freecom_ine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accounting getAccounting() {
        return accounting;
    }

    public void setAccounting(Accounting accounting) {
        this.accounting = accounting;
    }

    public Key_entity getKey_entity() {
        return key_entity;
    }

    public void setKey_entity(Key_entity key_entity) {
        this.key_entity = key_entity;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Freecom_ine getFreecom_ine() {
        return freecom_ine;
    }

    public void setFreecom_ine(Freecom_ine freecom_ine) {
        this.freecom_ine = freecom_ine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity_cfdi entity_cfdi = (Entity_cfdi) o;
        if(entity_cfdi.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entity_cfdi.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entity_cfdi{" +
            "id=" + id +
            '}';
    }
}
