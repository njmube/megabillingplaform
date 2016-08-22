package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_specific_descriptions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_specific_descriptions entity.
 */
@SuppressWarnings("unused")
public interface Freecom_specific_descriptionsRepository extends JpaRepository<Freecom_specific_descriptions,Long> {

}
