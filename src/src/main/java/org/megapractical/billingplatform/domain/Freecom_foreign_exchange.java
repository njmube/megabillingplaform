package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_foreign_exchange.
 */
@Entity
@Table(name = "freecom_foreign_exchange")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_foreign_exchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private C_type_operation c_type_operation;

    @ManyToOne
    private Free_cfdi free_cfdi;

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

    public Free_cfdi getFree_cfdi() {
        return free_cfdi;
    }

    public void setFree_cfdi(Free_cfdi free_cfdi) {
        this.free_cfdi = free_cfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_foreign_exchange freecom_foreign_exchange = (Freecom_foreign_exchange) o;
        if(freecom_foreign_exchange.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_foreign_exchange.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_foreign_exchange{" +
            "id=" + id +
            '}';
    }
}
