package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Cfdi_types;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cfdi_types entity.
 */
@SuppressWarnings("unused")
public interface Cfdi_typesRepository extends JpaRepository<Cfdi_types,Long> {

}
