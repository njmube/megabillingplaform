package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_state_event;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_state_event entity.
 */
public interface C_state_eventRepository extends JpaRepository<C_state_event,Long> {

}
