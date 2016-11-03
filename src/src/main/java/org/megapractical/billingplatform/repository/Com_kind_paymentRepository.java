package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_kind_payment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_kind_payment entity.
 */
@SuppressWarnings("unused")
public interface Com_kind_paymentRepository extends JpaRepository<Com_kind_payment,Long> {

}
