package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Cfdi_template;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cfdi_template entity.
 */
public interface Cfdi_templateRepository extends JpaRepository<Cfdi_template,Long> {
    Page<Cfdi_template> findByNameStartingWith(String filtername, Pageable pageable);
}
