package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Branch_office;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Branch_office entity.
 */
@SuppressWarnings("unused")
public interface Branch_officeRepository extends JpaRepository<Branch_office,Long> {

}
