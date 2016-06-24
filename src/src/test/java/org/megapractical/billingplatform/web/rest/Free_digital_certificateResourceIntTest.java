package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_digital_certificate;
import org.megapractical.billingplatform.repository.Free_digital_certificateRepository;
import org.megapractical.billingplatform.service.Free_digital_certificateService;

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
 * Test class for the Free_digital_certificateResource REST controller.
 *
 * @see Free_digital_certificateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_digital_certificateResourceIntTest {


    private static final byte[] DEFAULT_ADREES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ADREES = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ADREES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ADREES_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PRIVATE_KEY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRIVATE_KEY = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PRIVATE_KEY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRIVATE_KEY_CONTENT_TYPE = "image/png";

    @Inject
    private Free_digital_certificateRepository free_digital_certificateRepository;

    @Inject
    private Free_digital_certificateService free_digital_certificateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_digital_certificateMockMvc;

    private Free_digital_certificate free_digital_certificate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_digital_certificateResource free_digital_certificateResource = new Free_digital_certificateResource();
        ReflectionTestUtils.setField(free_digital_certificateResource, "free_digital_certificateService", free_digital_certificateService);
        this.restFree_digital_certificateMockMvc = MockMvcBuilders.standaloneSetup(free_digital_certificateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_digital_certificate = new Free_digital_certificate();
        free_digital_certificate.setAdrees(DEFAULT_ADREES);
        free_digital_certificate.setAdreesContentType(DEFAULT_ADREES_CONTENT_TYPE);
        free_digital_certificate.setPrivate_key(DEFAULT_PRIVATE_KEY);
        free_digital_certificate.setPrivate_keyContentType(DEFAULT_PRIVATE_KEY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFree_digital_certificate() throws Exception {
        int databaseSizeBeforeCreate = free_digital_certificateRepository.findAll().size();

        // Create the Free_digital_certificate

        restFree_digital_certificateMockMvc.perform(post("/api/free-digital-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_digital_certificate)))
                .andExpect(status().isCreated());

        // Validate the Free_digital_certificate in the database
        List<Free_digital_certificate> free_digital_certificates = free_digital_certificateRepository.findAll();
        assertThat(free_digital_certificates).hasSize(databaseSizeBeforeCreate + 1);
        Free_digital_certificate testFree_digital_certificate = free_digital_certificates.get(free_digital_certificates.size() - 1);
        assertThat(testFree_digital_certificate.getAdrees()).isEqualTo(DEFAULT_ADREES);
        assertThat(testFree_digital_certificate.getAdreesContentType()).isEqualTo(DEFAULT_ADREES_CONTENT_TYPE);
        assertThat(testFree_digital_certificate.getPrivate_key()).isEqualTo(DEFAULT_PRIVATE_KEY);
        assertThat(testFree_digital_certificate.getPrivate_keyContentType()).isEqualTo(DEFAULT_PRIVATE_KEY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkAdreesIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_digital_certificateRepository.findAll().size();
        // set the field null
        free_digital_certificate.setAdrees(null);

        // Create the Free_digital_certificate, which fails.

        restFree_digital_certificateMockMvc.perform(post("/api/free-digital-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_digital_certificate)))
                .andExpect(status().isBadRequest());

        List<Free_digital_certificate> free_digital_certificates = free_digital_certificateRepository.findAll();
        assertThat(free_digital_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrivate_keyIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_digital_certificateRepository.findAll().size();
        // set the field null
        free_digital_certificate.setPrivate_key(null);

        // Create the Free_digital_certificate, which fails.

        restFree_digital_certificateMockMvc.perform(post("/api/free-digital-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_digital_certificate)))
                .andExpect(status().isBadRequest());

        List<Free_digital_certificate> free_digital_certificates = free_digital_certificateRepository.findAll();
        assertThat(free_digital_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_digital_certificates() throws Exception {
        // Initialize the database
        free_digital_certificateRepository.saveAndFlush(free_digital_certificate);

        // Get all the free_digital_certificates
        restFree_digital_certificateMockMvc.perform(get("/api/free-digital-certificates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_digital_certificate.getId().intValue())))
                .andExpect(jsonPath("$.[*].adreesContentType").value(hasItem(DEFAULT_ADREES_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].adrees").value(hasItem(Base64Utils.encodeToString(DEFAULT_ADREES))))
                .andExpect(jsonPath("$.[*].private_keyContentType").value(hasItem(DEFAULT_PRIVATE_KEY_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].private_key").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRIVATE_KEY))));
    }

    @Test
    @Transactional
    public void getFree_digital_certificate() throws Exception {
        // Initialize the database
        free_digital_certificateRepository.saveAndFlush(free_digital_certificate);

        // Get the free_digital_certificate
        restFree_digital_certificateMockMvc.perform(get("/api/free-digital-certificates/{id}", free_digital_certificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_digital_certificate.getId().intValue()))
            .andExpect(jsonPath("$.adreesContentType").value(DEFAULT_ADREES_CONTENT_TYPE))
            .andExpect(jsonPath("$.adrees").value(Base64Utils.encodeToString(DEFAULT_ADREES)))
            .andExpect(jsonPath("$.private_keyContentType").value(DEFAULT_PRIVATE_KEY_CONTENT_TYPE))
            .andExpect(jsonPath("$.private_key").value(Base64Utils.encodeToString(DEFAULT_PRIVATE_KEY)));
    }

    @Test
    @Transactional
    public void getNonExistingFree_digital_certificate() throws Exception {
        // Get the free_digital_certificate
        restFree_digital_certificateMockMvc.perform(get("/api/free-digital-certificates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_digital_certificate() throws Exception {
        // Initialize the database
        free_digital_certificateService.save(free_digital_certificate);

        int databaseSizeBeforeUpdate = free_digital_certificateRepository.findAll().size();

        // Update the free_digital_certificate
        Free_digital_certificate updatedFree_digital_certificate = new Free_digital_certificate();
        updatedFree_digital_certificate.setId(free_digital_certificate.getId());
        updatedFree_digital_certificate.setAdrees(UPDATED_ADREES);
        updatedFree_digital_certificate.setAdreesContentType(UPDATED_ADREES_CONTENT_TYPE);
        updatedFree_digital_certificate.setPrivate_key(UPDATED_PRIVATE_KEY);
        updatedFree_digital_certificate.setPrivate_keyContentType(UPDATED_PRIVATE_KEY_CONTENT_TYPE);

        restFree_digital_certificateMockMvc.perform(put("/api/free-digital-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_digital_certificate)))
                .andExpect(status().isOk());

        // Validate the Free_digital_certificate in the database
        List<Free_digital_certificate> free_digital_certificates = free_digital_certificateRepository.findAll();
        assertThat(free_digital_certificates).hasSize(databaseSizeBeforeUpdate);
        Free_digital_certificate testFree_digital_certificate = free_digital_certificates.get(free_digital_certificates.size() - 1);
        assertThat(testFree_digital_certificate.getAdrees()).isEqualTo(UPDATED_ADREES);
        assertThat(testFree_digital_certificate.getAdreesContentType()).isEqualTo(UPDATED_ADREES_CONTENT_TYPE);
        assertThat(testFree_digital_certificate.getPrivate_key()).isEqualTo(UPDATED_PRIVATE_KEY);
        assertThat(testFree_digital_certificate.getPrivate_keyContentType()).isEqualTo(UPDATED_PRIVATE_KEY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteFree_digital_certificate() throws Exception {
        // Initialize the database
        free_digital_certificateService.save(free_digital_certificate);

        int databaseSizeBeforeDelete = free_digital_certificateRepository.findAll().size();

        // Get the free_digital_certificate
        restFree_digital_certificateMockMvc.perform(delete("/api/free-digital-certificates/{id}", free_digital_certificate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_digital_certificate> free_digital_certificates = free_digital_certificateRepository.findAll();
        assertThat(free_digital_certificates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
