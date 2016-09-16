package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Transactions_history;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Transactions_history entity.
 */
@SuppressWarnings("unused")
public interface Transactions_historyRepository extends JpaRepository<Transactions_history,Long> {

    @Query("select transactions_history from Transactions_history transactions_history where transactions_history.user.login = ?#{principal.username}")
    List<Transactions_history> findByUserIsCurrentUser();

}
