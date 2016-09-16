package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_ftp_account;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_ftp_account entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_ftp_accountRepository extends JpaRepository<Taxpayer_ftp_account,Long> {

}
