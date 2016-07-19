package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_zip_code;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_zip_code entity.
 */
public interface C_zip_codeRepository extends JpaRepository<C_zip_code,Long> {
    Page<C_zip_code> findByCodeStartingWith(String filtername, Pageable pageable);
    List<C_zip_code> findByCodeStartingWith(String filtername);
}
