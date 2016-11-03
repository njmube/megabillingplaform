package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_pfic.
 */
@Entity
@Table(name = "com_pfic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_pfic implements Serializable {

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
        Com_pfic com_pfic = (Com_pfic) o;
        if(com_pfic.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_pfic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_pfic{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", key_vehicle='" + key_vehicle + "'" +
            ", license_plate='" + license_plate + "'" +
            ", rfcpf='" + rfcpf + "'" +
            '}';
    }
}
