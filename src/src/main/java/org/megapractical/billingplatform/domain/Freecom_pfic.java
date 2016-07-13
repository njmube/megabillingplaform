package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Freecom_pfic.
 */
@javax.persistence.Entity
@Table(name = "freecom_pfic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freecom_pfic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "key_vehicle", nullable = false)
    private String key_vehicle;

    @NotNull
    @Column(name = "license_plate", nullable = false)
    private String license_plate;

    @Column(name = "rfcpf")
    private String rfcpf;

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

    public String getKey_vehicle() {
        return key_vehicle;
    }

    public void setKey_vehicle(String key_vehicle) {
        this.key_vehicle = key_vehicle;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getRfcpf() {
        return rfcpf;
    }

    public void setRfcpf(String rfcpf) {
        this.rfcpf = rfcpf;
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
        Freecom_pfic freecom_pfic = (Freecom_pfic) o;
        if(freecom_pfic.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, freecom_pfic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Freecom_pfic{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", key_vehicle='" + key_vehicle + "'" +
            ", license_plate='" + license_plate + "'" +
            ", rfcpf='" + rfcpf + "'" +
            '}';
    }
}
