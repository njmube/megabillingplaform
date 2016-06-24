package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_reciver;
import org.megapractical.billingplatform.repository.Free_reciverRepository;
import org.megapractical.billingplatform.service.Free_reciverService;

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
 * Test class for the Free_reciverResource REST controller.
 *
 * @see Free_reciverResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_reciverResourceIntTest {

    private static final String DEFAULT_RFC = "AAA120000AAA";
    private static final String UPDATED_RFC = "AAA120000AAB";
    private static final String DEFAULT_BUSINESS_NAME = "AAA";
    private static final String UPDATED_BUSINESS_NAME = "BBB";
    private static final String DEFAULT_EMAIL = "aa@a.a";
    private static final String UPDATED_EMAIL = "bb@a.a";
    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_NO_INT = "AAAAA";
    private static final String UPDATED_NO_INT = "BBBBB";
    private static final String DEFAULT_NO_EXT = "AAAAA";
    private static final String UPDATED_NO_EXT = "BBBBB";
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";

    @Inject
    private Free_reciverRepository free_reciverRepository;

    @Inject
    private Free_reciverService free_reciverService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_reciverMockMvc;

    private Free_reciver free_reciver;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_reciverResource free_reciverResource = new Free_reciverResource();
        ReflectionTestUtils.setField(free_reciverResource, "free_reciverService", free_reciverService);
        this.restFree_reciverMockMvc = MockMvcBuilders.standaloneSetup(free_reciverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_reciver = new Free_reciver();
        free_reciver.setRfc(DEFAULT_RFC);
        free_reciver.setBusiness_name(DEFAULT_BUSINESS_NAME);
        free_reciver.setEmail(DEFAULT_EMAIL);
        free_reciver.setStreet(DEFAULT_STREET);
        free_reciver.setNo_int(DEFAULT_NO_INT);
        free_reciver.setNo_ext(DEFAULT_NO_EXT);
        free_reciver.setReference(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createFree_reciver() throws Exception {
        int databaseSizeBeforeCreate = free_reciverRepository.findAll().size();

        // Create the Free_reciver

        restFree_reciverMockMvc.perform(post("/api/free-recivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_reciver)))
                .andExpect(status().isCreated());

        // Validate the Free_reciver in the database
        List<Free_reciver> free_recivers = free_reciverRepository.findAll();
        assertThat(free_recivers).hasSize(databaseSizeBeforeCreate + 1);
        Free_reciver testFree_reciver = free_recivers.get(free_recivers.size() - 1);
        assertThat(testFree_reciver.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFree_reciver.getBusiness_name()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testFree_reciver.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFree_reciver.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testFree_reciver.getNo_int()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testFree_reciver.getNo_ext()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testFree_reciver.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_reciverRepository.findAll().size();
        // set the field null
        free_reciver.setRfc(null);

        // Create the Free_reciver, which fails.

        restFree_reciverMockMvc.perform(post("/api/free-recivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_reciver)))
                .andExpect(status().isBadRequest());

        List<Free_reciver> free_recivers = free_reciverRepository.findAll();
        assertThat(free_recivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusiness_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_reciverRepository.findAll().size();
        // set the field null
        free_reciver.setBusiness_name(null);

        // Create the Free_reciver, which fails.

        restFree_reciverMockMvc.perform(post("/api/free-recivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_reciver)))
                .andExpect(status().isBadRequest());

        List<Free_reciver> free_recivers = free_reciverRepository.findAll();
        assertThat(free_recivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_reciverRepository.findAll().size();
        // set the field null
        free_reciver.setEmail(null);

        // Create the Free_reciver, which fails.

        restFree_reciverMockMvc.perform(post("/api/free-recivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_reciver)))
                .andExpect(status().isBadRequest());

        List<Free_reciver> free_recivers = free_reciverRepository.findAll();
        assertThat(free_recivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_recivers() throws Exception {
        // Initialize the database
        free_reciverRepository.saveAndFlush(free_reciver);

        // Get all the free_recivers
        restFree_reciverMockMvc.perform(get("/api/free-recivers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_reciver.getId().intValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].business_name").value(hasItem(DEFAULT_BUSINESS_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].no_int").value(hasItem(DEFAULT_NO_INT.toString())))
                .andExpect(jsonPath("$.[*].no_ext").value(hasItem(DEFAULT_NO_EXT.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getFree_reciver() throws Exception {
        // Initialize the database
        free_reciverRepository.saveAndFlush(free_reciver);

        // Get the free_reciver
        restFree_reciverMockMvc.perform(get("/api/free-recivers/{id}", free_reciver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_reciver.getId().intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.business_name").value(DEFAULT_BUSINESS_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.no_int").value(DEFAULT_NO_INT.toString()))
            .andExpect(jsonPath("$.no_ext").value(DEFAULT_NO_EXT.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFree_reciver() throws Exception {
        // Get the free_reciver
        restFree_reciverMockMvc.perform(get("/api/free-recivers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_reciver() throws Exception {
        // Initialize the database
        free_reciverService.save(free_reciver);

        int databaseSizeBeforeUpdate = free_reciverRepository.findAll().size();

        // Update the free_reciver
        Free_reciver updatedFree_reciver = new Free_reciver();
        updatedFree_reciver.setId(free_reciver.getId());
        updatedFree_reciver.setRfc(UPDATED_RFC);
        updatedFree_reciver.setBusiness_name(UPDATED_BUSINESS_NAME);
        updatedFree_reciver.setEmail(UPDATED_EMAIL);
        updatedFree_reciver.setStreet(UPDATED_STREET);
        updatedFree_reciver.setNo_int(UPDATED_NO_INT);
        updatedFree_reciver.setNo_ext(UPDATED_NO_EXT);
        updatedFree_reciver.setReference(UPDATED_REFERENCE);

        restFree_reciverMockMvc.perform(put("/api/free-recivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_reciver)))
                .andExpect(status().isOk());

        // Validate the Free_reciver in the database
        List<Free_reciver> free_recivers = free_reciverRepository.findAll();
        assertThat(free_recivers).hasSize(databaseSizeBeforeUpdate);
        Free_reciver testFree_reciver = free_recivers.get(free_recivers.size() - 1);
        assertThat(testFree_reciver.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFree_reciver.getBusiness_name()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testFree_reciver.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFree_reciver.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testFree_reciver.getNo_int()).isEqualTo(UPDATED_NO_INT);
        assertThat(testFree_reciver.getNo_ext()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testFree_reciver.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void deleteFree_reciver() throws Exception {
        // Initialize the database
        free_reciverService.save(free_reciver);

        int databaseSizeBeforeDelete = free_reciverRepository.findAll().size();

        // Get the free_reciver
        restFree_reciverMockMvc.perform(delete("/api/free-recivers/{id}", free_reciver.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_reciver> free_recivers = free_reciverRepository.findAll();
        assertThat(free_recivers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
