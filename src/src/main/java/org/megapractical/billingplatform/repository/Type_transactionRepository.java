package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Type_transaction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Type_transaction entity.
 */
@SuppressWarnings("unused")
public interface Type_transactionRepository extends JpaRepository<Type_transaction,Long> {

}
