package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_complement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_complement entity.
 */
@SuppressWarnings("unused")
public interface C_complementRepository extends JpaRepository<C_complement,Long> {
    Page<C_complement> findByNameStartingWith(String filtername, Pageable pageable);
    List<C_complement> findByNameStartingWith(String filtername);

}
