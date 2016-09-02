package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Taxpayer_certificate.
 */
@Entity
@Table(name = "taxpayer_certificate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "path_certificate", nullable = false)
    private String path_certificate;

    @NotNull
    @Lob
    @Column(name = "filecertificate", nullable = false)
    private byte[] filecertificate;

    @Column(name = "filecertificate_content_type", nullable = false)
    private String filecertificateContentType;

    @NotNull
    @Column(name = "path_key", nullable = false)
    private String path_key;

    @NotNull
    @Lob
    @Column(name = "filekey", nullable = false)
    private byte[] filekey;

    @Column(name = "filekey_content_type", nullable = false)
    private String filekeyContentType;

    @NotNull
    @Size(max = 20)
    @Column(name = "number_certificate", length = 20, nullable = false)
    private String number_certificate;

    @NotNull
    @Column(name = "date_certificate", nullable = false)
    private LocalDate date_certificate;

    @NotNull
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}$")
    @Column(name = "rfc_certificate", nullable = false)
    private String rfc_certificate;

    @NotNull
    @Column(name = "bussines_name_cert", nullable = false)
    private String bussines_name_cert;

    @NotNull
    @Column(name = "date_created_cert", nullable = false)
    private LocalDate date_created_cert;

    @Column(name = "date_expiration_cert")
    private LocalDate date_expiration_cert;

    @NotNull
    @Column(name = "info_certificate", nullable = false)
    private String info_certificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath_certificate() {
        return path_certificate;
    }

    public void setPath_certificate(String path_certificate) {
        this.path_certificate = path_certificate;
    }

    public byte[] getFilecertificate() {
        return filecertificate;
    }

    public void setFilecertificate(byte[] filecertificate) {
        this.filecertificate = filecertificate;
    }

    public String getFilecertificateContentType() {
        return filecertificateContentType;
    }

    public void setFilecertificateContentType(String filecertificateContentType) {
        this.filecertificateContentType = filecertificateContentType;
    }

    public String getPath_key() {
        return path_key;
    }

    public void setPath_key(String path_key) {
        this.path_key = path_key;
    }

    public byte[] getFilekey() {
        return filekey;
    }

    public void setFilekey(byte[] filekey) {
        this.filekey = filekey;
    }

    public String getFilekeyContentType() {
        return filekeyContentType;
    }

    public void setFilekeyContentType(String filekeyContentType) {
        this.filekeyContentType = filekeyContentType;
    }

    public String getNumber_certificate() {
        return number_certificate;
    }

    public void setNumber_certificate(String number_certificate) {
        this.number_certificate = number_certificate;
    }

    public LocalDate getDate_certificate() {
        return date_certificate;
    }

    public void setDate_certificate(LocalDate date_certificate) {
        this.date_certificate = date_certificate;
    }

    public String getRfc_certificate() {
        return rfc_certificate;
    }

    public void setRfc_certificate(String rfc_certificate) {
        this.rfc_certificate = rfc_certificate;
    }

    public String getBussines_name_cert() {
        return bussines_name_cert;
    }

    public void setBussines_name_cert(String bussines_name_cert) {
        this.bussines_name_cert = bussines_name_cert;
    }

    public LocalDate getDate_created_cert() {
        return date_created_cert;
    }

    public void setDate_created_cert(LocalDate date_created_cert) {
        this.date_created_cert = date_created_cert;
    }

    public LocalDate getDate_expiration_cert() {
        return date_expiration_cert;
    }

    public void setDate_expiration_cert(LocalDate date_expiration_cert) {
        this.date_expiration_cert = date_expiration_cert;
    }

    public String getInfo_certificate() {
        return info_certificate;
    }

    public void setInfo_certificate(String info_certificate) {
        this.info_certificate = info_certificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taxpayer_certificate taxpayer_certificate = (Taxpayer_certificate) o;
        if(taxpayer_certificate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_certificate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_certificate{" +
            "id=" + id +
            ", path_certificate='" + path_certificate + "'" +
            ", filecertificate='" + filecertificate + "'" +
            ", filecertificateContentType='" + filecertificateContentType + "'" +
            ", path_key='" + path_key + "'" +
            ", filekey='" + filekey + "'" +
            ", filekeyContentType='" + filekeyContentType + "'" +
            ", number_certificate='" + number_certificate + "'" +
            ", date_certificate='" + date_certificate + "'" +
            ", rfc_certificate='" + rfc_certificate + "'" +
            ", bussines_name_cert='" + bussines_name_cert + "'" +
            ", date_created_cert='" + date_created_cert + "'" +
            ", date_expiration_cert='" + date_expiration_cert + "'" +
            ", info_certificate='" + info_certificate + "'" +
            '}';
    }
}
