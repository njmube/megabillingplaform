package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.TracemgService;
import org.megapractical.billingplatform.domain.Tracemg;
import org.megapractical.billingplatform.repository.TracemgRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing Tracemg.
 */
@Service
@Transactional
public class TracemgServiceImpl implements TracemgService{

    private final Logger log = LoggerFactory.getLogger(TracemgServiceImpl.class);

    @Inject
    private TracemgRepository tracemgRepository;

    /**
     * Save a tracemg.
     *
     * @param tracemg the entity to save
     * @return the persisted entity
     */
    public Tracemg save(Tracemg tracemg) {
        log.debug("Request to save Tracemg : {}", tracemg);
        Tracemg result = tracemgRepository.save(tracemg);
        return result;
    }

    public Tracemg saveTrace(Audit_event_type audit_event_type, C_state_event c_state_event){
        try {
            Tracemg tracemg = new Tracemg();
            InetAddress localHost = InetAddress.getLocalHost();
            String address = localHost.getHostAddress();
            tracemg.setPrincipal(SecurityUtils.getCurrentUserLogin());
            tracemg.setAudit_event_type(audit_event_type);
            tracemg.setC_state_event(c_state_event);
            tracemg.setTimestamp(ZonedDateTime.now());
            tracemg.setIp(address);
            Tracemg result = tracemgRepository.save(tracemg);
            return result;
        }catch (Exception ex){

        }
        return null;
    }

    public Page<Tracemg> findAll(ZonedDateTime from, ZonedDateTime to, Pageable pageable){
        return tracemgRepository.findByTimestampBetweenOrderByIdDesc(from, to, pageable);
    }

    /**
     *  Get all the tracemgs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tracemg> findAll(Pageable pageable) {
        log.debug("Request to get all Tracemgs");
        Page<Tracemg> result = tracemgRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tracemg by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tracemg findOne(Long id) {
        log.debug("Request to get Tracemg : {}", id);
        Tracemg tracemg = tracemgRepository.findOne(id);
        return tracemg;
    }

    /**
     *  Delete the  tracemg by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tracemg : {}", id);
        tracemgRepository.delete(id);
    }
}
