package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Request_state;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Request_state entity.
 */
public interface Request_stateRepository extends JpaRepository<Request_state,Long> {

    Page<Request_state> findByNameStartingWith(String filtername, Pageable pageable);

}
