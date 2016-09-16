package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_mail_accounts;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_mail_accounts entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_mail_accountsRepository extends JpaRepository<Taxpayer_mail_accounts,Long> {

}
