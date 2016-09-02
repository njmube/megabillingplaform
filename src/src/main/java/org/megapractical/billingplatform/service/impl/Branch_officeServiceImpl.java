package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Branch_officeService;
import org.megapractical.billingplatform.domain.Branch_office;
import org.megapractical.billingplatform.repository.Branch_officeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Branch_office.
 */
@Service
@Transactional
public class Branch_officeServiceImpl implements Branch_officeService{

    private final Logger log = LoggerFactory.getLogger(Branch_officeServiceImpl.class);
    
    @Inject
    private Branch_officeRepository branch_officeRepository;
    
    /**
     * Save a branch_office.
     * 
     * @param branch_office the entity to save
     * @return the persisted entity
     */
    public Branch_office save(Branch_office branch_office) {
        log.debug("Request to save Branch_office : {}", branch_office);
        Branch_office result = branch_officeRepository.save(branch_office);
        return result;
    }

    /**
     *  Get all the branch_offices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Branch_office> findAll(Pageable pageable) {
        log.debug("Request to get all Branch_offices");
        Page<Branch_office> result = branch_officeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one branch_office by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Branch_office findOne(Long id) {
        log.debug("Request to get Branch_office : {}", id);
        Branch_office branch_office = branch_officeRepository.findOne(id);
        return branch_office;
    }

    /**
     *  Delete the  branch_office by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Branch_office : {}", id);
        branch_officeRepository.delete(id);
    }
}
