package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_dataunenajenante;
import org.megapractical.billingplatform.repository.Freecom_dataunenajenanteRepository;
import org.megapractical.billingplatform.service.Freecom_dataunenajenanteService;

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
 * Test class for the Freecom_dataunenajenanteResource REST controller.
 *
 * @see Freecom_dataunenajenanteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_dataunenajenanteResourceIntTest {

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
    private Freecom_dataunenajenanteRepository freecom_dataunenajenanteRepository;

    @Inject
    private Freecom_dataunenajenanteService freecom_dataunenajenanteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_dataunenajenanteMockMvc;

    private Freecom_dataunenajenante freecom_dataunenajenante;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_dataunenajenanteResource freecom_dataunenajenanteResource = new Freecom_dataunenajenanteResource();
        ReflectionTestUtils.setField(freecom_dataunenajenanteResource, "freecom_dataunenajenanteService", freecom_dataunenajenanteService);
        this.restFreecom_dataunenajenanteMockMvc = MockMvcBuilders.standaloneSetup(freecom_dataunenajenanteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_dataunenajenante = new Freecom_dataunenajenante();
        freecom_dataunenajenante.setName(DEFAULT_NAME);
        freecom_dataunenajenante.setLast_name(DEFAULT_LAST_NAME);
        freecom_dataunenajenante.setMother_last_name(DEFAULT_MOTHER_LAST_NAME);
        freecom_dataunenajenante.setRfc(DEFAULT_RFC);
        freecom_dataunenajenante.setCurp(DEFAULT_CURP);
    }

    @Test
    @Transactional
    public void createFreecom_dataunenajenante() throws Exception {
        int databaseSizeBeforeCreate = freecom_dataunenajenanteRepository.findAll().size();

        // Create the Freecom_dataunenajenante

        restFreecom_dataunenajenanteMockMvc.perform(post("/api/freecom-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataunenajenante)))
                .andExpect(status().isCreated());

        // Validate the Freecom_dataunenajenante in the database
        List<Freecom_dataunenajenante> freecom_dataunenajenantes = freecom_dataunenajenanteRepository.findAll();
        assertThat(freecom_dataunenajenantes).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_dataunenajenante testFreecom_dataunenajenante = freecom_dataunenajenantes.get(freecom_dataunenajenantes.size() - 1);
        assertThat(testFreecom_dataunenajenante.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFreecom_dataunenajenante.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testFreecom_dataunenajenante.getMother_last_name()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testFreecom_dataunenajenante.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFreecom_dataunenajenante.getCurp()).isEqualTo(DEFAULT_CURP);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataunenajenanteRepository.findAll().size();
        // set the field null
        freecom_dataunenajenante.setName(null);

        // Create the Freecom_dataunenajenante, which fails.

        restFreecom_dataunenajenanteMockMvc.perform(post("/api/freecom-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataunenajenante)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataunenajenante> freecom_dataunenajenantes = freecom_dataunenajenanteRepository.findAll();
        assertThat(freecom_dataunenajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataunenajenanteRepository.findAll().size();
        // set the field null
        freecom_dataunenajenante.setLast_name(null);

        // Create the Freecom_dataunenajenante, which fails.

        restFreecom_dataunenajenanteMockMvc.perform(post("/api/freecom-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataunenajenante)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataunenajenante> freecom_dataunenajenantes = freecom_dataunenajenanteRepository.findAll();
        assertThat(freecom_dataunenajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataunenajenanteRepository.findAll().size();
        // set the field null
        freecom_dataunenajenante.setRfc(null);

        // Create the Freecom_dataunenajenante, which fails.

        restFreecom_dataunenajenanteMockMvc.perform(post("/api/freecom-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataunenajenante)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataunenajenante> freecom_dataunenajenantes = freecom_dataunenajenanteRepository.findAll();
        assertThat(freecom_dataunenajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurpIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataunenajenanteRepository.findAll().size();
        // set the field null
        freecom_dataunenajenante.setCurp(null);

        // Create the Freecom_dataunenajenante, which fails.

        restFreecom_dataunenajenanteMockMvc.perform(post("/api/freecom-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataunenajenante)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataunenajenante> freecom_dataunenajenantes = freecom_dataunenajenanteRepository.findAll();
        assertThat(freecom_dataunenajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_dataunenajenantes() throws Exception {
        // Initialize the database
        freecom_dataunenajenanteRepository.saveAndFlush(freecom_dataunenajenante);

        // Get all the freecom_dataunenajenantes
        restFreecom_dataunenajenanteMockMvc.perform(get("/api/freecom-dataunenajenantes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_dataunenajenante.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_last_name").value(hasItem(DEFAULT_MOTHER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_dataunenajenante() throws Exception {
        // Initialize the database
        freecom_dataunenajenanteRepository.saveAndFlush(freecom_dataunenajenante);

        // Get the freecom_dataunenajenante
        restFreecom_dataunenajenanteMockMvc.perform(get("/api/freecom-dataunenajenantes/{id}", freecom_dataunenajenante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_dataunenajenante.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mother_last_name").value(DEFAULT_MOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_dataunenajenante() throws Exception {
        // Get the freecom_dataunenajenante
        restFreecom_dataunenajenanteMockMvc.perform(get("/api/freecom-dataunenajenantes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_dataunenajenante() throws Exception {
        // Initialize the database
        freecom_dataunenajenanteService.save(freecom_dataunenajenante);

        int databaseSizeBeforeUpdate = freecom_dataunenajenanteRepository.findAll().size();

        // Update the freecom_dataunenajenante
        Freecom_dataunenajenante updatedFreecom_dataunenajenante = new Freecom_dataunenajenante();
        updatedFreecom_dataunenajenante.setId(freecom_dataunenajenante.getId());
        updatedFreecom_dataunenajenante.setName(UPDATED_NAME);
        updatedFreecom_dataunenajenante.setLast_name(UPDATED_LAST_NAME);
        updatedFreecom_dataunenajenante.setMother_last_name(UPDATED_MOTHER_LAST_NAME);
        updatedFreecom_dataunenajenante.setRfc(UPDATED_RFC);
        updatedFreecom_dataunenajenante.setCurp(UPDATED_CURP);

        restFreecom_dataunenajenanteMockMvc.perform(put("/api/freecom-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_dataunenajenante)))
                .andExpect(status().isOk());

        // Validate the Freecom_dataunenajenante in the database
        List<Freecom_dataunenajenante> freecom_dataunenajenantes = freecom_dataunenajenanteRepository.findAll();
        assertThat(freecom_dataunenajenantes).hasSize(databaseSizeBeforeUpdate);
        Freecom_dataunenajenante testFreecom_dataunenajenante = freecom_dataunenajenantes.get(freecom_dataunenajenantes.size() - 1);
        assertThat(testFreecom_dataunenajenante.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFreecom_dataunenajenante.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testFreecom_dataunenajenante.getMother_last_name()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testFreecom_dataunenajenante.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFreecom_dataunenajenante.getCurp()).isEqualTo(UPDATED_CURP);
    }

    @Test
    @Transactional
    public void deleteFreecom_dataunenajenante() throws Exception {
        // Initialize the database
        freecom_dataunenajenanteService.save(freecom_dataunenajenante);

        int databaseSizeBeforeDelete = freecom_dataunenajenanteRepository.findAll().size();

        // Get the freecom_dataunenajenante
        restFreecom_dataunenajenanteMockMvc.perform(delete("/api/freecom-dataunenajenantes/{id}", freecom_dataunenajenante.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_dataunenajenante> freecom_dataunenajenantes = freecom_dataunenajenanteRepository.findAll();
        assertThat(freecom_dataunenajenantes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
