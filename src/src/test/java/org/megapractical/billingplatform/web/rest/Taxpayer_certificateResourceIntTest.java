package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Taxpayer_certificate;
import org.megapractical.billingplatform.repository.Taxpayer_certificateRepository;
import org.megapractical.billingplatform.service.Taxpayer_certificateService;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Taxpayer_certificateResource REST controller.
 *
 * @see Taxpayer_certificateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Taxpayer_certificateResourceIntTest {

    private static final String DEFAULT_PATH_CERTIFICATE = "AAAAA";
    private static final String UPDATED_PATH_CERTIFICATE = "BBBBB";

    private static final byte[] DEFAULT_FILECERTIFICATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILECERTIFICATE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILECERTIFICATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILECERTIFICATE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_PATH_KEY = "AAAAA";
    private static final String UPDATED_PATH_KEY = "BBBBB";

    private static final byte[] DEFAULT_FILEKEY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILEKEY = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILEKEY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILEKEY_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_NUMBER_CERTIFICATE = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMBER_CERTIFICATE = "BBBBBBBBBBBBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CERTIFICATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CERTIFICATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_RFC_CERTIFICATE = "AAAAA";
    private static final String UPDATED_RFC_CERTIFICATE = "BBBBB";
    private static final String DEFAULT_BUSSINES_NAME_CERT = "AAAAA";
    private static final String UPDATED_BUSSINES_NAME_CERT = "BBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED_CERT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED_CERT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EXPIRATION_CERT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPIRATION_CERT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_INFO_CERTIFICATE = "AAAAA";
    private static final String UPDATED_INFO_CERTIFICATE = "BBBBB";

    @Inject
    private Taxpayer_certificateRepository taxpayer_certificateRepository;

    @Inject
    private Taxpayer_certificateService taxpayer_certificateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxpayer_certificateMockMvc;

    private Taxpayer_certificate taxpayer_certificate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Taxpayer_certificateResource taxpayer_certificateResource = new Taxpayer_certificateResource();
        ReflectionTestUtils.setField(taxpayer_certificateResource, "taxpayer_certificateService", taxpayer_certificateService);
        this.restTaxpayer_certificateMockMvc = MockMvcBuilders.standaloneSetup(taxpayer_certificateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taxpayer_certificate = new Taxpayer_certificate();
        taxpayer_certificate.setPath_certificate(DEFAULT_PATH_CERTIFICATE);
        taxpayer_certificate.setFilecertificate(DEFAULT_FILECERTIFICATE);
        taxpayer_certificate.setFilecertificateContentType(DEFAULT_FILECERTIFICATE_CONTENT_TYPE);
        taxpayer_certificate.setPath_key(DEFAULT_PATH_KEY);
        taxpayer_certificate.setFilekey(DEFAULT_FILEKEY);
        taxpayer_certificate.setFilekeyContentType(DEFAULT_FILEKEY_CONTENT_TYPE);
        taxpayer_certificate.setNumber_certificate(DEFAULT_NUMBER_CERTIFICATE);
        taxpayer_certificate.setDate_certificate(DEFAULT_DATE_CERTIFICATE);
        taxpayer_certificate.setRfc_certificate(DEFAULT_RFC_CERTIFICATE);
        taxpayer_certificate.setBussines_name_cert(DEFAULT_BUSSINES_NAME_CERT);
        taxpayer_certificate.setDate_created_cert(DEFAULT_DATE_CREATED_CERT);
        taxpayer_certificate.setDate_expiration_cert(DEFAULT_DATE_EXPIRATION_CERT);
        taxpayer_certificate.setInfo_certificate(DEFAULT_INFO_CERTIFICATE);
    }

    @Test
    @Transactional
    public void createTaxpayer_certificate() throws Exception {
        int databaseSizeBeforeCreate = taxpayer_certificateRepository.findAll().size();

        // Create the Taxpayer_certificate

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isCreated());

        // Validate the Taxpayer_certificate in the database
        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeCreate + 1);
        Taxpayer_certificate testTaxpayer_certificate = taxpayer_certificates.get(taxpayer_certificates.size() - 1);
        assertThat(testTaxpayer_certificate.getPath_certificate()).isEqualTo(DEFAULT_PATH_CERTIFICATE);
        assertThat(testTaxpayer_certificate.getFilecertificate()).isEqualTo(DEFAULT_FILECERTIFICATE);
        assertThat(testTaxpayer_certificate.getFilecertificateContentType()).isEqualTo(DEFAULT_FILECERTIFICATE_CONTENT_TYPE);
        assertThat(testTaxpayer_certificate.getPath_key()).isEqualTo(DEFAULT_PATH_KEY);
        assertThat(testTaxpayer_certificate.getFilekey()).isEqualTo(DEFAULT_FILEKEY);
        assertThat(testTaxpayer_certificate.getFilekeyContentType()).isEqualTo(DEFAULT_FILEKEY_CONTENT_TYPE);
        assertThat(testTaxpayer_certificate.getNumber_certificate()).isEqualTo(DEFAULT_NUMBER_CERTIFICATE);
        assertThat(testTaxpayer_certificate.getDate_certificate()).isEqualTo(DEFAULT_DATE_CERTIFICATE);
        assertThat(testTaxpayer_certificate.getRfc_certificate()).isEqualTo(DEFAULT_RFC_CERTIFICATE);
        assertThat(testTaxpayer_certificate.getBussines_name_cert()).isEqualTo(DEFAULT_BUSSINES_NAME_CERT);
        assertThat(testTaxpayer_certificate.getDate_created_cert()).isEqualTo(DEFAULT_DATE_CREATED_CERT);
        assertThat(testTaxpayer_certificate.getDate_expiration_cert()).isEqualTo(DEFAULT_DATE_EXPIRATION_CERT);
        assertThat(testTaxpayer_certificate.getInfo_certificate()).isEqualTo(DEFAULT_INFO_CERTIFICATE);
    }

    @Test
    @Transactional
    public void checkPath_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setPath_certificate(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilecertificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setFilecertificate(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPath_keyIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setPath_key(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilekeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setFilekey(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumber_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setNumber_certificate(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setDate_certificate(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfc_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setRfc_certificate(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBussines_name_certIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setBussines_name_cert(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_created_certIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setDate_created_cert(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInfo_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_certificateRepository.findAll().size();
        // set the field null
        taxpayer_certificate.setInfo_certificate(null);

        // Create the Taxpayer_certificate, which fails.

        restTaxpayer_certificateMockMvc.perform(post("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_certificate)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxpayer_certificates() throws Exception {
        // Initialize the database
        taxpayer_certificateRepository.saveAndFlush(taxpayer_certificate);

        // Get all the taxpayer_certificates
        restTaxpayer_certificateMockMvc.perform(get("/api/taxpayer-certificates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxpayer_certificate.getId().intValue())))
                .andExpect(jsonPath("$.[*].path_certificate").value(hasItem(DEFAULT_PATH_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].filecertificateContentType").value(hasItem(DEFAULT_FILECERTIFICATE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].filecertificate").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILECERTIFICATE))))
                .andExpect(jsonPath("$.[*].path_key").value(hasItem(DEFAULT_PATH_KEY.toString())))
                .andExpect(jsonPath("$.[*].filekeyContentType").value(hasItem(DEFAULT_FILEKEY_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].filekey").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILEKEY))))
                .andExpect(jsonPath("$.[*].number_certificate").value(hasItem(DEFAULT_NUMBER_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].date_certificate").value(hasItem(DEFAULT_DATE_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].rfc_certificate").value(hasItem(DEFAULT_RFC_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].bussines_name_cert").value(hasItem(DEFAULT_BUSSINES_NAME_CERT.toString())))
                .andExpect(jsonPath("$.[*].date_created_cert").value(hasItem(DEFAULT_DATE_CREATED_CERT.toString())))
                .andExpect(jsonPath("$.[*].date_expiration_cert").value(hasItem(DEFAULT_DATE_EXPIRATION_CERT.toString())))
                .andExpect(jsonPath("$.[*].info_certificate").value(hasItem(DEFAULT_INFO_CERTIFICATE.toString())));
    }

    @Test
    @Transactional
    public void getTaxpayer_certificate() throws Exception {
        // Initialize the database
        taxpayer_certificateRepository.saveAndFlush(taxpayer_certificate);

        // Get the taxpayer_certificate
        restTaxpayer_certificateMockMvc.perform(get("/api/taxpayer-certificates/{id}", taxpayer_certificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxpayer_certificate.getId().intValue()))
            .andExpect(jsonPath("$.path_certificate").value(DEFAULT_PATH_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.filecertificateContentType").value(DEFAULT_FILECERTIFICATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.filecertificate").value(Base64Utils.encodeToString(DEFAULT_FILECERTIFICATE)))
            .andExpect(jsonPath("$.path_key").value(DEFAULT_PATH_KEY.toString()))
            .andExpect(jsonPath("$.filekeyContentType").value(DEFAULT_FILEKEY_CONTENT_TYPE))
            .andExpect(jsonPath("$.filekey").value(Base64Utils.encodeToString(DEFAULT_FILEKEY)))
            .andExpect(jsonPath("$.number_certificate").value(DEFAULT_NUMBER_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.date_certificate").value(DEFAULT_DATE_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.rfc_certificate").value(DEFAULT_RFC_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.bussines_name_cert").value(DEFAULT_BUSSINES_NAME_CERT.toString()))
            .andExpect(jsonPath("$.date_created_cert").value(DEFAULT_DATE_CREATED_CERT.toString()))
            .andExpect(jsonPath("$.date_expiration_cert").value(DEFAULT_DATE_EXPIRATION_CERT.toString()))
            .andExpect(jsonPath("$.info_certificate").value(DEFAULT_INFO_CERTIFICATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxpayer_certificate() throws Exception {
        // Get the taxpayer_certificate
        restTaxpayer_certificateMockMvc.perform(get("/api/taxpayer-certificates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxpayer_certificate() throws Exception {
        // Initialize the database
        taxpayer_certificateService.save(taxpayer_certificate);

        int databaseSizeBeforeUpdate = taxpayer_certificateRepository.findAll().size();

        // Update the taxpayer_certificate
        Taxpayer_certificate updatedTaxpayer_certificate = new Taxpayer_certificate();
        updatedTaxpayer_certificate.setId(taxpayer_certificate.getId());
        updatedTaxpayer_certificate.setPath_certificate(UPDATED_PATH_CERTIFICATE);
        updatedTaxpayer_certificate.setFilecertificate(UPDATED_FILECERTIFICATE);
        updatedTaxpayer_certificate.setFilecertificateContentType(UPDATED_FILECERTIFICATE_CONTENT_TYPE);
        updatedTaxpayer_certificate.setPath_key(UPDATED_PATH_KEY);
        updatedTaxpayer_certificate.setFilekey(UPDATED_FILEKEY);
        updatedTaxpayer_certificate.setFilekeyContentType(UPDATED_FILEKEY_CONTENT_TYPE);
        updatedTaxpayer_certificate.setNumber_certificate(UPDATED_NUMBER_CERTIFICATE);
        updatedTaxpayer_certificate.setDate_certificate(UPDATED_DATE_CERTIFICATE);
        updatedTaxpayer_certificate.setRfc_certificate(UPDATED_RFC_CERTIFICATE);
        updatedTaxpayer_certificate.setBussines_name_cert(UPDATED_BUSSINES_NAME_CERT);
        updatedTaxpayer_certificate.setDate_created_cert(UPDATED_DATE_CREATED_CERT);
        updatedTaxpayer_certificate.setDate_expiration_cert(UPDATED_DATE_EXPIRATION_CERT);
        updatedTaxpayer_certificate.setInfo_certificate(UPDATED_INFO_CERTIFICATE);

        restTaxpayer_certificateMockMvc.perform(put("/api/taxpayer-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTaxpayer_certificate)))
                .andExpect(status().isOk());

        // Validate the Taxpayer_certificate in the database
        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeUpdate);
        Taxpayer_certificate testTaxpayer_certificate = taxpayer_certificates.get(taxpayer_certificates.size() - 1);
        assertThat(testTaxpayer_certificate.getPath_certificate()).isEqualTo(UPDATED_PATH_CERTIFICATE);
        assertThat(testTaxpayer_certificate.getFilecertificate()).isEqualTo(UPDATED_FILECERTIFICATE);
        assertThat(testTaxpayer_certificate.getFilecertificateContentType()).isEqualTo(UPDATED_FILECERTIFICATE_CONTENT_TYPE);
        assertThat(testTaxpayer_certificate.getPath_key()).isEqualTo(UPDATED_PATH_KEY);
        assertThat(testTaxpayer_certificate.getFilekey()).isEqualTo(UPDATED_FILEKEY);
        assertThat(testTaxpayer_certificate.getFilekeyContentType()).isEqualTo(UPDATED_FILEKEY_CONTENT_TYPE);
        assertThat(testTaxpayer_certificate.getNumber_certificate()).isEqualTo(UPDATED_NUMBER_CERTIFICATE);
        assertThat(testTaxpayer_certificate.getDate_certificate()).isEqualTo(UPDATED_DATE_CERTIFICATE);
        assertThat(testTaxpayer_certificate.getRfc_certificate()).isEqualTo(UPDATED_RFC_CERTIFICATE);
        assertThat(testTaxpayer_certificate.getBussines_name_cert()).isEqualTo(UPDATED_BUSSINES_NAME_CERT);
        assertThat(testTaxpayer_certificate.getDate_created_cert()).isEqualTo(UPDATED_DATE_CREATED_CERT);
        assertThat(testTaxpayer_certificate.getDate_expiration_cert()).isEqualTo(UPDATED_DATE_EXPIRATION_CERT);
        assertThat(testTaxpayer_certificate.getInfo_certificate()).isEqualTo(UPDATED_INFO_CERTIFICATE);
    }

    @Test
    @Transactional
    public void deleteTaxpayer_certificate() throws Exception {
        // Initialize the database
        taxpayer_certificateService.save(taxpayer_certificate);

        int databaseSizeBeforeDelete = taxpayer_certificateRepository.findAll().size();

        // Get the taxpayer_certificate
        restTaxpayer_certificateMockMvc.perform(delete("/api/taxpayer-certificates/{id}", taxpayer_certificate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxpayer_certificate> taxpayer_certificates = taxpayer_certificateRepository.findAll();
        assertThat(taxpayer_certificates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
