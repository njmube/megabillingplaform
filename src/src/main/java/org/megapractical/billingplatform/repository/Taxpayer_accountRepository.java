package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_account;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_account entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_accountRepository extends JpaRepository<Taxpayer_account,Long> {

    @Query("select distinct taxpayer_account from Taxpayer_account taxpayer_account left join fetch taxpayer_account.tax_regimes left join fetch taxpayer_account.users")
    List<Taxpayer_account> findAllWithEagerRelationships();

    @Query("select taxpayer_account from Taxpayer_account taxpayer_account left join fetch taxpayer_account.tax_regimes left join fetch taxpayer_account.users where taxpayer_account.id =:id")
    Taxpayer_account findOneWithEagerRelationships(@Param("id") Long id);

}
