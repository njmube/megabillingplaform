package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_incoterm;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_incoterm entity.
 */
@SuppressWarnings("unused")
public interface Freecom_incotermRepository extends JpaRepository<Freecom_incoterm,Long> {

}
