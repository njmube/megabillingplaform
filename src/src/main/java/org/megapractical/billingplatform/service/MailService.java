package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.config.JHipsterProperties;
import org.megapractical.billingplatform.domain.*;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;



import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Properties;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * System default email address that sends the e-mails.
     */
    private String from;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        final String username = "cfdicontact@megapractical.com";
        final String password = "megacfdi01";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "smtp.uservers.net");
        props.put("mail.smtp.port", "587");

        // Prepare message using a Spring helper
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject(subject);
            // Send the actual HTML message, as big as you like
            message.setContent(content,"text/html");
            // Send message
            Transport.send(message);

            System.out.println("The message was successfully sent to the mail " + to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendActivationEmail(User user, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("activationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
        Long idauditevent = new Long("25");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTraceUser(user.getLogin(), audit_event_type, c_state_event);
    }

    @Async
    public void sendCreationEmail(User user, String baseUrl) {
        log.debug("Sending creation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("creationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
        Long idauditevent = new Long("23");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTraceUser(user.getLogin(), audit_event_type, c_state_event);
    }

    @Async
    public void sendPasswordResetMail(User user, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("passwordResetEmail", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
        Long idauditevent = new Long("24");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTraceUser(user.getLogin(), audit_event_type, c_state_event);
    }

    @Async
    public void sendRequestMail(User user, Request_taxpayer_account request_taxpayer_account) {
        log.debug("Sending request e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("request", request_taxpayer_account);
        String content = templateEngine.process("creationRequestEmail", context);
        String subject = messageSource.getMessage("email.request.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
        Long idauditevent = new Long("39");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTraceUser(user.getLogin(), audit_event_type, c_state_event);
    }

    @Async
    public void sendCreatedAccountMail(User user, Taxpayer_account taxpayer_account) {
        log.debug("Sending account creation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("account", taxpayer_account);
        String content = templateEngine.process("creationAccountEmail", context);
        String subject = messageSource.getMessage("email.account.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
        Long idauditevent = new Long("44");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTraceUser(user.getLogin(), audit_event_type, c_state_event);
    }

    @Async
    public void sendRejectAccountMail(User user, Request_taxpayer_account request_taxpayer_account) {
        log.debug("Sending account reject e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("reject", request_taxpayer_account);
        String content = templateEngine.process("rejectAccountEmail", context);
        String subject = messageSource.getMessage("email.reject.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
        Long idauditevent = new Long("43");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTraceUser(user.getLogin(), audit_event_type, c_state_event);
    }

    @Async
    public void sendNewFreeCFDICreatedToEmitterEmail(User user) {
        log.debug("Sending new free cfdi created emitter e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        String content = templateEngine.process("newfreecfditoemitterEmail", context);
        String subject = messageSource.getMessage("email.newfreecfditoemitter.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendNewFreeCFDICreatedToReceiverEmail(User user, Free_receiver receiver) {
        log.debug("Sending new free cfdi created to receiver e-mail to '{}'", receiver.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("receiver", receiver);
        String content = templateEngine.process("newfreecfditoreceiverEmail", context);
        String subject = messageSource.getMessage("email.newfreecfditoreceiver.title", null, locale);
        sendEmail(receiver.getEmail(), subject, content, false, true);
    }

}
