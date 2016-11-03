package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_airline.
 */
@Entity
@Table(name = "com_airline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_airline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "tua", precision=10, scale=2, nullable = false)
    private BigDecimal tua;

    @NotNull
    @Column(name = "total_charge", precision=10, scale=2, nullable = false)
    private BigDecimal total_charge;

    @OneToOne
    @JoinColumn(unique = true)
    private Cfdi cfdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BigDecimal getTua() {
        return tua;
    }

    public void setTua(BigDecimal tua) {
        this.tua = tua;
    }

    public BigDecimal getTotal_charge() {
        return total_charge;
    }

    public void setTotal_charge(BigDecimal total_charge) {
        this.total_charge = total_charge;
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
        Com_airline com_airline = (Com_airline) o;
        if(com_airline.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_airline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_airline{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", tua='" + tua + "'" +
            ", total_charge='" + total_charge + "'" +
            '}';
    }
}
