package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_class;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_class entity.
 */
public interface C_classRepository extends JpaRepository<C_class,Long> {

}
