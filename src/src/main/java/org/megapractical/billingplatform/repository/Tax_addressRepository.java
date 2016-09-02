package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Tax_address;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tax_address entity.
 */
@SuppressWarnings("unused")
public interface Tax_addressRepository extends JpaRepository<Tax_address,Long> {

}
