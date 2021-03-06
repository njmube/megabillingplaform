package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.repository.PersistentTokenRepository;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.web.rest.dto.KeyAndPasswordDTO;
import org.megapractical.billingplatform.web.rest.dto.UserDTO;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private MailService mailService;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * POST  /register : register the user.
     *
     * @param userDTO the user DTO
     * @param request the HTTP request
     * @return the ResponseEntity with status 201 (Created) if the user is registred or 400 (Bad Request) if the login or e-mail is already in use
     */
    @RequestMapping(value = "/register",
                    method = RequestMethod.POST,
                    produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity<?> registerAccount(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        return userRepository.findOneByRfc(userDTO.getRFC())
            .map(user -> new ResponseEntity<>("rfc already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByLogin(userDTO.getLogin())
            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
                    .map(user -> new ResponseEntity<>("e-mail address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                    .orElseGet(() -> {
                        User user = userService.createUserInformation(userDTO.getLogin(), userDTO.getRFC(),
                            userDTO.getPassword(), userDTO.getName(), userDTO.getFirtsurname(), userDTO.getSecondsurname(),
                            userDTO.getEmail().toLowerCase(),
                            userDTO.getPhone(), userDTO.getGender(), userDTO.getLangKey(), userDTO.getCreator(),userDTO.getFilephoto(),
                            userDTO.getFilephotoContentType(),userDTO.getPath_photo());
                        String baseUrl = request.getScheme() + // "http"
                            "://" +                                // "://"
                            request.getServerName() +              // "myhost"
                            ":" +                                  // ":"
                            request.getServerPort() +              // "80"
                            request.getContextPath();              // "/myContextPath" or "" if deployed in root context

                        Long idauditevent = new Long("20");
                        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                        C_state_event c_state_event;
                        Long idstate = new Long("1");
                        c_state_event = c_state_eventService.findOne(idstate);
                        tracemgService.saveTraceUser(user.getLogin(),audit_event_type, c_state_event);

                        mailService.sendActivationEmail(user, baseUrl);
                        return new ResponseEntity<>(HttpStatus.CREATED);
                    })
            ));
    }
    @RequestMapping(value = "/register",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"login"})
    @Timed
    public ResponseEntity<List<String>> sugesLogin(@RequestParam(value = "login") String login){
        Optional<User> user = userRepository.findOneByLogin(login);
        if(user.isPresent()){
            login = propousalUser(login);
        }
        log.debug("Login retornado: " + login);
        List<String> list = new ArrayList<>();
        list.add(login);
        ResponseEntity<List<String>> response = new ResponseEntity<List<String>>(list,HttpStatus.OK);
        log.debug("Response del metodo: {}",response);
        return response;
    }

    private String propousalUser(String login){
        String temp = login;
        boolean ban = true;
        int cont = 1;

        while (ban){
            if(cont<10){
                login = temp+ "0"+ cont;
            }
            else{
                login = temp + cont;
            }
            if(userRepository.findOneByLogin(login).isPresent()){
                cont++;
            }else {
                ban = false;
            }
        }
        return login;
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @RequestMapping(value = "/activate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {

        return userService.activateRegistration(key)
            .map(user -> {
                Long idauditevent = new Long("34");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);
                tracemgService.saveTraceUser(user.getLogin(),audit_event_type, c_state_event);
                return new ResponseEntity<String>(HttpStatus.OK);})
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @RequestMapping(value = "/authenticate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(user -> {
                Long idauditevent = new Long("35");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);
                tracemgService.saveTraceUser(user.getLogin(),audit_event_type, c_state_event);
                return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK); })
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> saveAccount(@RequestBody UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        //Optional<User> existingRCF = userRepository.findOneByRfc(userDTO.getRFC());

        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userDTO.getLogin()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }
        /*if (existingRCF.isPresent()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "rfcexists", "RFC already in use")).body(null);
        }*/
        return userRepository
            .findOneByLogin(SecurityUtils.getCurrentUserLogin())
            .map(u -> {
                userService.updateUserInformation(userDTO.getRFC(),userDTO.getName(),
                    userDTO.getFirtsurname(),userDTO.getSecondsurname(),userDTO.getEmail(),
                    userDTO.getPhone(),
                    userDTO.getGender(), userDTO.getLangKey(), userDTO.getCreator(),
                    userDTO.getFilephoto(),userDTO.getFilephotoContentType(),userDTO.getPath_photo());
                return new ResponseEntity<String>(HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account/change_password : changes the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @RequestMapping(value = "/account/change_password",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            Long idauditevent = new Long("29");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);
            tracemgService.saveTrace(audit_event_type, c_state_event);
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        if(userService.changePassword(password)) {
            Long idauditevent = new Long("29");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("1");
            c_state_event = c_state_eventService.findOne(idstate);
            tracemgService.saveTrace(audit_event_type, c_state_event);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            Long idauditevent = new Long("29");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);
            tracemgService.saveTrace(audit_event_type, c_state_event);
            return new ResponseEntity<>("Incorrect old password", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET  /account/sessions : get the current open sessions.
     *
     * @return the ResponseEntity with status 200 (OK) and the current open sessions in body,
     *  or status 500 (Internal Server Error) if the current open sessions couldn't be retrieved
     */
    @RequestMapping(value = "/account/sessions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PersistentToken>> getCurrentSessions() {
        Optional<User> user1 = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
        if(user1.isPresent()){
            Long idauditevent = new Long("30");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("1");
            c_state_event = c_state_eventService.findOne(idstate);
            tracemgService.saveTrace(audit_event_type, c_state_event);
            boolean administrator = false;
            for(Authority item: user1.get().getAuthorities()){
                if(item.getName().compareTo("ROLE_ADMIN")==0){
                    administrator = true;
                    //log.debug("Encontro el rol admin", administrator);
                }
            }
            if(administrator){
                return new ResponseEntity<>(
                        persistentTokenRepository.findAll(),
                        HttpStatus.OK);
            }
            else{
                return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin())
                    .map(user -> new ResponseEntity<>(
                        persistentTokenRepository.findByUser(user),
                        HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
            }
        }
        Long idauditevent = new Long("30");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("2");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * DELETE  /account/sessions?series={series} : invalidate an existing session.
     *
     * - You can only delete your own sessions, not any other user's session
     * - If you delete one of your existing sessions, and that you are currently logged in on that session, you will
     *   still be able to use that session, until you quit your browser: it does not work in real time (there is
     *   no API for that), it only removes the "remember me" cookie
     * - This is also true if you invalidate your current session: you will still be able to use it until you close
     *   your browser or that the session times out. But automatic login (the "remember me" cookie) will not work
     *   anymore.
     *   There is an API to invalidate the current session, but there is no API to check which session uses which
     *   cookie.
     *
     * @param series the series of an existing session
     * @throws UnsupportedEncodingException if the series couldnt be URL decoded
     */
    @RequestMapping(value = "/account/sessions/{series}",
        method = RequestMethod.DELETE)
    @Timed
    public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        Optional<User> user1 = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
        if(user1.isPresent()){
            Long idauditevent = new Long("31");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("1");
            c_state_event = c_state_eventService.findOne(idstate);
            tracemgService.saveTrace(audit_event_type, c_state_event);

            boolean administrator = false;
            for(Authority item: user1.get().getAuthorities()){
                if(item.getName().compareTo("ROLE_ADMIN")==0){
                    administrator = true;
                }
            }
            if(administrator){
                Optional<User> admin = userRepository.findOneByLogin("admin");
                PersistentToken token = persistentTokenRepository.findOne(decodedSeries);
                if(admin.isPresent()){
                    String mailtoadmin = "Reporte de sesión eliminada. Usuario: " + token.getUser().getLogin() +
                        ". Dirección IP: " + token.getIpAddress();
                    log.debug("Mensaje de reporte: " + mailtoadmin);
                    mailService.sendEmail(admin.get().getEmail(),"Reporte de sesión",mailtoadmin,false,false, null);
                }
                persistentTokenRepository.delete(decodedSeries);
            }
            else{
                userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
                    persistentTokenRepository.findByUser(u).stream()
                        .filter(persistentToken -> StringUtils.equals(persistentToken.getSeries(), decodedSeries))
                        .findAny().ifPresent(t -> {

                        Optional<User> admin = userRepository.findOneByLogin("admin");
                        if(admin.isPresent()){
                            String mailtoadmin = "Reporte de sesión eliminada. Usuario: " + u.getLogin() +
                                ". Dirección IP: " + t.getIpAddress();
                            log.debug("Mensaje de reporte: " + mailtoadmin);
                            mailService.sendEmail(admin.get().getEmail(),"Reporte de sesión",mailtoadmin,false,false, null);
                        }
                        persistentTokenRepository.delete(decodedSeries);});
                });
            }
        }
    }

    /**
     * POST   /account/reset_password/init : Send an e-mail to reset the password of the user
     *
     * @param mail the mail of the user
     * @param request the HTTP request
     * @return the ResponseEntity with status 200 (OK) if the e-mail was sent, or status 400 (Bad Request) if the e-mail address is not registred
     */
    @RequestMapping(value = "/account/reset_password/init",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail, HttpServletRequest request) {
        return userService.requestPasswordReset(mail)
            .map(user -> {
                String baseUrl = request.getScheme() +
                    "://" +
                    request.getServerName() +
                    ":" +
                    request.getServerPort() +
                    request.getContextPath();
                Long idauditevent = new Long("32");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);
                tracemgService.saveTrace(audit_event_type, c_state_event);

                mailService.sendPasswordResetMail(user, baseUrl);
                return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
            }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
    }

    /**
     * POST   /account/reset_password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the password could not be reset
     */
    @RequestMapping(value = "/account/reset_password/finish",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordDTO keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            Long idauditevent = new Long("33");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);
            tracemgService.saveTrace(audit_event_type, c_state_event);

            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        Long idauditevent = new Long("33");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);

        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
              .map(user -> new ResponseEntity<String>(HttpStatus.OK))
              .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
            password.length() >= UserDTO.PASSWORD_MIN_LENGTH &&
            password.length() <= UserDTO.PASSWORD_MAX_LENGTH);
    }
}
