package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Rate_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rate_type entity.
 */
public interface Rate_typeRepository extends JpaRepository<Rate_type,Long> {

}
