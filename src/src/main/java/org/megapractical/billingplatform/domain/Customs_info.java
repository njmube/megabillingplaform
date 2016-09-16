package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Customs_info.
 */
@Entity
@Table(name = "customs_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customs_info implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "number_doc", nullable = false)
    private String number_doc;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "customs")
    private String customs;

    @ManyToOne
    private Concept concept;

    @ManyToOne
    private Part_concept part_concept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber_doc() {
        return number_doc;
    }

    public void setNumber_doc(String number_doc) {
        this.number_doc = number_doc;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public Part_concept getPart_concept() {
        return part_concept;
    }

    public void setPart_concept(Part_concept part_concept) {
        this.part_concept = part_concept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customs_info customs_info = (Customs_info) o;
        if(customs_info.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customs_info.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customs_info{" +
            "id=" + id +
            ", number_doc='" + number_doc + "'" +
            ", date='" + date + "'" +
            ", customs='" + customs + "'" +
            '}';
    }
}
