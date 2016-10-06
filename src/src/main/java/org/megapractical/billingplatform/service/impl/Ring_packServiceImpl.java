package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Ring_packService;
import org.megapractical.billingplatform.domain.Ring_pack;
import org.megapractical.billingplatform.repository.Ring_packRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Ring_pack.
 */
@Service
@Transactional
public class Ring_packServiceImpl implements Ring_packService{

    private final Logger log = LoggerFactory.getLogger(Ring_packServiceImpl.class);

    @Inject
    private Ring_packRepository ring_packRepository;

    /**
     * Save a ring_pack.
     *
     * @param ring_pack the entity to save
     * @return the persisted entity
     */
    public Ring_pack save(Ring_pack ring_pack) {
        log.debug("Request to save Ring_pack : {}", ring_pack);
        Ring_pack result = ring_packRepository.save(ring_pack);
        return result;
    }

    public boolean buytransactions(Integer idaccount, Integer idring_pack, Long iduser, Integer count){
        return true;
    }

    /**
     *  Get all the ring_packs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Ring_pack> findAll(Pageable pageable) {
        log.debug("Request to get all Ring_packs");
        Page<Ring_pack> result = ring_packRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one ring_pack by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Ring_pack findOne(Long id) {
        log.debug("Request to get Ring_pack : {}", id);
        Ring_pack ring_pack = ring_packRepository.findOne(id);
        return ring_pack;
    }

    /**
     *  Delete the  ring_pack by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ring_pack : {}", id);
        ring_packRepository.delete(id);
    }
}
