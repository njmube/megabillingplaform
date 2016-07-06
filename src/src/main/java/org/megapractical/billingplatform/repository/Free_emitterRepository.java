package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Free_emitter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Free_emitter entity.
 */
@SuppressWarnings("unused")
public interface Free_emitterRepository extends JpaRepository<Free_emitter,Long> {

}
