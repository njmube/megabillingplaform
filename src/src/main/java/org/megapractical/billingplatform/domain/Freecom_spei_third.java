package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Freecom_spei_third.
 */
@Entity
@Table(name = "freecom_spei_third")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_spei_third implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date_operation", nullable = false)
    private LocalDate date_operation;

    @NotNull
    @Column(name = "hour", nullable = false)
    private ZonedDateTime hour;

    @NotNull
    @Column(name = "key_spei", nullable = false)
    private Integer key_spei;

    @NotNull
    @Column(name = "stamp", nullable = false)
    private String stamp;

    @NotNull
    @Size(max = 20)
    @Column(name = "number_certificate", length = 20, nullable = false)
    private String number_certificate;

    @NotNull
    @Size(max = 841)
    @Column(name = "cda", length = 841, nullable = false)
    private String cda;

    @ManyToOne
    private Freecom_spei freecom_spei;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_payer freecom_payer;

    @OneToOne
    @JoinColumn(unique = true)
    private Freecom_beneficiary freecom_beneficiary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_operation() {
        return date_operation;
    }

    public void setDate_operation(LocalDate date_operation) {
        this.date_operation = date_operation;
    }

    public ZonedDateTime getHour() {
        return hour;
    }

    public void setHour(ZonedDateTime hour) {
        this.hour = hour;
    }

    public Integer getKey_spei() {
        return key_spei;
    }

    public void setKey_spei(Integer key_spei) {
        this.key_spei = key_spei;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getNumber_certificate() {
        return number_certificate;
    }

    public void setNumber_certificate(String number_certificate) {
        this.number_certificate = number_certificate;
    }

    public String getCda() {
        return cda;
    }

    public void setCda(String cda) {
        this.cda = cda;
    }

    public Freecom_spei getFreecom_spei() {
        return freecom_spei;
    }

    public void setFreecom_spei(Freecom_spei freecom_spei) {
        this.freecom_spei = freecom_spei;
    }

    public Freecom_payer getFreecom_payer() {
        return freecom_payer;
    }

    public void setFreecom_payer(Freecom_payer freecom_payer) {
        this.freecom_payer = freecom_payer;
    }

    public Freecom_beneficiary getFreecom_beneficiary() {
        return freecom_beneficiary;
    }

    public void setFreecom_beneficiary(Freecom_beneficiary freecom_beneficiary) {
        this.freecom_beneficiary = freecom_beneficiary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freecom_spei_third freecom_spei_third = (Freecom_spei_third) o;
        if(freecom_spei_third.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_spei_third.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_spei_third{" +
            "id=" + id +
            ", date_operation='" + date_operation + "'" +
            ", hour='" + hour + "'" +
            ", key_spei='" + key_spei + "'" +
            ", stamp='" + stamp + "'" +
            ", number_certificate='" + number_certificate + "'" +
            ", cda='" + cda + "'" +
            '}';
    }
}
