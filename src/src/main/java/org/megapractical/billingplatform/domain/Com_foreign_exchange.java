package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_foreign_exchange.
 */
@Entity
@Table(name = "com_foreign_exchange")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_foreign_exchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private C_type_operation c_type_operation;

    @ManyToOne
    private Cfdi cfdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public C_type_operation getC_type_operation() {
        return c_type_operation;
    }

    public void setC_type_operation(C_type_operation c_type_operation) {
        this.c_type_operation = c_type_operation;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_foreign_exchange com_foreign_exchange = (Com_foreign_exchange) o;
        if(com_foreign_exchange.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_foreign_exchange.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_foreign_exchange{" +
            "id=" + id +
            '}';
    }
}
