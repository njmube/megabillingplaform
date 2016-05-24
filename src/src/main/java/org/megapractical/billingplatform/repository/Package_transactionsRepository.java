package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Package_transactions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Package_transactions entity.
 */
public interface Package_transactionsRepository extends JpaRepository<Package_transactions,Long> {

}
