package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Accounting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Accounting entity.
 */
@SuppressWarnings("unused")
public interface AccountingRepository extends JpaRepository<Accounting,Long> {

}
