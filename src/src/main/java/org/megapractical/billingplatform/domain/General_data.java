package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A General_data.
 */
@Entity
@Table(name = "general_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class General_data implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "platform_name")
    private String platform_name;

    @Column(name = "format_date")
    private String format_date;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")    
    private String logoContentType;

    @Column(name = "adrees")
    private String adrees;

    @Column(name = "phones")
    private String phones;

    @Column(name = "path_root")
    private String path_root;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform_name() {
        return platform_name;
    }

    public void setPlatform_name(String platform_name) {
        this.platform_name = platform_name;
    }

    public String getFormat_date() {
        return format_date;
    }

    public void setFormat_date(String format_date) {
        this.format_date = format_date;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getAdrees() {
        return adrees;
    }

    public void setAdrees(String adrees) {
        this.adrees = adrees;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getPath_root() {
        return path_root;
    }

    public void setPath_root(String path_root) {
        this.path_root = path_root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        General_data general_data = (General_data) o;
        if(general_data.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, general_data.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "General_data{" +
            "id=" + id +
            ", platform_name='" + platform_name + "'" +
            ", format_date='" + format_date + "'" +
            ", logo='" + logo + "'" +
            ", logoContentType='" + logoContentType + "'" +
            ", adrees='" + adrees + "'" +
            ", phones='" + phones + "'" +
            ", path_root='" + path_root + "'" +
            '}';
    }
}
