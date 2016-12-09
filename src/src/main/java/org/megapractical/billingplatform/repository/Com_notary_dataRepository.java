package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_notary_data;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_notary_data entity.
 */
public interface Com_notary_dataRepository extends JpaRepository<Com_notary_data,Long> {

}
