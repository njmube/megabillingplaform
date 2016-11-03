package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_incoterm;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_incoterm entity.
 */
@SuppressWarnings("unused")
public interface Com_incotermRepository extends JpaRepository<Com_incoterm,Long> {

}
