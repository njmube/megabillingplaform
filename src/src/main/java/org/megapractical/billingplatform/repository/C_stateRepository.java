package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_country;
import org.megapractical.billingplatform.domain.C_state;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_state entity.
 */
public interface C_stateRepository extends JpaRepository<C_state,Long> {

    List<C_state> findByC(C_country country);
    Page<C_state> findByNameStartingWith(String filtername, Pageable pageable);
    List<C_state> findByNameStartingWith(String filtername);

}
