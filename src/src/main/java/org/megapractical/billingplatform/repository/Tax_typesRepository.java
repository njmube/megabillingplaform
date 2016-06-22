package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Tax_types;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tax_types entity.
 */
@SuppressWarnings("unused")
public interface Tax_typesRepository extends JpaRepository<Tax_types,Long> {

}
