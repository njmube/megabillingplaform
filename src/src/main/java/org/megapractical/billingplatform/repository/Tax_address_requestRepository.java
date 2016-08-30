package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Tax_address_request;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tax_address_request entity.
 */
@SuppressWarnings("unused")
public interface Tax_address_requestRepository extends JpaRepository<Tax_address_request,Long> {

}
