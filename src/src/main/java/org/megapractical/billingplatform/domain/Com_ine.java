package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_ine.
 */
@Entity
@Table(name = "com_ine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_ine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "version", length = 50, nullable = false)
    private String version;

    @Column(name = "ident")
    private Integer ident;

    @OneToOne
    @JoinColumn(unique = true)
    private Cfdi cfdi;

    @ManyToOne
    private C_committee_type c_committee_type;

    @ManyToOne
    private C_process_type c_process_type;

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

    public Integer getIdent() {
        return ident;
    }

    public void setIdent(Integer ident) {
        this.ident = ident;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    public C_committee_type getC_committee_type() {
        return c_committee_type;
    }

    public void setC_committee_type(C_committee_type c_committee_type) {
        this.c_committee_type = c_committee_type;
    }

    public C_process_type getC_process_type() {
        return c_process_type;
    }

    public void setC_process_type(C_process_type c_process_type) {
        this.c_process_type = c_process_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_ine com_ine = (Com_ine) o;
        if(com_ine.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_ine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_ine{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", ident='" + ident + "'" +
            '}';
    }
}
