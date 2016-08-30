package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Request_taxpayer_account;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Request_taxpayer_account entity.
 */
@SuppressWarnings("unused")
public interface Request_taxpayer_accountRepository extends JpaRepository<Request_taxpayer_account,Long> {

    @Query("select request_taxpayer_account from Request_taxpayer_account request_taxpayer_account where request_taxpayer_account.user.login = ?#{principal.username}")
    List<Request_taxpayer_account> findByUserIsCurrentUser();

}
