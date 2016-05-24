package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Services;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Services entity.
 */
public interface ServicesRepository extends JpaRepository<Services,Long> {

}
