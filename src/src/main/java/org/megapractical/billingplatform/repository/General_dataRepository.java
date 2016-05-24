package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.General_data;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the General_data entity.
 */
public interface General_dataRepository extends JpaRepository<General_data,Long> {

}
