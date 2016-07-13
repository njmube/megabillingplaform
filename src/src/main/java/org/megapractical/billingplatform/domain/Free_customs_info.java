package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Free_customs_info.
 */
@javax.persistence.Entity
@Table(name = "free_customs_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_customs_info implements Serializable {

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
    private Free_concept free_concept;

    @ManyToOne
    private Free_part_concept free_part_concept;

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

    public Free_concept getFree_concept() {
        return free_concept;
    }

    public void setFree_concept(Free_concept free_concept) {
        this.free_concept = free_concept;
    }

    public Free_part_concept getFree_part_concept() {
        return free_part_concept;
    }

    public void setFree_part_concept(Free_part_concept free_part_concept) {
        this.free_part_concept = free_part_concept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Free_customs_info free_customs_info = (Free_customs_info) o;
        if(free_customs_info.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, free_customs_info.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Free_customs_info{" +
            "id=" + id +
            ", number_doc='" + number_doc + "'" +
            ", date='" + date + "'" +
            ", customs='" + customs + "'" +
            '}';
    }
}
