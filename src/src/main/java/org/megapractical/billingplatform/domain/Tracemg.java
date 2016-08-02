package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Tracemg.
 */
@Entity
@Table(name = "tracemg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tracemg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "principal", nullable = false)
    private String principal;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @NotNull
    @Column(name = "ip", nullable = false)
    private String ip;

    @ManyToOne
    private Audit_event_type audit_event_type;

    @ManyToOne
    private C_state_event c_state_event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Audit_event_type getAudit_event_type() {
        return audit_event_type;
    }

    public void setAudit_event_type(Audit_event_type audit_event_type) {
        this.audit_event_type = audit_event_type;
    }

    public C_state_event getC_state_event() {
        return c_state_event;
    }

    public void setC_state_event(C_state_event c_state_event) {
        this.c_state_event = c_state_event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tracemg tracemg = (Tracemg) o;
        if(tracemg.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tracemg.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tracemg{" +
            "id=" + id +
            ", principal='" + principal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", ip='" + ip + "'" +
            '}';
    }
}
