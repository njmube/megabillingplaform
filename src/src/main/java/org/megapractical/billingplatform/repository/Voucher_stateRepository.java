package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Voucher_state;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Voucher_state entity.
 */
public interface Voucher_stateRepository extends JpaRepository<Voucher_state,Long> {

}
