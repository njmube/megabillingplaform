package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Committee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Committee entity.
 */
public interface CommitteeRepository extends JpaRepository<Committee,Long> {

}
