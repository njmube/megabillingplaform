package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Way_payment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Way_payment entity.
 */
@SuppressWarnings("unused")
public interface Way_paymentRepository extends JpaRepository<Way_payment,Long> {

}
