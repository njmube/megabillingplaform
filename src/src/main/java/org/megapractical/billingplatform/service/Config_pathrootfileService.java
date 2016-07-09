package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Config_pathrootfile.
 */
public interface Config_pathrootfileService {

    /**
     * Save a config_pathrootfile.
     *
     * @param config_pathrootfile the entity to save
     * @return the persisted entity
     */
    Config_pathrootfile save(Config_pathrootfile config_pathrootfile);

    /**
     *  Get all the config_pathrootfiles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Config_pathrootfile> findAll(Pageable pageable);

    List<Config_pathrootfile> finAll();

    /**
     *  Get the "id" config_pathrootfile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Config_pathrootfile findOne(Long id);

    /**
     *  Delete the "id" config_pathrootfile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
