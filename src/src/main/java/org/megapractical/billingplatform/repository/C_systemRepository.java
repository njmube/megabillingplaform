package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_system;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_system entity.
 */
@SuppressWarnings("unused")
public interface C_systemRepository extends JpaRepository<C_system,Long> {

}
