package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_storeroom_paybill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_storeroom_paybill entity.
 */
@SuppressWarnings("unused")
public interface Com_storeroom_paybillRepository extends JpaRepository<Com_storeroom_paybill,Long> {

}
