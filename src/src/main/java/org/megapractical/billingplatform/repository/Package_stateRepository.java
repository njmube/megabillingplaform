package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Package_state;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Package_state entity.
 */
public interface Package_stateRepository extends JpaRepository<Package_state,Long> {
    Page<Package_state> findByNameStartingWith(String filtername, Pageable pageable);

}
