package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_municipality;

import org.megapractical.billingplatform.domain.C_state;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_municipality entity.
 */
public interface C_municipalityRepository extends JpaRepository<C_municipality,Long> {
    List<C_municipality> findByS(C_state state);

}
