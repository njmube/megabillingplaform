package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_paybill_concept;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_paybill_concept entity.
 */
@SuppressWarnings("unused")
public interface Com_paybill_conceptRepository extends JpaRepository<Com_paybill_concept,Long> {

}
