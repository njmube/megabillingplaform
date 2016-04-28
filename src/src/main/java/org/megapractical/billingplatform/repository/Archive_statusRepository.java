package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Archive_status;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Archive_status entity.
 */
public interface Archive_statusRepository extends JpaRepository<Archive_status,Long> {

}
