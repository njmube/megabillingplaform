package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_money;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_money entity.
 */
public interface C_moneyRepository extends JpaRepository<C_money,Long> {

}
