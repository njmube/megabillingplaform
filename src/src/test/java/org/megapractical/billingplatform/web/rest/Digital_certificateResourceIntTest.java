package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Digital_certificate;
import org.megapractical.billingplatform.repository.Digital_certificateRepository;
import org.megapractical.billingplatform.service.Digital_certificateService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Digital_certificateResource REST controller.
 *
 * @see Digital_certificateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Digital_certificateResourceIntTest {


    private static final byte[] DEFAULT_ADREES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ADREES = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ADREES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ADREES_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PRIVATE_KEY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRIVATE_KEY = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PRIVATE_KEY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRIVATE_KEY_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_POSSWORD = "AAAAA";
    private static final String UPDATED_POSSWORD = "BBBBB";

    @Inject
    private Digital_certificateRepository digital_certificateRepository;

    @Inject
    private Digital_certificateService digital_certificateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDigital_certificateMockMvc;

    private Digital_certificate digital_certificate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Digital_certificateResource digital_certificateResource = new Digital_certificateResource();
        ReflectionTestUtils.setField(digital_certificateResource, "digital_certificateService", digital_certificateService);
        this.restDigital_certificateMockMvc = MockMvcBuilders.standaloneSetup(digital_certificateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        digital_certificate = new Digital_certificate();
        digital_certificate.setAdrees(DEFAULT_ADREES);
        digital_certificate.setAdreesContentType(DEFAULT_ADREES_CONTENT_TYPE);
        digital_certificate.setPrivate_key(DEFAULT_PRIVATE_KEY);
        digital_certificate.setPrivate_keyContentType(DEFAULT_PRIVATE_KEY_CONTENT_TYPE);
        digital_certificate.setPossword(DEFAULT_POSSWORD);
    }

    @Test
    @Transactional
    public void createDigital_certificate() throws Exception {
        int databaseSizeBeforeCreate = digital_certificateRepository.findAll().size();

        // Create the Digital_certificate

        restDigital_certificateMockMvc.perform(post("/api/digital-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(digital_certificate)))
                .andExpect(status().isCreated());

        // Validate the Digital_certificate in the database
        List<Digital_certificate> digital_certificates = digital_certificateRepository.findAll();
        assertThat(digital_certificates).hasSize(databaseSizeBeforeCreate + 1);
        Digital_certificate testDigital_certificate = digital_certificates.get(digital_certificates.size() - 1);
        assertThat(testDigital_certificate.getAdrees()).isEqualTo(DEFAULT_ADREES);
        assertThat(testDigital_certificate.getAdreesContentType()).isEqualTo(DEFAULT_ADREES_CONTENT_TYPE);
        assertThat(testDigital_certificate.getPrivate_key()).isEqualTo(DEFAULT_PRIVATE_KEY);
        assertThat(testDigital_certificate.getPrivate_keyContentType()).isEqualTo(DEFAULT_PRIVATE_KEY_CONTENT_TYPE);
        assertThat(testDigital_certificate.getPossword()).isEqualTo(DEFAULT_POSSWORD);
    }

    @Test
    @Transactional
    public void getAllDigital_certificates() throws Exception {
        // Initialize the database
        digital_certificateRepository.saveAndFlush(digital_certificate);

        // Get all the digital_certificates
        restDigital_certificateMockMvc.perform(get("/api/digital-certificates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(digital_certificate.getId().intValue())))
                .andExpect(jsonPath("$.[*].adreesContentType").value(hasItem(DEFAULT_ADREES_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].adrees").value(hasItem(Base64Utils.encodeToString(DEFAULT_ADREES))))
                .andExpect(jsonPath("$.[*].private_keyContentType").value(hasItem(DEFAULT_PRIVATE_KEY_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].private_key").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRIVATE_KEY))))
                .andExpect(jsonPath("$.[*].possword").value(hasItem(DEFAULT_POSSWORD.toString())));
    }

    @Test
    @Transactional
    public void getDigital_certificate() throws Exception {
        // Initialize the database
        digital_certificateRepository.saveAndFlush(digital_certificate);

        // Get the digital_certificate
        restDigital_certificateMockMvc.perform(get("/api/digital-certificates/{id}", digital_certificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(digital_certificate.getId().intValue()))
            .andExpect(jsonPath("$.adreesContentType").value(DEFAULT_ADREES_CONTENT_TYPE))
            .andExpect(jsonPath("$.adrees").value(Base64Utils.encodeToString(DEFAULT_ADREES)))
            .andExpect(jsonPath("$.private_keyContentType").value(DEFAULT_PRIVATE_KEY_CONTENT_TYPE))
            .andExpect(jsonPath("$.private_key").value(Base64Utils.encodeToString(DEFAULT_PRIVATE_KEY)))
            .andExpect(jsonPath("$.possword").value(DEFAULT_POSSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDigital_certificate() throws Exception {
        // Get the digital_certificate
        restDigital_certificateMockMvc.perform(get("/api/digital-certificates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDigital_certificate() throws Exception {
        // Initialize the database
        digital_certificateService.save(digital_certificate);

        int databaseSizeBeforeUpdate = digital_certificateRepository.findAll().size();

        // Update the digital_certificate
        Digital_certificate updatedDigital_certificate = new Digital_certificate();
        updatedDigital_certificate.setId(digital_certificate.getId());
        updatedDigital_certificate.setAdrees(UPDATED_ADREES);
        updatedDigital_certificate.setAdreesContentType(UPDATED_ADREES_CONTENT_TYPE);
        updatedDigital_certificate.setPrivate_key(UPDATED_PRIVATE_KEY);
        updatedDigital_certificate.setPrivate_keyContentType(UPDATED_PRIVATE_KEY_CONTENT_TYPE);
        updatedDigital_certificate.setPossword(UPDATED_POSSWORD);

        restDigital_certificateMockMvc.perform(put("/api/digital-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDigital_certificate)))
                .andExpect(status().isOk());

        // Validate the Digital_certificate in the database
        List<Digital_certificate> digital_certificates = digital_certificateRepository.findAll();
        assertThat(digital_certificates).hasSize(databaseSizeBeforeUpdate);
        Digital_certificate testDigital_certificate = digital_certificates.get(digital_certificates.size() - 1);
        assertThat(testDigital_certificate.getAdrees()).isEqualTo(UPDATED_ADREES);
        assertThat(testDigital_certificate.getAdreesContentType()).isEqualTo(UPDATED_ADREES_CONTENT_TYPE);
        assertThat(testDigital_certificate.getPrivate_key()).isEqualTo(UPDATED_PRIVATE_KEY);
        assertThat(testDigital_certificate.getPrivate_keyContentType()).isEqualTo(UPDATED_PRIVATE_KEY_CONTENT_TYPE);
        assertThat(testDigital_certificate.getPossword()).isEqualTo(UPDATED_POSSWORD);
    }

    @Test
    @Transactional
    public void deleteDigital_certificate() throws Exception {
        // Initialize the database
        digital_certificateService.save(digital_certificate);

        int databaseSizeBeforeDelete = digital_certificateRepository.findAll().size();

        // Get the digital_certificate
        restDigital_certificateMockMvc.perform(delete("/api/digital-certificates/{id}", digital_certificate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Digital_certificate> digital_certificates = digital_certificateRepository.findAll();
        assertThat(digital_certificates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
