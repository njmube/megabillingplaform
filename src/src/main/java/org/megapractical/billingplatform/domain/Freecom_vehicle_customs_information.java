package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Freecom_vehicle_customs_information.
 */
@Entity
@Table(name = "freecom_vehicle_customs_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_vehicle_customs_information implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @NotNull
    @Column(name = "date_expedition", nullable = false)
    private LocalDate date_expedition;

    @Column(name = "customs")
    private String customs;

    @ManyToOne
    private Freecom_used_vehicle freecom_used_vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate_expedition() {
        return date_expedition;
    }

    public void setDate_expedition(LocalDate date_expedition) {
        this.date_expedition = date_expedition;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public Freecom_used_vehicle getFreecom_used_vehicle() {
        return freecom_used_vehicle;
    }

    public void setFreecom_used_vehicle(Freecom_used_vehicle freecom_used_vehicle) {
        this.freecom_used_vehicle = freecom_used_vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_vehicle_customs_information freecom_vehicle_customs_information = (Freecom_vehicle_customs_information) o;
        if(freecom_vehicle_customs_information.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_vehicle_customs_information.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_vehicle_customs_information{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", date_expedition='" + date_expedition + "'" +
            ", customs='" + customs + "'" +
            '}';
    }
}
