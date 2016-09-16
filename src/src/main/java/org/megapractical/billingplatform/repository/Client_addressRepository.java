package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Client_address;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Client_address entity.
 */
@SuppressWarnings("unused")
public interface Client_addressRepository extends JpaRepository<Client_address,Long> {

}
