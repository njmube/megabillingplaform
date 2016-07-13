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
 * A Freecom_apaw.
 */
@javax.persistence.Entity
@Table(name = "freecom_apaw")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_apaw implements Serializable {

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

    @ManyToOne
    private Well_type well_type;

    @ManyToOne
    private Acquired_title acquired_title;

    @ManyToOne
    private Features_work_piece features_work_piece;

    @OneToOne
    @JoinColumn(unique = true)
    private Free_cfdi free_cfdi;

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

    public Well_type getWell_type() {
        return well_type;
    }

    public void setWell_type(Well_type well_type) {
        this.well_type = well_type;
    }

    public Acquired_title getAcquired_title() {
        return acquired_title;
    }

    public void setAcquired_title(Acquired_title acquired_title) {
        this.acquired_title = acquired_title;
    }

    public Features_work_piece getFeatures_work_piece() {
        return features_work_piece;
    }

    public void setFeatures_work_piece(Features_work_piece features_work_piece) {
        this.features_work_piece = features_work_piece;
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
        Freecom_apaw freecom_apaw = (Freecom_apaw) o;
        if(freecom_apaw.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_apaw.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_apaw{" +
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
