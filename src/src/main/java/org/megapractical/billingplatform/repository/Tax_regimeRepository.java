package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Tax_regime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tax_regime entity.
 */
public interface Tax_regimeRepository extends JpaRepository<Tax_regime,Long> {
    Page<Tax_regime> findByNameStartingWith(String filtername, Pageable pageable);

}
