package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_certificate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_certificate entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_certificateRepository extends JpaRepository<Taxpayer_certificate,Long> {

}
