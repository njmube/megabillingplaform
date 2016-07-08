package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Cfdi_states;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cfdi_states entity.
 */
@SuppressWarnings("unused")
public interface Cfdi_statesRepository extends JpaRepository<Cfdi_states,Long> {
    Page<Cfdi_states> findByNameStartingWith(String filtername, Pageable pageable);

}
