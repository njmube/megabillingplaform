package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_request_confirm;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_request_confirm entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_request_confirmRepository extends JpaRepository<Taxpayer_request_confirm,Long> {

}
