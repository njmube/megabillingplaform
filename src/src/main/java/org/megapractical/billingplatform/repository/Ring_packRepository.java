package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Ring_pack;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ring_pack entity.
 */
@SuppressWarnings("unused")
public interface Ring_packRepository extends JpaRepository<Ring_pack,Long> {

}
