package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Mail_configuration;
import org.megapractical.billingplatform.repository.Mail_configurationRepository;
import org.megapractical.billingplatform.service.Mail_configurationService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Mail_configurationResource REST controller.
 *
 * @see Mail_configurationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Mail_configurationResourceIntTest {

    private static final String DEFAULT_HOST = "AAAAA";
    private static final String UPDATED_HOST = "BBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;
    private static final String DEFAULT_USER_MAIL = "AAAAA";
    private static final String UPDATED_USER_MAIL = "BBBBB";
    private static final String DEFAULT_PASSWORD_MAIL = "AAAAA";
    private static final String UPDATED_PASSWORD_MAIL = "BBBBB";
    private static final String DEFAULT_PROTOCOL = "AAAAA";
    private static final String UPDATED_PROTOCOL = "BBBBB";

    private static final Boolean DEFAULT_TLS = false;
    private static final Boolean UPDATED_TLS = true;

    private static final Boolean DEFAULT_SMTP_AUTH = false;
    private static final Boolean UPDATED_SMTP_AUTH = true;

    private static final Boolean DEFAULT_STARTTLS_ENABLE = false;
    private static final Boolean UPDATED_STARTTLS_ENABLE = true;
    private static final String DEFAULT_SSL_TRUST = "AAAAA";
    private static final String UPDATED_SSL_TRUST = "BBBBB";

    @Inject
    private Mail_configurationRepository mail_configurationRepository;

    @Inject
    private Mail_configurationService mail_configurationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMail_configurationMockMvc;

    private Mail_configuration mail_configuration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mail_configurationResource mail_configurationResource = new Mail_configurationResource();
        ReflectionTestUtils.setField(mail_configurationResource, "mail_configurationService", mail_configurationService);
        this.restMail_configurationMockMvc = MockMvcBuilders.standaloneSetup(mail_configurationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mail_configuration = new Mail_configuration();
        mail_configuration.setHost(DEFAULT_HOST);
        mail_configuration.setPort(DEFAULT_PORT);
        mail_configuration.setUser_mail(DEFAULT_USER_MAIL);
        mail_configuration.setPassword_mail(DEFAULT_PASSWORD_MAIL);
        mail_configuration.setProtocol(DEFAULT_PROTOCOL);
        mail_configuration.setTls(DEFAULT_TLS);
        mail_configuration.setSmtp_auth(DEFAULT_SMTP_AUTH);
        mail_configuration.setStarttls_enable(DEFAULT_STARTTLS_ENABLE);
        mail_configuration.setSsl_trust(DEFAULT_SSL_TRUST);
    }

    @Test
    @Transactional
    public void createMail_configuration() throws Exception {
        int databaseSizeBeforeCreate = mail_configurationRepository.findAll().size();

        // Create the Mail_configuration

        restMail_configurationMockMvc.perform(post("/api/mail-configurations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mail_configuration)))
                .andExpect(status().isCreated());

        // Validate the Mail_configuration in the database
        List<Mail_configuration> mail_configurations = mail_configurationRepository.findAll();
        assertThat(mail_configurations).hasSize(databaseSizeBeforeCreate + 1);
        Mail_configuration testMail_configuration = mail_configurations.get(mail_configurations.size() - 1);
        assertThat(testMail_configuration.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testMail_configuration.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testMail_configuration.getUser_mail()).isEqualTo(DEFAULT_USER_MAIL);
        assertThat(testMail_configuration.getPassword_mail()).isEqualTo(DEFAULT_PASSWORD_MAIL);
        assertThat(testMail_configuration.getProtocol()).isEqualTo(DEFAULT_PROTOCOL);
        assertThat(testMail_configuration.isTls()).isEqualTo(DEFAULT_TLS);
        assertThat(testMail_configuration.isSmtp_auth()).isEqualTo(DEFAULT_SMTP_AUTH);
        assertThat(testMail_configuration.isStarttls_enable()).isEqualTo(DEFAULT_STARTTLS_ENABLE);
        assertThat(testMail_configuration.getSsl_trust()).isEqualTo(DEFAULT_SSL_TRUST);
    }

    @Test
    @Transactional
    public void getAllMail_configurations() throws Exception {
        // Initialize the database
        mail_configurationRepository.saveAndFlush(mail_configuration);

        // Get all the mail_configurations
        restMail_configurationMockMvc.perform(get("/api/mail-configurations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mail_configuration.getId().intValue())))
                .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.toString())))
                .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
                .andExpect(jsonPath("$.[*].user_mail").value(hasItem(DEFAULT_USER_MAIL.toString())))
                .andExpect(jsonPath("$.[*].password_mail").value(hasItem(DEFAULT_PASSWORD_MAIL.toString())))
                .andExpect(jsonPath("$.[*].protocol").value(hasItem(DEFAULT_PROTOCOL.toString())))
                .andExpect(jsonPath("$.[*].tls").value(hasItem(DEFAULT_TLS.booleanValue())))
                .andExpect(jsonPath("$.[*].smtp_auth").value(hasItem(DEFAULT_SMTP_AUTH.booleanValue())))
                .andExpect(jsonPath("$.[*].starttls_enable").value(hasItem(DEFAULT_STARTTLS_ENABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].ssl_trust").value(hasItem(DEFAULT_SSL_TRUST.toString())));
    }

    @Test
    @Transactional
    public void getMail_configuration() throws Exception {
        // Initialize the database
        mail_configurationRepository.saveAndFlush(mail_configuration);

        // Get the mail_configuration
        restMail_configurationMockMvc.perform(get("/api/mail-configurations/{id}", mail_configuration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mail_configuration.getId().intValue()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.user_mail").value(DEFAULT_USER_MAIL.toString()))
            .andExpect(jsonPath("$.password_mail").value(DEFAULT_PASSWORD_MAIL.toString()))
            .andExpect(jsonPath("$.protocol").value(DEFAULT_PROTOCOL.toString()))
            .andExpect(jsonPath("$.tls").value(DEFAULT_TLS.booleanValue()))
            .andExpect(jsonPath("$.smtp_auth").value(DEFAULT_SMTP_AUTH.booleanValue()))
            .andExpect(jsonPath("$.starttls_enable").value(DEFAULT_STARTTLS_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.ssl_trust").value(DEFAULT_SSL_TRUST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMail_configuration() throws Exception {
        // Get the mail_configuration
        restMail_configurationMockMvc.perform(get("/api/mail-configurations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMail_configuration() throws Exception {
        // Initialize the database
        mail_configurationService.save(mail_configuration);

        int databaseSizeBeforeUpdate = mail_configurationRepository.findAll().size();

        // Update the mail_configuration
        Mail_configuration updatedMail_configuration = new Mail_configuration();
        updatedMail_configuration.setId(mail_configuration.getId());
        updatedMail_configuration.setHost(UPDATED_HOST);
        updatedMail_configuration.setPort(UPDATED_PORT);
        updatedMail_configuration.setUser_mail(UPDATED_USER_MAIL);
        updatedMail_configuration.setPassword_mail(UPDATED_PASSWORD_MAIL);
        updatedMail_configuration.setProtocol(UPDATED_PROTOCOL);
        updatedMail_configuration.setTls(UPDATED_TLS);
        updatedMail_configuration.setSmtp_auth(UPDATED_SMTP_AUTH);
        updatedMail_configuration.setStarttls_enable(UPDATED_STARTTLS_ENABLE);
        updatedMail_configuration.setSsl_trust(UPDATED_SSL_TRUST);

        restMail_configurationMockMvc.perform(put("/api/mail-configurations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMail_configuration)))
                .andExpect(status().isOk());

        // Validate the Mail_configuration in the database
        List<Mail_configuration> mail_configurations = mail_configurationRepository.findAll();
        assertThat(mail_configurations).hasSize(databaseSizeBeforeUpdate);
        Mail_configuration testMail_configuration = mail_configurations.get(mail_configurations.size() - 1);
        assertThat(testMail_configuration.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testMail_configuration.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testMail_configuration.getUser_mail()).isEqualTo(UPDATED_USER_MAIL);
        assertThat(testMail_configuration.getPassword_mail()).isEqualTo(UPDATED_PASSWORD_MAIL);
        assertThat(testMail_configuration.getProtocol()).isEqualTo(UPDATED_PROTOCOL);
        assertThat(testMail_configuration.isTls()).isEqualTo(UPDATED_TLS);
        assertThat(testMail_configuration.isSmtp_auth()).isEqualTo(UPDATED_SMTP_AUTH);
        assertThat(testMail_configuration.isStarttls_enable()).isEqualTo(UPDATED_STARTTLS_ENABLE);
        assertThat(testMail_configuration.getSsl_trust()).isEqualTo(UPDATED_SSL_TRUST);
    }

    @Test
    @Transactional
    public void deleteMail_configuration() throws Exception {
        // Initialize the database
        mail_configurationService.save(mail_configuration);

        int databaseSizeBeforeDelete = mail_configurationRepository.findAll().size();

        // Get the mail_configuration
        restMail_configurationMockMvc.perform(delete("/api/mail-configurations/{id}", mail_configuration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mail_configuration> mail_configurations = mail_configurationRepository.findAll();
        assertThat(mail_configurations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
