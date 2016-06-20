package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.config.audit.AuditEventConverter;
import org.megapractical.billingplatform.repository.PersistenceAuditEventRepository;
import java.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

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

    public Page<AuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate,String principal,String auditEventType, Pageable pageable) {
            if(principal == null && auditEventType == null)
            {
                return persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate, pageable)
                    .map(persistentAuditEvents -> auditEventConverter.convertToAuditEvent(persistentAuditEvents));
            }else if (principal == null){
                return persistenceAuditEventRepository.findAllByAuditEventDateBetweenAndAuditEventType(fromDate, toDate, auditEventType, pageable)
                    .map(persistentAuditEvents -> auditEventConverter.convertToAuditEvent(persistentAuditEvents));
            }else if (auditEventType == null)
            {
                return persistenceAuditEventRepository.findAllByAuditEventDateBetweenAndPrincipal(fromDate, toDate, principal, pageable)
                    .map(persistentAuditEvents -> auditEventConverter.convertToAuditEvent(persistentAuditEvents));
            }else {
                return persistenceAuditEventRepository.findAllByAuditEventDateBetweenAndPrincipalAndAuditEventType(fromDate, toDate, principal,auditEventType, pageable)
                    .map(persistentAuditEvents -> auditEventConverter.convertToAuditEvent(persistentAuditEvents));
            }
    }

    public Optional<AuditEvent> find(Long id) {
        return Optional.ofNullable(persistenceAuditEventRepository.findOne(id)).map
            (auditEventConverter::convertToAuditEvent);
    }
}
