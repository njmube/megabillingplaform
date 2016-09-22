package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Freecom_foreign_trade.
 */
@Entity
@Table(name = "freecom_foreign_trade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_foreign_trade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "emitter_curp")
    private String emitter_curp;

    @Size(max = 10)
    @Column(name = "receiver_curp", length = 10)
    private String receiver_curp;

    @NotNull
    @Size(max = 40)
    @Column(name = "receiver_numregidtrib", length = 40, nullable = false)
    private String receiver_numregidtrib;

    @Column(name = "origin_certificate")
    private Integer origin_certificate;

    @Size(max = 40)
    @Column(name = "number_origin_certificate", length = 40)
    private String number_origin_certificate;

    @Size(max = 50)
    @Column(name = "number_reliable_exporter", length = 50)
    private String number_reliable_exporter;

    @Column(name = "subdivision")
    private Integer subdivision;

    @Size(max = 300)
    @Column(name = "observations", length = 300)
    private String observations;

    @Column(name = "typechangeusd", precision=10, scale=2)
    private BigDecimal typechangeusd;

    @Column(name = "totalusd", precision=10, scale=2)
    private BigDecimal totalusd;

    @ManyToOne
    private Free_cfdi free_cfdi;

    @ManyToOne
    private Freecom_incoterm freecom_incoterm;

    @ManyToOne
    private C_type_operation_ce c_type_operation_ce;

    @ManyToOne
    private C_key_pediment c_key_pediment;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_addressee freecom_addressee;

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

    public String getEmitter_curp() {
        return emitter_curp;
    }

    public void setEmitter_curp(String emitter_curp) {
        this.emitter_curp = emitter_curp;
    }

    public String getReceiver_curp() {
        return receiver_curp;
    }

    public void setReceiver_curp(String receiver_curp) {
        this.receiver_curp = receiver_curp;
    }

    public String getReceiver_numregidtrib() {
        return receiver_numregidtrib;
    }

    public void setReceiver_numregidtrib(String receiver_numregidtrib) {
        this.receiver_numregidtrib = receiver_numregidtrib;
    }

    public Integer getOrigin_certificate() {
        return origin_certificate;
    }

    public void setOrigin_certificate(Integer origin_certificate) {
        this.origin_certificate = origin_certificate;
    }

    public String getNumber_origin_certificate() {
        return number_origin_certificate;
    }

    public void setNumber_origin_certificate(String number_origin_certificate) {
        this.number_origin_certificate = number_origin_certificate;
    }

    public String getNumber_reliable_exporter() {
        return number_reliable_exporter;
    }

    public void setNumber_reliable_exporter(String number_reliable_exporter) {
        this.number_reliable_exporter = number_reliable_exporter;
    }

    public Integer getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Integer subdivision) {
        this.subdivision = subdivision;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public BigDecimal getTypechangeusd() {
        return typechangeusd;
    }

    public void setTypechangeusd(BigDecimal typechangeusd) {
        this.typechangeusd = typechangeusd;
    }

    public BigDecimal getTotalusd() {
        return totalusd;
    }

    public void setTotalusd(BigDecimal totalusd) {
        this.totalusd = totalusd;
    }

    public Free_cfdi getFree_cfdi() {
        return free_cfdi;
    }

    public void setFree_cfdi(Free_cfdi free_cfdi) {
        this.free_cfdi = free_cfdi;
    }

    public Freecom_incoterm getFreecom_incoterm() {
        return freecom_incoterm;
    }

    public void setFreecom_incoterm(Freecom_incoterm freecom_incoterm) {
        this.freecom_incoterm = freecom_incoterm;
    }

    public C_type_operation_ce getC_type_operation_ce() {
        return c_type_operation_ce;
    }

    public void setC_type_operation_ce(C_type_operation_ce c_type_operation_ce) {
        this.c_type_operation_ce = c_type_operation_ce;
    }

    public C_key_pediment getC_key_pediment() {
        return c_key_pediment;
    }

    public void setC_key_pediment(C_key_pediment c_key_pediment) {
        this.c_key_pediment = c_key_pediment;
    }

    public Freecom_addressee getFreecom_addressee() {
        return freecom_addressee;
    }

    public void setFreecom_addressee(Freecom_addressee freecom_addressee) {
        this.freecom_addressee = freecom_addressee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_foreign_trade freecom_foreign_trade = (Freecom_foreign_trade) o;
        if(freecom_foreign_trade.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_foreign_trade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_foreign_trade{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", emitter_curp='" + emitter_curp + "'" +
            ", receiver_curp='" + receiver_curp + "'" +
            ", receiver_numregidtrib='" + receiver_numregidtrib + "'" +
            ", origin_certificate='" + origin_certificate + "'" +
            ", number_origin_certificate='" + number_origin_certificate + "'" +
            ", number_reliable_exporter='" + number_reliable_exporter + "'" +
            ", subdivision='" + subdivision + "'" +
            ", observations='" + observations + "'" +
            ", typechangeusd='" + typechangeusd + "'" +
            ", totalusd='" + totalusd + "'" +
            '}';
    }
}
