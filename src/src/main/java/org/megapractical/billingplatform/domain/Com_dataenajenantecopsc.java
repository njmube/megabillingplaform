package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Com_dataenajenantecopsc.
 */
@Entity
@Table(name = "com_dataenajenantecopsc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Com_dataenajenantecopsc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "mother_last_name")
    private String mother_last_name;

    @NotNull
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}$")
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @Column(name = "curp")
    private String curp;

    @NotNull
    @Column(name = "percentage", precision=10, scale=2, nullable = false)
    private BigDecimal percentage;

    @ManyToOne
    private Com_data_enajenante com_data_enajenante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMother_last_name() {
        return mother_last_name;
    }

    public void setMother_last_name(String mother_last_name) {
        this.mother_last_name = mother_last_name;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Com_data_enajenante getCom_data_enajenante() {
        return com_data_enajenante;
    }

    public void setCom_data_enajenante(Com_data_enajenante com_data_enajenante) {
        this.com_data_enajenante = com_data_enajenante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Com_dataenajenantecopsc com_dataenajenantecopsc = (Com_dataenajenantecopsc) o;
        if(com_dataenajenantecopsc.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, com_dataenajenantecopsc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Com_dataenajenantecopsc{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", last_name='" + last_name + "'" +
            ", mother_last_name='" + mother_last_name + "'" +
            ", rfc='" + rfc + "'" +
            ", curp='" + curp + "'" +
            ", percentage='" + percentage + "'" +
            '}';
    }
}
