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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
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

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";
    private static final String DEFAULT_NUM_INT = "1";
    private static final String UPDATED_NUM_INT = "2";
    private static final String DEFAULT_NUM_EXT = "3";
    private static final String UPDATED_NUM_EXT = "4";
    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;
    private static final String DEFAULT_RFC = "AAA121234ZWW";
    private static final String UPDATED_RFC = "AAA121234ZSS";
    private static final String DEFAULT_SOCIAL_REASON = "AAA";
    private static final String UPDATED_SOCIAL_REASON = "BBB";
    private static final String DEFAULT_EMAIL = "algo@algo.com";
    private static final String UPDATED_EMAIL = "algo1@algo.com";

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
        free_emitter.setReference(DEFAULT_REFERENCE);
        free_emitter.setNum_int(DEFAULT_NUM_INT);
        free_emitter.setNum_ext(DEFAULT_NUM_EXT);
        free_emitter.setStreet(DEFAULT_STREET);
        free_emitter.setCreate_date(DEFAULT_CREATE_DATE);
        free_emitter.setActivated(DEFAULT_ACTIVATED);
        free_emitter.setRfc(DEFAULT_RFC);
        free_emitter.setSocial_reason(DEFAULT_SOCIAL_REASON);
        free_emitter.setEmail(DEFAULT_EMAIL);
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
        assertThat(testFree_emitter.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testFree_emitter.getNum_int()).isEqualTo(DEFAULT_NUM_INT);
        assertThat(testFree_emitter.getNum_ext()).isEqualTo(DEFAULT_NUM_EXT);
        assertThat(testFree_emitter.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testFree_emitter.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFree_emitter.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testFree_emitter.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFree_emitter.getSocial_reason()).isEqualTo(DEFAULT_SOCIAL_REASON);
        assertThat(testFree_emitter.getEmail()).isEqualTo(DEFAULT_EMAIL);
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
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setCreate_date(null);

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
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setActivated(null);

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
    public void checkSocial_reasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setSocial_reason(null);

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
    public void getAllFree_emitters() throws Exception {
        // Initialize the database
        free_emitterRepository.saveAndFlush(free_emitter);

        // Get all the free_emitters
        restFree_emitterMockMvc.perform(get("/api/free-emitters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_emitter.getId().intValue())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].num_int").value(hasItem(DEFAULT_NUM_INT.toString())))
                .andExpect(jsonPath("$.[*].num_ext").value(hasItem(DEFAULT_NUM_EXT.toString())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].social_reason").value(hasItem(DEFAULT_SOCIAL_REASON.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
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
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.num_int").value(DEFAULT_NUM_INT.toString()))
            .andExpect(jsonPath("$.num_ext").value(DEFAULT_NUM_EXT.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.social_reason").value(DEFAULT_SOCIAL_REASON.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
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
        updatedFree_emitter.setReference(UPDATED_REFERENCE);
        updatedFree_emitter.setNum_int(UPDATED_NUM_INT);
        updatedFree_emitter.setNum_ext(UPDATED_NUM_EXT);
        updatedFree_emitter.setStreet(UPDATED_STREET);
        updatedFree_emitter.setCreate_date(UPDATED_CREATE_DATE);
        updatedFree_emitter.setActivated(UPDATED_ACTIVATED);
        updatedFree_emitter.setRfc(UPDATED_RFC);
        updatedFree_emitter.setSocial_reason(UPDATED_SOCIAL_REASON);
        updatedFree_emitter.setEmail(UPDATED_EMAIL);

        restFree_emitterMockMvc.perform(put("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_emitter)))
                .andExpect(status().isOk());

        // Validate the Free_emitter in the database
        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeUpdate);
        Free_emitter testFree_emitter = free_emitters.get(free_emitters.size() - 1);
        assertThat(testFree_emitter.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testFree_emitter.getNum_int()).isEqualTo(UPDATED_NUM_INT);
        assertThat(testFree_emitter.getNum_ext()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testFree_emitter.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testFree_emitter.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFree_emitter.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testFree_emitter.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFree_emitter.getSocial_reason()).isEqualTo(UPDATED_SOCIAL_REASON);
        assertThat(testFree_emitter.getEmail()).isEqualTo(UPDATED_EMAIL);
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
