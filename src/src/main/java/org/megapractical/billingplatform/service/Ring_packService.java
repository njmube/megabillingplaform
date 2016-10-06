package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Ring_pack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Ring_pack.
 */
public interface Ring_packService {

    /**
     * Save a ring_pack.
     *
     * @param ring_pack the entity to save
     * @return the persisted entity
     */
    Ring_pack save(Ring_pack ring_pack);

    /**
     *  Get all the ring_packs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Ring_pack> findAll(Pageable pageable);

    boolean buytransactions(Integer idaccount, Integer idring_pack, Long iduser, Integer count);
    /**
     *  Get the "id" ring_pack.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Ring_pack findOne(Long id);

    /**
     *  Delete the "id" ring_pack.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
