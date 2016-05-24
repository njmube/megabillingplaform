package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Package_state;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Package_state entity.
 */
public interface Package_stateRepository extends JpaRepository<Package_state,Long> {

}
