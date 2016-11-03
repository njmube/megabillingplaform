package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_beneficiary;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_beneficiary entity.
 */
@SuppressWarnings("unused")
public interface Com_beneficiaryRepository extends JpaRepository<Com_beneficiary,Long> {

}
