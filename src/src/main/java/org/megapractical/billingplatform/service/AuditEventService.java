package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.config.audit.AuditEventConverter;
import org.megapractical.billingplatform.domain.PersistentAuditEvent;
import org.megapractical.billingplatform.repository.PersistenceAuditEventRepository;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.megapractical.billingplatform.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

/**
 * Service for managing audit events.
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 * </p>
 */
@Service
@Transactional
public class AuditEventService {

    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    private AuditEventConverter auditEventConverter;

    private final Logger log = LoggerFactory.getLogger(AuditEventService.class);

    @Inject
    public AuditEventService(
        PersistenceAuditEventRepository persistenceAuditEventRepository,
        AuditEventConverter auditEventConverter) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
    }

    public Page<AuditEvent> findAll(Pageable pageable) {
        return persistenceAuditEventRepository.findAll(pageable)
            .map(persistentAuditEvents -> auditEventConverter.convertToAuditEvent(persistentAuditEvents));
    }

    public Page<AuditEvent> findByDates(LocalDate fromDate, LocalDate toDate,String principal,
                                        String auditEventType, String ip, Pageable pageable) {
        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.atStartOfDay();
        if(toDate.toString().compareTo("0001-01-01") == 0){
            to = LocalDateTime.now();
        }
        log.debug("Parametros del filtro: " + from.toString() + " - " + to.toString() + " - " + principal + " - " + auditEventType +
            " - " + ip);
        List<PersistentAuditEvent> list = new ArrayList<>();
        if(principal.compareTo(" ")==0 && auditEventType.compareTo(" ")==0){
            list = persistenceAuditEventRepository.findAllByAuditEventDateBetween(from,to);
        }
        else
        {
            if(principal.compareTo(" ")==0&& auditEventType.compareTo(" ")!=0){
                //Buscar por auditEventType
                list = persistenceAuditEventRepository.findAllByAuditEventDateBetweenAndAuditEventTypeStartingWith(from,to,auditEventType);
            }
            if(principal.compareTo(" ")!=0&& auditEventType.compareTo(" ")==0){
                //Buscar por principal
                list = persistenceAuditEventRepository.findAllByAuditEventDateBetweenAndPrincipalStartingWith(from, to, principal);
            }
            if(principal.compareTo(" ")!=0&& auditEventType.compareTo(" ")!=0){
                //Buscar por principal y auditEventType
                list = persistenceAuditEventRepository.findAllByAuditEventDateBetweenAndPrincipalStartingWithAndAuditEventTypeStartingWith(from,to,principal, auditEventType);
            }
        }
        List<AuditEvent> result = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            boolean OK = true;

            if(ip.compareTo(" ")!=0){
                log.debug("remoteAddress: {}",list.get(i).getData().get("remoteAddress"));
                if(list.get(i).getData().get("remoteAddress")!=null){
                    if(list.get(i).getData().get("remoteAddress").compareTo(ip)!=0){
                        OK = false;
                    }
                }
            }
            if(OK){
                result.add(auditEventConverter.convertToAuditEvent(list.get(i)));
            }
        }
        Page<AuditEvent> page = new PageImpl<AuditEvent>(result,pageable,result.size());

        return page;
    }

    public Optional<AuditEvent> find(Long id) {
        return Optional.ofNullable(persistenceAuditEventRepository.findOne(id)).map
            (auditEventConverter::convertToAuditEvent);
    }

    public void saveEvent(String event_type, String result){
        try{
            InetAddress localHost = InetAddress.getLocalHost();
            String address = localHost.getHostAddress();
            PersistentAuditEvent event = new PersistentAuditEvent();
            event.setAuditEventDate(LocalDateTime.now());
            event.setAuditEventType(event_type + "_" + result);
            event.setPrincipal(SecurityUtils.getCurrentUserLogin());
            Map<String,String> map = new HashMap<>();
            map.put("remoteAddress",address);
            event.setData(map);
            persistenceAuditEventRepository.save(event);
        }catch (Exception e){

        }
    }
}
