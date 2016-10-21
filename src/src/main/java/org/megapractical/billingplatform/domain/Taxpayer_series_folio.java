package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Taxpayer_series_folio.
 */
@Entity
@Table(name = "taxpayer_series_folio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_series_folio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 25)
    @Column(name = "serie", length = 25, nullable = false)
    private String serie;

    @NotNull
    @Column(name = "folio_start", nullable = false)
    private Integer folio_start;

    @NotNull
    @Column(name = "folio_end", nullable = false)
    private Integer folio_end;

    @NotNull
    @Column(name = "folio_current", nullable = false)
    private Integer folio_current;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private LocalDate date_creation;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getFolio_start() {
        return folio_start;
    }

    public void setFolio_start(Integer folio_start) {
        this.folio_start = folio_start;
    }

    public Integer getFolio_end() {
        return folio_end;
    }

    public void setFolio_end(Integer folio_end) {
        this.folio_end = folio_end;
    }

    public Integer getFolio_current() {
        return folio_current;
    }

    public void setFolio_current(Integer folio_current) {
        this.folio_current = folio_current;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

    public Taxpayer_account getTaxpayer_account() {
        return taxpayer_account;
    }

    public void setTaxpayer_account(Taxpayer_account taxpayer_account) {
        this.taxpayer_account = taxpayer_account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taxpayer_series_folio taxpayer_series_folio = (Taxpayer_series_folio) o;
        if(taxpayer_series_folio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_series_folio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_series_folio{" +
            "id=" + id +
            ", serie='" + serie + "'" +
            ", folio_start='" + folio_start + "'" +
            ", folio_end='" + folio_end + "'" +
            ", folio_current='" + folio_current + "'" +
            ", date_creation='" + date_creation + "'" +
            '}';
    }
}
