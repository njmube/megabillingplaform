package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.School_level;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the School_level entity.
 */
public interface School_levelRepository extends JpaRepository<School_level,Long> {

}
