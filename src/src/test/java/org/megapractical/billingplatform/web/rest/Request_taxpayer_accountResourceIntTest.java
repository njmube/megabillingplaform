package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Request_taxpayer_account;
import org.megapractical.billingplatform.repository.Request_taxpayer_accountRepository;
import org.megapractical.billingplatform.service.Request_taxpayer_accountService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Request_taxpayer_accountResource REST controller.
 *
 * @see Request_taxpayer_accountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Request_taxpayer_accountResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATEREQUEST = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATEREQUEST = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATEREQUEST_STR = dateTimeFormatter.format(DEFAULT_DATEREQUEST);
    private static final String DEFAULT_FIRTSURNAME = "AAAAA";
    private static final String UPDATED_FIRTSURNAME = "BBBBB";
    private static final String DEFAULT_SECONDSURNAME = "AAAAA";
    private static final String UPDATED_SECONDSURNAME = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final LocalDate DEFAULT_DATEBORN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEBORN = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_GENDER = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_BUSSINESNAME = "AAAAA";
    private static final String UPDATED_BUSSINESNAME = "BBBBB";
    private static final String DEFAULT_ACCOUNTEMAIL = "AAAAA";
    private static final String UPDATED_ACCOUNTEMAIL = "BBBBB";

    @Inject
    private Request_taxpayer_accountRepository request_taxpayer_accountRepository;

    @Inject
    private Request_taxpayer_accountService request_taxpayer_accountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRequest_taxpayer_accountMockMvc;

    private Request_taxpayer_account request_taxpayer_account;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Request_taxpayer_accountResource request_taxpayer_accountResource = new Request_taxpayer_accountResource();
        ReflectionTestUtils.setField(request_taxpayer_accountResource, "request_taxpayer_accountService", request_taxpayer_accountService);
        this.restRequest_taxpayer_accountMockMvc = MockMvcBuilders.standaloneSetup(request_taxpayer_accountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        request_taxpayer_account = new Request_taxpayer_account();
        request_taxpayer_account.setName(DEFAULT_NAME);
        request_taxpayer_account.setDaterequest(DEFAULT_DATEREQUEST);
        request_taxpayer_account.setFirtsurname(DEFAULT_FIRTSURNAME);
        request_taxpayer_account.setSecondsurname(DEFAULT_SECONDSURNAME);
        request_taxpayer_account.setPhone(DEFAULT_PHONE);
        request_taxpayer_account.setEmail(DEFAULT_EMAIL);
        request_taxpayer_account.setDateborn(DEFAULT_DATEBORN);
        request_taxpayer_account.setGender(DEFAULT_GENDER);
        request_taxpayer_account.setRfc(DEFAULT_RFC);
        request_taxpayer_account.setBussinesname(DEFAULT_BUSSINESNAME);
        request_taxpayer_account.setAccountemail(DEFAULT_ACCOUNTEMAIL);
    }

    @Test
    @Transactional
    public void createRequest_taxpayer_account() throws Exception {
        int databaseSizeBeforeCreate = request_taxpayer_accountRepository.findAll().size();

        // Create the Request_taxpayer_account

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isCreated());

        // Validate the Request_taxpayer_account in the database
        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeCreate + 1);
        Request_taxpayer_account testRequest_taxpayer_account = request_taxpayer_accounts.get(request_taxpayer_accounts.size() - 1);
        assertThat(testRequest_taxpayer_account.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRequest_taxpayer_account.getDaterequest()).isEqualTo(DEFAULT_DATEREQUEST);
        assertThat(testRequest_taxpayer_account.getFirtsurname()).isEqualTo(DEFAULT_FIRTSURNAME);
        assertThat(testRequest_taxpayer_account.getSecondsurname()).isEqualTo(DEFAULT_SECONDSURNAME);
        assertThat(testRequest_taxpayer_account.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testRequest_taxpayer_account.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRequest_taxpayer_account.getDateborn()).isEqualTo(DEFAULT_DATEBORN);
        assertThat(testRequest_taxpayer_account.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testRequest_taxpayer_account.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testRequest_taxpayer_account.getBussinesname()).isEqualTo(DEFAULT_BUSSINESNAME);
        assertThat(testRequest_taxpayer_account.getAccountemail()).isEqualTo(DEFAULT_ACCOUNTEMAIL);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setName(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDaterequestIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setDaterequest(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirtsurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setFirtsurname(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecondsurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setSecondsurname(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setPhone(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setEmail(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatebornIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setDateborn(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setRfc(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBussinesnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setBussinesname(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountemailIsRequired() throws Exception {
        int databaseSizeBeforeTest = request_taxpayer_accountRepository.findAll().size();
        // set the field null
        request_taxpayer_account.setAccountemail(null);

        // Create the Request_taxpayer_account, which fails.

        restRequest_taxpayer_accountMockMvc.perform(post("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequest_taxpayer_accounts() throws Exception {
        // Initialize the database
        request_taxpayer_accountRepository.saveAndFlush(request_taxpayer_account);

        // Get all the request_taxpayer_accounts
        restRequest_taxpayer_accountMockMvc.perform(get("/api/request-taxpayer-accounts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(request_taxpayer_account.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].daterequest").value(hasItem(DEFAULT_DATEREQUEST_STR)))
                .andExpect(jsonPath("$.[*].firtsurname").value(hasItem(DEFAULT_FIRTSURNAME.toString())))
                .andExpect(jsonPath("$.[*].secondsurname").value(hasItem(DEFAULT_SECONDSURNAME.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].dateborn").value(hasItem(DEFAULT_DATEBORN.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].bussinesname").value(hasItem(DEFAULT_BUSSINESNAME.toString())))
                .andExpect(jsonPath("$.[*].accountemail").value(hasItem(DEFAULT_ACCOUNTEMAIL.toString())));
    }

    @Test
    @Transactional
    public void getRequest_taxpayer_account() throws Exception {
        // Initialize the database
        request_taxpayer_accountRepository.saveAndFlush(request_taxpayer_account);

        // Get the request_taxpayer_account
        restRequest_taxpayer_accountMockMvc.perform(get("/api/request-taxpayer-accounts/{id}", request_taxpayer_account.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(request_taxpayer_account.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.daterequest").value(DEFAULT_DATEREQUEST_STR))
            .andExpect(jsonPath("$.firtsurname").value(DEFAULT_FIRTSURNAME.toString()))
            .andExpect(jsonPath("$.secondsurname").value(DEFAULT_SECONDSURNAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.dateborn").value(DEFAULT_DATEBORN.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.bussinesname").value(DEFAULT_BUSSINESNAME.toString()))
            .andExpect(jsonPath("$.accountemail").value(DEFAULT_ACCOUNTEMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequest_taxpayer_account() throws Exception {
        // Get the request_taxpayer_account
        restRequest_taxpayer_accountMockMvc.perform(get("/api/request-taxpayer-accounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequest_taxpayer_account() throws Exception {
        // Initialize the database
        request_taxpayer_accountService.save(request_taxpayer_account);

        int databaseSizeBeforeUpdate = request_taxpayer_accountRepository.findAll().size();

        // Update the request_taxpayer_account
        Request_taxpayer_account updatedRequest_taxpayer_account = new Request_taxpayer_account();
        updatedRequest_taxpayer_account.setId(request_taxpayer_account.getId());
        updatedRequest_taxpayer_account.setName(UPDATED_NAME);
        updatedRequest_taxpayer_account.setDaterequest(UPDATED_DATEREQUEST);
        updatedRequest_taxpayer_account.setFirtsurname(UPDATED_FIRTSURNAME);
        updatedRequest_taxpayer_account.setSecondsurname(UPDATED_SECONDSURNAME);
        updatedRequest_taxpayer_account.setPhone(UPDATED_PHONE);
        updatedRequest_taxpayer_account.setEmail(UPDATED_EMAIL);
        updatedRequest_taxpayer_account.setDateborn(UPDATED_DATEBORN);
        updatedRequest_taxpayer_account.setGender(UPDATED_GENDER);
        updatedRequest_taxpayer_account.setRfc(UPDATED_RFC);
        updatedRequest_taxpayer_account.setBussinesname(UPDATED_BUSSINESNAME);
        updatedRequest_taxpayer_account.setAccountemail(UPDATED_ACCOUNTEMAIL);

        restRequest_taxpayer_accountMockMvc.perform(put("/api/request-taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRequest_taxpayer_account)))
                .andExpect(status().isOk());

        // Validate the Request_taxpayer_account in the database
        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeUpdate);
        Request_taxpayer_account testRequest_taxpayer_account = request_taxpayer_accounts.get(request_taxpayer_accounts.size() - 1);
        assertThat(testRequest_taxpayer_account.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequest_taxpayer_account.getDaterequest()).isEqualTo(UPDATED_DATEREQUEST);
        assertThat(testRequest_taxpayer_account.getFirtsurname()).isEqualTo(UPDATED_FIRTSURNAME);
        assertThat(testRequest_taxpayer_account.getSecondsurname()).isEqualTo(UPDATED_SECONDSURNAME);
        assertThat(testRequest_taxpayer_account.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testRequest_taxpayer_account.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRequest_taxpayer_account.getDateborn()).isEqualTo(UPDATED_DATEBORN);
        assertThat(testRequest_taxpayer_account.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testRequest_taxpayer_account.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testRequest_taxpayer_account.getBussinesname()).isEqualTo(UPDATED_BUSSINESNAME);
        assertThat(testRequest_taxpayer_account.getAccountemail()).isEqualTo(UPDATED_ACCOUNTEMAIL);
    }

    @Test
    @Transactional
    public void deleteRequest_taxpayer_account() throws Exception {
        // Initialize the database
        request_taxpayer_accountService.save(request_taxpayer_account);

        int databaseSizeBeforeDelete = request_taxpayer_accountRepository.findAll().size();

        // Get the request_taxpayer_account
        restRequest_taxpayer_accountMockMvc.perform(delete("/api/request-taxpayer-accounts/{id}", request_taxpayer_account.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Request_taxpayer_account> request_taxpayer_accounts = request_taxpayer_accountRepository.findAll();
        assertThat(request_taxpayer_accounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
