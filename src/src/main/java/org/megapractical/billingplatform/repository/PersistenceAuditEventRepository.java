package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.PersistentAuditEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after);

    List<PersistentAuditEvent> findAllByAuditEventDateBetweenOrPrincipalStartingWithOrAuditEventTypeStartingWith(LocalDateTime fromDate,
                                                                                                                 LocalDateTime toDate,
                                                                                                                 String principal,
                                                                                                                 String auditEventType);

    List<PersistentAuditEvent> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
    List<PersistentAuditEvent> findAllByAuditEventDateBetweenAndAuditEventTypeStartingWith(LocalDateTime fromDate, LocalDateTime toDate, String auditEventType);

    List<PersistentAuditEvent> findAllByAuditEventDateBetweenAndPrincipalStartingWith(LocalDateTime fromDate, LocalDateTime toDate, String principal);

    List<PersistentAuditEvent> findAllByAuditEventDateBetweenAndPrincipalStartingWithAndAuditEventTypeStartingWith(LocalDateTime fromDate, LocalDateTime toDate, String principal, String auditEventType);


    Page<PersistentAuditEvent> findAllByAuditEventDateBetweenAndPrincipalAndAuditEventType(LocalDateTime fromDate,
                                                                                           LocalDateTime toDate,
                                                                                           String principal,
                                                                                           String auditEventType, Pageable pageable);

    Page<PersistentAuditEvent> findAllByAuditEventDateBetweenAndPrincipal(LocalDateTime fromDate,
                                                                                           LocalDateTime toDate,
                                                                                           String principal,
                                                                                           Pageable pageable);
    Page<PersistentAuditEvent> findAllByAuditEventDateBetweenAndAuditEventType(LocalDateTime fromDate,
                                                                                           LocalDateTime toDate,
                                                                                           String auditEventType, Pageable pageable);
    Page<PersistentAuditEvent> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
    }
