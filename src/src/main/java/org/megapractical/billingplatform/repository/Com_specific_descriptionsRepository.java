package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_specific_descriptions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_specific_descriptions entity.
 */
@SuppressWarnings("unused")
public interface Com_specific_descriptionsRepository extends JpaRepository<Com_specific_descriptions,Long> {

}
