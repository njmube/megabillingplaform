package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.service.TracemgService;
import org.megapractical.billingplatform.repository.TracemgRepository;
import org.megapractical.billingplatform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.net.InetAddress;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
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

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    UserService userService;

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

            String address = SecurityUtils.ipCliente;
            log.debug("IP Client : " + address);
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

    public Integer getTimeFailLogin(Integer time, Integer delay){
        ZonedDateTime to = ZonedDateTime.now().minusMinutes(new Long(time.toString()));
        List<Tracemg> result = tracemgRepository.findByIpAndTimestampBetweenOrderByIdDesc(SecurityUtils.ipCliente,to,ZonedDateTime.now());

        Integer count = 0;
        Integer failLogin = 0;
        List<Integer> postions = new ArrayList<>();
        Integer min = 0;
        ZonedDateTime lastfail = ZonedDateTime.now();
        Long idevent = new Long("3");
        Long idstate = new Long("2");
        for(Tracemg trace: result){
            count++;
            if(trace.getAudit_event_type().getId() == idevent && trace.getC_state_event().getId()==idstate){
                failLogin++;
                postions.add(count);
                if(failLogin == 1){
                    lastfail = trace.getTimestamp();
                }
            }
        }
        if(failLogin >= 3){
            if(postions.get(0) == postions.get(1)-1 && postions.get(1) == postions.get(2)-1){
                Long minutosnow = (ZonedDateTime.now().toEpochSecond()-lastfail.toEpochSecond())/60;
                if(minutosnow < time){
                    min = delay - Integer.parseInt(minutosnow.toString());
                }
            }
        }
        return min;
    }


    public Tracemg saveTraceUser(String user, Audit_event_type audit_event_type, C_state_event c_state_event){
        try {
            Tracemg tracemg = new Tracemg();

            String address = SecurityUtils.ipCliente;
            log.debug("IP Client : " + address);
            tracemg.setPrincipal(user);
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

    public Page<Tracemg> findCustom(ZonedDateTime from, ZonedDateTime to,String principal, String auditEventType, String ip, Pageable pageable){
        log.debug("Request to get some Tracemgs");
        Page<Tracemg> result = tracemgRepository.findByTimestampBetweenOrderByIdDesc(from, to, pageable);
        if(principal.compareTo(" ")!=0 && ip.compareTo(" ")==0){
           result = tracemgRepository.findByPrincipalAndTimestampBetweenOrderByIdDesc(principal,from,to,pageable);
        }
        if(principal.compareTo(" ")==0 && ip.compareTo(" ")!=0){
           result = tracemgRepository.findByIpAndTimestampBetweenOrderByIdDesc(ip, from, to, pageable);
        }
        if(principal.compareTo(" ")!=0 && ip.compareTo(" ")!=0){
           result = tracemgRepository.findByIpAndPrincipalAndTimestampBetweenOrderByIdDesc(ip,principal,from,to,pageable);
        }
        if(auditEventType.compareTo(" ")!=0){
            Audit_event_type au = audit_event_typeService.findByName(auditEventType);
            List<Tracemg> list = new ArrayList<>();
            if(au != null){
                for(Tracemg trace: result.getContent()){
                    if(trace.getAudit_event_type().getName().compareTo(au.getName())==0){
                        list.add(trace);
                    }
                }
                Page<Tracemg> page = new PageImpl<Tracemg>(list,pageable,result.getTotalElements());
                return page;
            }
        }

        return result;
    }

    public Page<TracemgAccount> findCustomAccount(String principal, Pageable pageable){
        log.debug("Request to get some Tracemgs Account");
        log.debug("PRINCIPAL: " + principal);

        //Page<Tracemg> result = tracemgRepository.findByPrincipalOrderByIdDesc(principal,pageable);
        List<Tracemg> result = tracemgRepository.findByPrincipalOrderByIdDesc(principal);
        List<TracemgAccount> list = new ArrayList<>();
        long id = 1;
        int cont = 1;
        for(Tracemg trace: result){
            if(trace.getAudit_event_type() != null) {
                Long idtrace = trace.getAudit_event_type().getId();
                Long start = new Long("38");
                Long end = new Long("52");
                //log.debug("ID de tipo de evento: " + idtrace.toString());
                if (cont <= 10 && principal.compareTo(trace.getPrincipal()) == 0 && idtrace >= start && idtrace <= end) {
                    TracemgAccount newTrace = new TracemgAccount();
                    newTrace.setID(id);
                    newTrace.setTimestamp(trace.getTimestamp().toLocalDateTime().toString().replace('T', ' '));
                    newTrace.setResult(trace.getC_state_event().getName());
                    newTrace.setOperation(trace.getAudit_event_type().getDescription());
                    newTrace.setModulo("Facturación en línea");
                    User user = userService.getUserWithAuthoritiesByLogin(trace.getPrincipal()).get();
                    newTrace.setRfc(user.getRFC());
                    list.add(newTrace);
                    id++;
                    cont++;
                }
            }
        }
        Page<TracemgAccount> page = new PageImpl<TracemgAccount>(list,pageable,list.size());
        return page;
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
