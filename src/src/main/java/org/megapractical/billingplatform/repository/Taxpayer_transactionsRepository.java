package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_transactions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_transactions entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_transactionsRepository extends JpaRepository<Taxpayer_transactions,Long> {

}
