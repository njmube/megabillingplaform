package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Com_apaw.
 */
@Entity
@Table(name = "com_apaw")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_apaw implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "others_well_type")
    private String others_well_type;

    @Column(name = "others_acquired_title")
    private String others_acquired_title;

    @Column(name = "subtotal", precision=10, scale=2)
    private BigDecimal subtotal;

    @Column(name = "iva", precision=10, scale=2)
    private BigDecimal iva;

    @NotNull
    @Column(name = "date_acquisition", nullable = false)
    private ZonedDateTime date_acquisition;

    @OneToOne
    @JoinColumn(unique = true)
    private Cfdi cfdi;

    @ManyToOne
    private C_well_type c_well_type;

    @ManyToOne
    private C_acquired_title c_acquired_title;

    @ManyToOne
    private C_features_work_piece c_features_work_piece;

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

    public String getOthers_well_type() {
        return others_well_type;
    }

    public void setOthers_well_type(String others_well_type) {
        this.others_well_type = others_well_type;
    }

    public String getOthers_acquired_title() {
        return others_acquired_title;
    }

    public void setOthers_acquired_title(String others_acquired_title) {
        this.others_acquired_title = others_acquired_title;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public ZonedDateTime getDate_acquisition() {
        return date_acquisition;
    }

    public void setDate_acquisition(ZonedDateTime date_acquisition) {
        this.date_acquisition = date_acquisition;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    public C_well_type getC_well_type() {
        return c_well_type;
    }

    public void setC_well_type(C_well_type c_well_type) {
        this.c_well_type = c_well_type;
    }

    public C_acquired_title getC_acquired_title() {
        return c_acquired_title;
    }

    public void setC_acquired_title(C_acquired_title c_acquired_title) {
        this.c_acquired_title = c_acquired_title;
    }

    public C_features_work_piece getC_features_work_piece() {
        return c_features_work_piece;
    }

    public void setC_features_work_piece(C_features_work_piece c_features_work_piece) {
        this.c_features_work_piece = c_features_work_piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_apaw com_apaw = (Com_apaw) o;
        if(com_apaw.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_apaw.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_apaw{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", others_well_type='" + others_well_type + "'" +
            ", others_acquired_title='" + others_acquired_title + "'" +
            ", subtotal='" + subtotal + "'" +
            ", iva='" + iva + "'" +
            ", date_acquisition='" + date_acquisition + "'" +
            '}';
    }
}
