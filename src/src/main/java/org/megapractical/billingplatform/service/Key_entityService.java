package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Key_entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Key_entity.
 */
public interface Key_entityService {

    /**
     * Save a key_entity.
     *
     * @param key_entity the entity to save
     * @return the persisted entity
     */
    Key_entity save(Key_entity key_entity);

    /**
     *  Get all the key_entities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Key_entity> findAll(Pageable pageable);

    /**
     *  Get the "id" key_entity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Key_entity findOne(Long id);

    /**
     *  Delete the "id" key_entity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<Key_entity> findAll();

}
