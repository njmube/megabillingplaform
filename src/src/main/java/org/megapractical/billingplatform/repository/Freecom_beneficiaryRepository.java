package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_beneficiary;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_beneficiary entity.
 */
@SuppressWarnings("unused")
public interface Freecom_beneficiaryRepository extends JpaRepository<Freecom_beneficiary,Long> {

}
