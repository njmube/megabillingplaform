package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_kind_payment.
 */
@Entity
@Table(name = "com_kind_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_kind_payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "cvepic", nullable = false)
    private String cvepic;

    @NotNull
    @Column(name = "foliosoldon", nullable = false)
    private String foliosoldon;

    @NotNull
    @Column(name = "art_piece_name", nullable = false)
    private String art_piece_name;

    @NotNull
    @Column(name = "technical_art_piece", nullable = false)
    private String technical_art_piece;

    @NotNull
    @Column(name = "year_art_piece", nullable = false)
    private Integer year_art_piece;

    @NotNull
    @Column(name = "dimensional_art_piece", nullable = false)
    private String dimensional_art_piece;

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

    public String getCvepic() {
        return cvepic;
    }

    public void setCvepic(String cvepic) {
        this.cvepic = cvepic;
    }

    public String getFoliosoldon() {
        return foliosoldon;
    }

    public void setFoliosoldon(String foliosoldon) {
        this.foliosoldon = foliosoldon;
    }

    public String getArt_piece_name() {
        return art_piece_name;
    }

    public void setArt_piece_name(String art_piece_name) {
        this.art_piece_name = art_piece_name;
    }

    public String getTechnical_art_piece() {
        return technical_art_piece;
    }

    public void setTechnical_art_piece(String technical_art_piece) {
        this.technical_art_piece = technical_art_piece;
    }

    public Integer getYear_art_piece() {
        return year_art_piece;
    }

    public void setYear_art_piece(Integer year_art_piece) {
        this.year_art_piece = year_art_piece;
    }

    public String getDimensional_art_piece() {
        return dimensional_art_piece;
    }

    public void setDimensional_art_piece(String dimensional_art_piece) {
        this.dimensional_art_piece = dimensional_art_piece;
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
        Com_kind_payment com_kind_payment = (Com_kind_payment) o;
        if(com_kind_payment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_kind_payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_kind_payment{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", cvepic='" + cvepic + "'" +
            ", foliosoldon='" + foliosoldon + "'" +
            ", art_piece_name='" + art_piece_name + "'" +
            ", technical_art_piece='" + technical_art_piece + "'" +
            ", year_art_piece='" + year_art_piece + "'" +
            ", dimensional_art_piece='" + dimensional_art_piece + "'" +
            '}';
    }
}
