package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_country;
import org.megapractical.billingplatform.domain.C_state;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_state entity.
 */
public interface C_stateRepository extends JpaRepository<C_state,Long> {

}
