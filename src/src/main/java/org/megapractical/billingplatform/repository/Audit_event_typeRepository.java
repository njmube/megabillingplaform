package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Audit_event_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Audit_event_type entity.
 */
public interface Audit_event_typeRepository extends JpaRepository<Audit_event_type,Long> {

}
