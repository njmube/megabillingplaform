package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Scope;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Scope entity.
 */
public interface ScopeRepository extends JpaRepository<Scope,Long> {

}
