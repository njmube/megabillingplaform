package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Tax_retentions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tax_retentions entity.
 */
public interface Tax_retentionsRepository extends JpaRepository<Tax_retentions,Long> {

}
