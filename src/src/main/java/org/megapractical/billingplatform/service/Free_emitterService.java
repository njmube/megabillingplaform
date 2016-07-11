package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Free_emitter.
 */
public interface Free_emitterService {

    /**
     * Save a free_emitter.
     *
     * @param free_emitter the entity to save
     * @return the persisted entity
     */
    Free_emitter save(Free_emitter free_emitter);

    /**
     *  Get all the free_emitters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_emitter> findAll(Pageable pageable);

    Free_emitter findOneByRfc(String rfc);

    /**
     *  Get the "id" free_emitter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_emitter findOne(Long id);

    Free_emitter saveFile(Free_emitter free_emitter);

    Free_emitter getFile(Free_emitter free_emitter);

    /**
     *  Delete the "id" free_emitter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Free_emitter findOneByUser(User user);
}
