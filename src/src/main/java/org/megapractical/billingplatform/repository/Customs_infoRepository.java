package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Customs_info;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Customs_info entity.
 */
@SuppressWarnings("unused")
public interface Customs_infoRepository extends JpaRepository<Customs_info,Long> {

}
