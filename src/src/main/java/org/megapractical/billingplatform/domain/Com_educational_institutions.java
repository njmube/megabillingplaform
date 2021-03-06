package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Com_educational_institutions.
 */
@Entity
@Table(name = "com_educational_institutions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_educational_institutions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "name_student", nullable = false)
    private String name_student;

    @NotNull
    @Column(name = "curp", nullable = false)
    private String curp;

    @NotNull
    @Column(name = "autrvoe", nullable = false)
    private String autrvoe;

    @Column(name = "rfcpayment")
    private String rfcpayment;

    @OneToOne
    @JoinColumn(unique = true)
    private Cfdi cfdi;

    @ManyToOne
    private C_school_level c_school_level;

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

    public String getName_student() {
        return name_student;
    }

    public void setName_student(String name_student) {
        this.name_student = name_student;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getAutrvoe() {
        return autrvoe;
    }

    public void setAutrvoe(String autrvoe) {
        this.autrvoe = autrvoe;
    }

    public String getRfcpayment() {
        return rfcpayment;
    }

    public void setRfcpayment(String rfcpayment) {
        this.rfcpayment = rfcpayment;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    public C_school_level getC_school_level() {
        return c_school_level;
    }

    public void setC_school_level(C_school_level c_school_level) {
        this.c_school_level = c_school_level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_educational_institutions com_educational_institutions = (Com_educational_institutions) o;
        if(com_educational_institutions.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_educational_institutions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_educational_institutions{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", name_student='" + name_student + "'" +
            ", curp='" + curp + "'" +
            ", autrvoe='" + autrvoe + "'" +
            ", rfcpayment='" + rfcpayment + "'" +
            '}';
    }
}
