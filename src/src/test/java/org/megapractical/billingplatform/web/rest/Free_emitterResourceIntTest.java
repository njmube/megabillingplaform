package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.repository.Free_emitterRepository;
import org.megapractical.billingplatform.service.Free_emitterService;

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
 * Test class for the Free_emitterResource REST controller.
 *
 * @see Free_emitterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_emitterResourceIntTest {

    private static final String DEFAULT_RFC = "AAAAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBBBB";
    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_EMAIL = "AAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBB";

    private static final byte[] DEFAULT_PATH_CERTIFICATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PATH_CERTIFICATE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PATH_CERTIFICATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PATH_CERTIFICATE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PRIVATE_KEY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRIVATE_KEY = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PRIVATE_KEY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRIVATE_KEY_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";
    private static final String DEFAULT_NUM_IN = "AAAAA";
    private static final String UPDATED_NUM_IN = "BBBBB";
    private static final String DEFAULT_NUM_OUT = "AAAAA";
    private static final String UPDATED_NUM_OUT = "BBBBB";
    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";

    @Inject
    private Free_emitterRepository free_emitterRepository;

    @Inject
    private Free_emitterService free_emitterService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_emitterMockMvc;

    private Free_emitter free_emitter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_emitterResource free_emitterResource = new Free_emitterResource();
        ReflectionTestUtils.setField(free_emitterResource, "free_emitterService", free_emitterService);
        this.restFree_emitterMockMvc = MockMvcBuilders.standaloneSetup(free_emitterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_emitter = new Free_emitter();
        free_emitter.setRfc(DEFAULT_RFC);
        free_emitter.setName(DEFAULT_NAME);
        free_emitter.setEmail(DEFAULT_EMAIL);
        free_emitter.setPath_certificate(DEFAULT_PATH_CERTIFICATE);
        free_emitter.setPath_certificateContentType(DEFAULT_PATH_CERTIFICATE_CONTENT_TYPE);
        free_emitter.setPrivate_key(DEFAULT_PRIVATE_KEY);
        free_emitter.setPrivate_keyContentType(DEFAULT_PRIVATE_KEY_CONTENT_TYPE);
        free_emitter.setReference(DEFAULT_REFERENCE);
        free_emitter.setNum_in(DEFAULT_NUM_IN);
        free_emitter.setNum_out(DEFAULT_NUM_OUT);
        free_emitter.setStreet(DEFAULT_STREET);
    }

    @Test
    @Transactional
    public void createFree_emitter() throws Exception {
        int databaseSizeBeforeCreate = free_emitterRepository.findAll().size();

        // Create the Free_emitter

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isCreated());

        // Validate the Free_emitter in the database
        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeCreate + 1);
        Free_emitter testFree_emitter = free_emitters.get(free_emitters.size() - 1);
        assertThat(testFree_emitter.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFree_emitter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFree_emitter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFree_emitter.getPath_certificate()).isEqualTo(DEFAULT_PATH_CERTIFICATE);
        assertThat(testFree_emitter.getPath_certificateContentType()).isEqualTo(DEFAULT_PATH_CERTIFICATE_CONTENT_TYPE);
        assertThat(testFree_emitter.getPrivate_key()).isEqualTo(DEFAULT_PRIVATE_KEY);
        assertThat(testFree_emitter.getPrivate_keyContentType()).isEqualTo(DEFAULT_PRIVATE_KEY_CONTENT_TYPE);
        assertThat(testFree_emitter.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testFree_emitter.getNum_in()).isEqualTo(DEFAULT_NUM_IN);
        assertThat(testFree_emitter.getNum_out()).isEqualTo(DEFAULT_NUM_OUT);
        assertThat(testFree_emitter.getStreet()).isEqualTo(DEFAULT_STREET);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setRfc(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setName(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setEmail(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPath_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setPath_certificate(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrivate_keyIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setPrivate_key(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setStreet(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_emitters() throws Exception {
        // Initialize the database
        free_emitterRepository.saveAndFlush(free_emitter);

        // Get all the free_emitters
        restFree_emitterMockMvc.perform(get("/api/free-emitters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_emitter.getId().intValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].path_certificateContentType").value(hasItem(DEFAULT_PATH_CERTIFICATE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].path_certificate").value(hasItem(Base64Utils.encodeToString(DEFAULT_PATH_CERTIFICATE))))
                .andExpect(jsonPath("$.[*].private_keyContentType").value(hasItem(DEFAULT_PRIVATE_KEY_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].private_key").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRIVATE_KEY))))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].num_in").value(hasItem(DEFAULT_NUM_IN.toString())))
                .andExpect(jsonPath("$.[*].num_out").value(hasItem(DEFAULT_NUM_OUT.toString())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())));
    }

    @Test
    @Transactional
    public void getFree_emitter() throws Exception {
        // Initialize the database
        free_emitterRepository.saveAndFlush(free_emitter);

        // Get the free_emitter
        restFree_emitterMockMvc.perform(get("/api/free-emitters/{id}", free_emitter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_emitter.getId().intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.path_certificateContentType").value(DEFAULT_PATH_CERTIFICATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.path_certificate").value(Base64Utils.encodeToString(DEFAULT_PATH_CERTIFICATE)))
            .andExpect(jsonPath("$.private_keyContentType").value(DEFAULT_PRIVATE_KEY_CONTENT_TYPE))
            .andExpect(jsonPath("$.private_key").value(Base64Utils.encodeToString(DEFAULT_PRIVATE_KEY)))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.num_in").value(DEFAULT_NUM_IN.toString()))
            .andExpect(jsonPath("$.num_out").value(DEFAULT_NUM_OUT.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFree_emitter() throws Exception {
        // Get the free_emitter
        restFree_emitterMockMvc.perform(get("/api/free-emitters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_emitter() throws Exception {
        // Initialize the database
        free_emitterService.save(free_emitter);

        int databaseSizeBeforeUpdate = free_emitterRepository.findAll().size();

        // Update the free_emitter
        Free_emitter updatedFree_emitter = new Free_emitter();
        updatedFree_emitter.setId(free_emitter.getId());
        updatedFree_emitter.setRfc(UPDATED_RFC);
        updatedFree_emitter.setName(UPDATED_NAME);
        updatedFree_emitter.setEmail(UPDATED_EMAIL);
        updatedFree_emitter.setPath_certificate(UPDATED_PATH_CERTIFICATE);
        updatedFree_emitter.setPath_certificateContentType(UPDATED_PATH_CERTIFICATE_CONTENT_TYPE);
        updatedFree_emitter.setPrivate_key(UPDATED_PRIVATE_KEY);
        updatedFree_emitter.setPrivate_keyContentType(UPDATED_PRIVATE_KEY_CONTENT_TYPE);
        updatedFree_emitter.setReference(UPDATED_REFERENCE);
        updatedFree_emitter.setNum_in(UPDATED_NUM_IN);
        updatedFree_emitter.setNum_out(UPDATED_NUM_OUT);
        updatedFree_emitter.setStreet(UPDATED_STREET);

        restFree_emitterMockMvc.perform(put("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_emitter)))
                .andExpect(status().isOk());

        // Validate the Free_emitter in the database
        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeUpdate);
        Free_emitter testFree_emitter = free_emitters.get(free_emitters.size() - 1);
        assertThat(testFree_emitter.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFree_emitter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFree_emitter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFree_emitter.getPath_certificate()).isEqualTo(UPDATED_PATH_CERTIFICATE);
        assertThat(testFree_emitter.getPath_certificateContentType()).isEqualTo(UPDATED_PATH_CERTIFICATE_CONTENT_TYPE);
        assertThat(testFree_emitter.getPrivate_key()).isEqualTo(UPDATED_PRIVATE_KEY);
        assertThat(testFree_emitter.getPrivate_keyContentType()).isEqualTo(UPDATED_PRIVATE_KEY_CONTENT_TYPE);
        assertThat(testFree_emitter.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testFree_emitter.getNum_in()).isEqualTo(UPDATED_NUM_IN);
        assertThat(testFree_emitter.getNum_out()).isEqualTo(UPDATED_NUM_OUT);
        assertThat(testFree_emitter.getStreet()).isEqualTo(UPDATED_STREET);
    }

    @Test
    @Transactional
    public void deleteFree_emitter() throws Exception {
        // Initialize the database
        free_emitterService.save(free_emitter);

        int databaseSizeBeforeDelete = free_emitterRepository.findAll().size();

        // Get the free_emitter
        restFree_emitterMockMvc.perform(delete("/api/free-emitters/{id}", free_emitter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
