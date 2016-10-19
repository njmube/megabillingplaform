package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_concept;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_concept entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_conceptRepository extends JpaRepository<Taxpayer_concept,Long> {

}
