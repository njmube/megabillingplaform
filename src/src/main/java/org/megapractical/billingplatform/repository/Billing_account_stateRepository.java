package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Billing_account_state;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Billing_account_state entity.
 */
public interface Billing_account_stateRepository extends JpaRepository<Billing_account_state,Long> {

}
