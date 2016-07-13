package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Well_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Well_type entity.
 */
public interface Well_typeRepository extends JpaRepository<Well_type,Long> {

}
