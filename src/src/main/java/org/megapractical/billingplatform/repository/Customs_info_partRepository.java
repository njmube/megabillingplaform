package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Customs_info_part;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Customs_info_part entity.
 */
@SuppressWarnings("unused")
public interface Customs_info_partRepository extends JpaRepository<Customs_info_part,Long> {

}
