package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_accounting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_accounting entity.
 */
@SuppressWarnings("unused")
public interface Com_accountingRepository extends JpaRepository<Com_accounting,Long> {

}
