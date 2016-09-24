package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.Authority;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.repository.AuthorityRepository;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.security.AuthoritiesConstants;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.web.rest.dto.ManagedUserDTO;
import org.megapractical.billingplatform.web.rest.dto.UserDTO;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing users.
 *
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * </p>
 * <p>
 * We use a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>Another option would be to have a specific JPA entity graph to handle this case.</p>
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private MailService mailService;


    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserService userService;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     * </p>
     *
     * @param managedUserDTO the user to create
     * @param request the HTTP request
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntaxt is incorrect
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> createUser(@RequestBody ManagedUserDTO managedUserDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserDTO);
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        if (userRepository.findOneByLogin(managedUserDTO.getLogin()).isPresent()) {
            return new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST);
        } else if (userRepository.findOneByEmail(managedUserDTO.getEmail()).isPresent()) {
            return new ResponseEntity<>("e-mail address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST);
        } else if(userRepository.findOneByRfc(managedUserDTO.getRFC()).isPresent()){
            return new ResponseEntity<>("rfc already in use", textPlainHeaders, HttpStatus.BAD_REQUEST);
        }else{
            if(managedUserDTO.getCreator() == null)
                managedUserDTO.setCreator(SecurityUtils.getCurrentUserLogin());
            User newUser = userService.createUser(managedUserDTO);
            String baseUrl = request.getScheme() + // "http"
            "://" +                                // "://"
            request.getServerName() +              // "myhost"
            ":" +                                  // ":"
            request.getServerPort() +              // "80"
            request.getContextPath();              // "/myContextPath" or "" if deployed in root context
            //Audit user created
            Long idauditevent = new Long("26");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            if(newUser != null){
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);
            }
            else
            {
                Long idstate = new Long("2");
                c_state_event = c_state_eventService.findOne(idstate);
            }
            tracemgService.saveTrace(audit_event_type, c_state_event);

            mailService.sendCreationEmail(newUser, baseUrl);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param managedUserDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldnt be updated
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<ManagedUserDTO> updateUser(@RequestBody ManagedUserDTO managedUserDTO) {
        log.debug("REST request to update User : {}", managedUserDTO);
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        Optional<User> existingUser = userRepository.findOneByEmail(managedUserDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserDTO.getId()))) {
            Long idauditevent = new Long("27");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);

            tracemgService.saveTrace(audit_event_type, c_state_event);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userManagement", "emailexists", "E-mail already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(managedUserDTO.getLogin());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserDTO.getId()))) {
            Long idauditevent = new Long("27");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);

            tracemgService.saveTrace(audit_event_type, c_state_event);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userManagement", "userexists", "Login already in use")).body(null);
        }
        existingUser = userRepository.findOneByRfc(managedUserDTO.getRFC());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserDTO.getId()))) {
            Long idauditevent = new Long("27");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);

            tracemgService.saveTrace(audit_event_type, c_state_event);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userManagement", "rfcexists", "rfc already in use")).body(null);
        }
        return userRepository
            .findOneById(managedUserDTO.getId())
            .map(user -> {
                user.setLogin(managedUserDTO.getLogin());
                user.setRFC(managedUserDTO.getRFC());
                user.setName(managedUserDTO.getName());
                user.setEmail(managedUserDTO.getEmail());
                user.setPhone(managedUserDTO.getPhone());
                user.setGender(managedUserDTO.getGender());
                user.setActivated(managedUserDTO.isActivated());
                user.setLangKey(managedUserDTO.getLangKey());
                user.setCreator(managedUserDTO.getCreator());
                user.setFilephoto(managedUserDTO.getFilephoto());
                user.setFilephotoContentType(managedUserDTO.getFilephotoContentType());
                user.setPath_photo(managedUserDTO.getPath_photo());
                Set<Authority> authorities = user.getAuthorities();
                if (!managedUserDTO.isActivated()) {
                    userService.DeletePersistenTokenByUser(user);
                }
                authorities.clear();
                managedUserDTO.getAuthorities().stream().forEach(
                    authority -> authorities.add(authorityRepository.findOne(authority))
                );
                Long idauditevent = new Long("27");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);

                tracemgService.saveTrace(audit_event_type, c_state_event);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert("userManagement.updated", managedUserDTO.getLogin()))
                    .body(new ManagedUserDTO(userRepository
                        .findOne(managedUserDTO.getId())));
            })
            .orElseGet(() -> {
                Long idauditevent = new Long("27");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("2");
                c_state_event = c_state_eventService.findOne(idstate);

                tracemgService.saveTrace(audit_event_type, c_state_event);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);});
    }

    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     * @throws URISyntaxException if the pagination headers couldnt be generated
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"filterrfc", "datefrom","dateto","stateuser","role","filterlogin"})
    @Timed
    @Transactional(readOnly = false)
    public ResponseEntity<List<ManagedUserDTO>> getAllUsers(
        @RequestParam(value = "filterrfc") String filterrfc,
        @RequestParam(value = "datefrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datefrom,
        @RequestParam(value = "dateto") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateto,
        @RequestParam(value = "stateuser") Integer stateuser,
        @RequestParam(value = "role") String role,
        @RequestParam(value = "filterlogin") String filterlogin,
        Pageable pageable)
        throws URISyntaxException {
        Long idauditevent = new Long("21");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);

        tracemgService.saveTrace(audit_event_type, c_state_event);

        boolean activated = true;
        if(stateuser == 0)
            activated = false;
        if(filterlogin.compareTo(" ")!=0){
            User user = userRepository.findOneByLogin(filterlogin).get();
            Page<User> page = userRepository.findByLoginNotLikeAndLoginNotLikeAndRfcStartingWithAndActivated("system","anonymousUser",user.getRFC(),user.getActivated(),pageable);

            List<ManagedUserDTO> managedUserDTOs = page.getContent().stream()
                .map(ManagedUserDTO::new)
                .collect(Collectors.toList());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
            log.debug("Objetos DTOs : {}", managedUserDTOs);
            return new ResponseEntity<>(managedUserDTOs, headers, HttpStatus.OK);
        }

        if(filterrfc.compareTo(" ")==0 && datefrom.toString().compareTo("0001-01-01") == 0 &&
            dateto.toString().compareTo("0001-01-01") == 0 && stateuser == -1 && role.compareTo(" ")==0){
            Page<User> page = userRepository.findByLoginNotLikeAndLoginNotLike("system","anonymousUser",pageable);

            List<ManagedUserDTO> managedUserDTOs = page.getContent().stream()
                .map(ManagedUserDTO::new)
                .collect(Collectors.toList());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
            log.debug("Objetos DTOs : {}", managedUserDTOs);
            return new ResponseEntity<>(managedUserDTOs, headers, HttpStatus.OK);
        }else
        {
            Page<User> page;
            if(filterrfc.compareTo(" ")!=0){
                if(stateuser != -1){
                    page = userRepository.findByLoginNotLikeAndLoginNotLikeAndRfcStartingWithAndActivated("system","anonymousUser",filterrfc,activated,pageable);
                }else
                {
                    page = userRepository.findByLoginNotLikeAndLoginNotLikeAndRfcStartingWith("system","anonymousUser",filterrfc,pageable);
                }
            }else {
                if(stateuser != -1){
                    page = userRepository.findByLoginNotLikeAndLoginNotLikeAndActivated("system", "anonymousUser", activated, pageable);
                }else {
                    page = userRepository.findByLoginNotLikeAndLoginNotLike("system","anonymousUser",pageable);
                }
            }

            List<ManagedUserDTO> managedUserDTOs = page.getContent().stream()
                .map(ManagedUserDTO::new)
                .collect(Collectors.toList());

            List<ManagedUserDTO> other = new ArrayList<>();

            for (int i = 0; i < managedUserDTOs.size();i++){
                boolean a = true;
                boolean b = true;
                if(datefrom.toString().compareTo("0001-01-01") != 0 || dateto.toString().compareTo("0001-01-01") != 0){
                    LocalDate inicio = datefrom;
                    LocalDate datefinal;
                    if(datefrom.isBefore(dateto)){
                        datefinal = dateto;
                    }else {
                        datefinal = LocalDate.now();
                    }
                    LocalDate actual = managedUserDTOs.get(i).getCreatedDate().toLocalDate();
                    if(inicio.isAfter(actual)){
                        a = false;
                    }
                    if(datefinal.isBefore(actual)){
                        a = false;
                    }
                }
                if(!role.isEmpty() && role.compareTo(" ")!=0){
                    if(!managedUserDTOs.get(i).getAuthorities().contains(role)){
                        b = false;
                    }
                }
                if(a && b){
                    other.add(managedUserDTOs.get(i));
                }
            }
            Page<ManagedUserDTO> page1 = new PageImpl<ManagedUserDTO>(other,pageable, other.size());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, "/api/users");
            return new ResponseEntity<>(other, headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/users/{login:[_'.@a-z0-9-]+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManagedUserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        Long idauditevent = new Long("21");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);

        return userService.getUserWithAuthoritiesByLogin(login)
                .map(ManagedUserDTO::new)
                .map(managedUserDTO -> new ResponseEntity<>(managedUserDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*@RequestMapping(method = RequestMethod.GET,
        params = {"name", "firtsurname", "secondsurname"})
    public ResponseEntity<String> getSugesUser(
        @RequestParam(value = "name") String name,
        @RequestParam(value = "firtsurname") String firtsurname,
        @RequestParam(value = "secondsurname") String secondsurname
        ) throws URISyntaxException {
        String result = userService.sugestionUserLogin(name,firtsurname,secondsurname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/
    /**
     * DELETE  USER :login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/users/{login:[_'.@a-z0-9-]+}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        Optional<User> user1 = userRepository.findOneByLogin(login);
        if(user1.isPresent()){
            userService.DeletePersistenTokenByUser(user1.get());
        }
        userService.deleteUserInformation(login);
        Long idauditevent = new Long("28");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "userManagement.deleted", login)).build();
    }
}
