package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_colony;

import org.megapractical.billingplatform.domain.C_municipality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_colony entity.
 */
public interface C_colonyRepository extends JpaRepository<C_colony,Long> {
    List<C_colony> findByM(C_municipality municipality);
    Page<C_colony> findByCodeStartingWith(String filtername, Pageable pageable);
    List<C_colony> findByCodeStartingWith(String filtername);
}
