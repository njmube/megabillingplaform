package org.megapractical.billingplatform.security;

import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.repository.TracemgRepository;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.service.AuditEventService;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.service.C_state_eventService;
import org.megapractical.billingplatform.service.TracemgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    @Inject
    private TracemgRepository tracemgRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();
        SecurityUtils.intentLogin = login;
        List<Tracemg> list = tracemgRepository.findByPrincipalOrderByIdDesc(login);
        if(list != null){
           Long id = new Long("37");
           Long id1 = new Long("53");
            if(list.size() > 0) {
                if (list.get(0).getAudit_event_type().getId() == id || list.get(0).getAudit_event_type().getId() == id1) {

                } else {
                    if(list.get(0).getIp().compareTo(SecurityUtils.ipCliente)!=0)
                        throw new UserNotActivatedException("El usuario está ya autenticado");
                }
            }
        }
        Optional<User> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);

        return userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                Long idauditevent = new Long("3");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                Long idstate = new Long("2");
                C_state_event c_state_event = c_state_eventService.findOne(idstate);
                tracemgService.saveTraceUser(lowercaseLogin,audit_event_type,c_state_event);

                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");

            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
    }
}
