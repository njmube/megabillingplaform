package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_request;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_request entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_requestRepository extends JpaRepository<Taxpayer_request,Long> {

}
