package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Billing_account_state;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Billing_account_state entity.
 */
public interface Billing_account_stateRepository extends JpaRepository<Billing_account_state,Long> {
    Page<Billing_account_state> findByNameStartingWith(String filtername, Pageable pageable);
}
