package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_colony;

import org.megapractical.billingplatform.domain.C_municipality;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_colony entity.
 */
public interface C_colonyRepository extends JpaRepository<C_colony,Long> {


    List<C_colony> findByM(C_municipality municipality);

}
