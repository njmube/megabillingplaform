package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Cfdi_type_doc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cfdi_type_doc entity.
 */
public interface Cfdi_type_docRepository extends JpaRepository<Cfdi_type_doc,Long> {
    Page<Cfdi_type_doc> findByNameStartingWith(String filtername, Pageable pageable);

}
