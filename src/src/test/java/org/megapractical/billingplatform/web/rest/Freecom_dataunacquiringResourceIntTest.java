package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_dataunacquiring;
import org.megapractical.billingplatform.repository.Freecom_dataunacquiringRepository;
import org.megapractical.billingplatform.service.Freecom_dataunacquiringService;

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
 * Test class for the Freecom_dataunacquiringResource REST controller.
 *
 * @see Freecom_dataunacquiringResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_dataunacquiringResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_LAST_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_LAST_NAME = "BBBBB";
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_CURP = "AAAAA";
    private static final String UPDATED_CURP = "BBBBB";

    @Inject
    private Freecom_dataunacquiringRepository freecom_dataunacquiringRepository;

    @Inject
    private Freecom_dataunacquiringService freecom_dataunacquiringService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_dataunacquiringMockMvc;

    private Freecom_dataunacquiring freecom_dataunacquiring;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_dataunacquiringResource freecom_dataunacquiringResource = new Freecom_dataunacquiringResource();
        ReflectionTestUtils.setField(freecom_dataunacquiringResource, "freecom_dataunacquiringService", freecom_dataunacquiringService);
        this.restFreecom_dataunacquiringMockMvc = MockMvcBuilders.standaloneSetup(freecom_dataunacquiringResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_dataunacquiring = new Freecom_dataunacquiring();
        freecom_dataunacquiring.setName(DEFAULT_NAME);
        freecom_dataunacquiring.setLast_name(DEFAULT_LAST_NAME);
        freecom_dataunacquiring.setMother_last_name(DEFAULT_MOTHER_LAST_NAME);
        freecom_dataunacquiring.setRfc(DEFAULT_RFC);
        freecom_dataunacquiring.setCurp(DEFAULT_CURP);
    }

    @Test
    @Transactional
    public void createFreecom_dataunacquiring() throws Exception {
        int databaseSizeBeforeCreate = freecom_dataunacquiringRepository.findAll().size();

        // Create the Freecom_dataunacquiring

        restFreecom_dataunacquiringMockMvc.perform(post("/api/freecom-dataunacquirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataunacquiring)))
                .andExpect(status().isCreated());

        // Validate the Freecom_dataunacquiring in the database
        List<Freecom_dataunacquiring> freecom_dataunacquirings = freecom_dataunacquiringRepository.findAll();
        assertThat(freecom_dataunacquirings).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_dataunacquiring testFreecom_dataunacquiring = freecom_dataunacquirings.get(freecom_dataunacquirings.size() - 1);
        assertThat(testFreecom_dataunacquiring.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFreecom_dataunacquiring.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testFreecom_dataunacquiring.getMother_last_name()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testFreecom_dataunacquiring.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFreecom_dataunacquiring.getCurp()).isEqualTo(DEFAULT_CURP);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataunacquiringRepository.findAll().size();
        // set the field null
        freecom_dataunacquiring.setName(null);

        // Create the Freecom_dataunacquiring, which fails.

        restFreecom_dataunacquiringMockMvc.perform(post("/api/freecom-dataunacquirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataunacquiring)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataunacquiring> freecom_dataunacquirings = freecom_dataunacquiringRepository.findAll();
        assertThat(freecom_dataunacquirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataunacquiringRepository.findAll().size();
        // set the field null
        freecom_dataunacquiring.setRfc(null);

        // Create the Freecom_dataunacquiring, which fails.

        restFreecom_dataunacquiringMockMvc.perform(post("/api/freecom-dataunacquirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataunacquiring)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataunacquiring> freecom_dataunacquirings = freecom_dataunacquiringRepository.findAll();
        assertThat(freecom_dataunacquirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_dataunacquirings() throws Exception {
        // Initialize the database
        freecom_dataunacquiringRepository.saveAndFlush(freecom_dataunacquiring);

        // Get all the freecom_dataunacquirings
        restFreecom_dataunacquiringMockMvc.perform(get("/api/freecom-dataunacquirings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_dataunacquiring.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_last_name").value(hasItem(DEFAULT_MOTHER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_dataunacquiring() throws Exception {
        // Initialize the database
        freecom_dataunacquiringRepository.saveAndFlush(freecom_dataunacquiring);

        // Get the freecom_dataunacquiring
        restFreecom_dataunacquiringMockMvc.perform(get("/api/freecom-dataunacquirings/{id}", freecom_dataunacquiring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_dataunacquiring.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mother_last_name").value(DEFAULT_MOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_dataunacquiring() throws Exception {
        // Get the freecom_dataunacquiring
        restFreecom_dataunacquiringMockMvc.perform(get("/api/freecom-dataunacquirings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_dataunacquiring() throws Exception {
        // Initialize the database
        freecom_dataunacquiringService.save(freecom_dataunacquiring);

        int databaseSizeBeforeUpdate = freecom_dataunacquiringRepository.findAll().size();

        // Update the freecom_dataunacquiring
        Freecom_dataunacquiring updatedFreecom_dataunacquiring = new Freecom_dataunacquiring();
        updatedFreecom_dataunacquiring.setId(freecom_dataunacquiring.getId());
        updatedFreecom_dataunacquiring.setName(UPDATED_NAME);
        updatedFreecom_dataunacquiring.setLast_name(UPDATED_LAST_NAME);
        updatedFreecom_dataunacquiring.setMother_last_name(UPDATED_MOTHER_LAST_NAME);
        updatedFreecom_dataunacquiring.setRfc(UPDATED_RFC);
        updatedFreecom_dataunacquiring.setCurp(UPDATED_CURP);

        restFreecom_dataunacquiringMockMvc.perform(put("/api/freecom-dataunacquirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_dataunacquiring)))
                .andExpect(status().isOk());

        // Validate the Freecom_dataunacquiring in the database
        List<Freecom_dataunacquiring> freecom_dataunacquirings = freecom_dataunacquiringRepository.findAll();
        assertThat(freecom_dataunacquirings).hasSize(databaseSizeBeforeUpdate);
        Freecom_dataunacquiring testFreecom_dataunacquiring = freecom_dataunacquirings.get(freecom_dataunacquirings.size() - 1);
        assertThat(testFreecom_dataunacquiring.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFreecom_dataunacquiring.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testFreecom_dataunacquiring.getMother_last_name()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testFreecom_dataunacquiring.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFreecom_dataunacquiring.getCurp()).isEqualTo(UPDATED_CURP);
    }

    @Test
    @Transactional
    public void deleteFreecom_dataunacquiring() throws Exception {
        // Initialize the database
        freecom_dataunacquiringService.save(freecom_dataunacquiring);

        int databaseSizeBeforeDelete = freecom_dataunacquiringRepository.findAll().size();

        // Get the freecom_dataunacquiring
        restFreecom_dataunacquiringMockMvc.perform(delete("/api/freecom-dataunacquirings/{id}", freecom_dataunacquiring.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_dataunacquiring> freecom_dataunacquirings = freecom_dataunacquiringRepository.findAll();
        assertThat(freecom_dataunacquirings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
