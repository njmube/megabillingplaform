package org.megapractical.billingplatform.security;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.service.AuditEventService;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.service.C_state_eventService;
import org.megapractical.billingplatform.service.TracemgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns a 401 error code (Unauthorized) to the client, when Ajax authentication fails.
 */
@Component
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);
    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {

        Long idauditevent = new Long("3");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);

        Long idstate = new Long("2");
        C_state_event c_state_event = c_state_eventService.findOne(idstate);

        tracemgService.saveTrace(audit_event_type,c_state_event);

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
    }
}
