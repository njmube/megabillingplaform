package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Free_concept;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Free_concept entity.
 */
@SuppressWarnings("unused")
public interface Free_conceptRepository extends JpaRepository<Free_concept,Long> {

}
