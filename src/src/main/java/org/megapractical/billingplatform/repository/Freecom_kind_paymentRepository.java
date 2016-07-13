package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_kind_payment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_kind_payment entity.
 */
public interface Freecom_kind_paymentRepository extends JpaRepository<Freecom_kind_payment,Long> {

}
