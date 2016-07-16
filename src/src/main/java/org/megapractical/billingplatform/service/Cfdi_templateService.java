package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Cfdi_template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Cfdi_template.
 */
public interface Cfdi_templateService {

    /**
     * Save a cfdi_template.
     *
     * @param cfdi_template the entity to save
     * @return the persisted entity
     */
    Cfdi_template save(Cfdi_template cfdi_template);

    /**
     *  Get all the cfdi_templates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cfdi_template> findAll(Pageable pageable);

    Page<Cfdi_template> findAllByName(String filtername, Pageable pageable);

    /**
     *  Get the "id" cfdi_template.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Cfdi_template findOne(Long id);

    /**
     *  Delete the "id" cfdi_template.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
