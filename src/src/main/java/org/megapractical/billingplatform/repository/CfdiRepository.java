package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Cfdi;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cfdi entity.
 */
@SuppressWarnings("unused")
public interface CfdiRepository extends JpaRepository<Cfdi,Long> {

}
