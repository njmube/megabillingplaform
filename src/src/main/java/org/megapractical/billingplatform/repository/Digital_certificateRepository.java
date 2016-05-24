package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Digital_certificate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Digital_certificate entity.
 */
public interface Digital_certificateRepository extends JpaRepository<Digital_certificate,Long> {

}
