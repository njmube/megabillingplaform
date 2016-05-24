package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Voucher_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Voucher_type entity.
 */
public interface Voucher_typeRepository extends JpaRepository<Voucher_type,Long> {

}
