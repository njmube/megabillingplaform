package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_dataunenajenante;
import org.megapractical.billingplatform.repository.Com_dataunenajenanteRepository;
import org.megapractical.billingplatform.service.Com_dataunenajenanteService;

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
 * Test class for the Com_dataunenajenanteResource REST controller.
 *
 * @see Com_dataunenajenanteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_dataunenajenanteResourceIntTest {

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
    private Com_dataunenajenanteRepository com_dataunenajenanteRepository;

    @Inject
    private Com_dataunenajenanteService com_dataunenajenanteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_dataunenajenanteMockMvc;

    private Com_dataunenajenante com_dataunenajenante;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_dataunenajenanteResource com_dataunenajenanteResource = new Com_dataunenajenanteResource();
        ReflectionTestUtils.setField(com_dataunenajenanteResource, "com_dataunenajenanteService", com_dataunenajenanteService);
        this.restCom_dataunenajenanteMockMvc = MockMvcBuilders.standaloneSetup(com_dataunenajenanteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_dataunenajenante = new Com_dataunenajenante();
        com_dataunenajenante.setName(DEFAULT_NAME);
        com_dataunenajenante.setLast_name(DEFAULT_LAST_NAME);
        com_dataunenajenante.setMother_last_name(DEFAULT_MOTHER_LAST_NAME);
        com_dataunenajenante.setRfc(DEFAULT_RFC);
        com_dataunenajenante.setCurp(DEFAULT_CURP);
    }

    @Test
    @Transactional
    public void createCom_dataunenajenante() throws Exception {
        int databaseSizeBeforeCreate = com_dataunenajenanteRepository.findAll().size();

        // Create the Com_dataunenajenante

        restCom_dataunenajenanteMockMvc.perform(post("/api/com-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataunenajenante)))
                .andExpect(status().isCreated());

        // Validate the Com_dataunenajenante in the database
        List<Com_dataunenajenante> com_dataunenajenantes = com_dataunenajenanteRepository.findAll();
        assertThat(com_dataunenajenantes).hasSize(databaseSizeBeforeCreate + 1);
        Com_dataunenajenante testCom_dataunenajenante = com_dataunenajenantes.get(com_dataunenajenantes.size() - 1);
        assertThat(testCom_dataunenajenante.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCom_dataunenajenante.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCom_dataunenajenante.getMother_last_name()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testCom_dataunenajenante.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_dataunenajenante.getCurp()).isEqualTo(DEFAULT_CURP);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataunenajenanteRepository.findAll().size();
        // set the field null
        com_dataunenajenante.setName(null);

        // Create the Com_dataunenajenante, which fails.

        restCom_dataunenajenanteMockMvc.perform(post("/api/com-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataunenajenante)))
                .andExpect(status().isBadRequest());

        List<Com_dataunenajenante> com_dataunenajenantes = com_dataunenajenanteRepository.findAll();
        assertThat(com_dataunenajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataunenajenanteRepository.findAll().size();
        // set the field null
        com_dataunenajenante.setLast_name(null);

        // Create the Com_dataunenajenante, which fails.

        restCom_dataunenajenanteMockMvc.perform(post("/api/com-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataunenajenante)))
                .andExpect(status().isBadRequest());

        List<Com_dataunenajenante> com_dataunenajenantes = com_dataunenajenanteRepository.findAll();
        assertThat(com_dataunenajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataunenajenanteRepository.findAll().size();
        // set the field null
        com_dataunenajenante.setRfc(null);

        // Create the Com_dataunenajenante, which fails.

        restCom_dataunenajenanteMockMvc.perform(post("/api/com-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataunenajenante)))
                .andExpect(status().isBadRequest());

        List<Com_dataunenajenante> com_dataunenajenantes = com_dataunenajenanteRepository.findAll();
        assertThat(com_dataunenajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurpIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataunenajenanteRepository.findAll().size();
        // set the field null
        com_dataunenajenante.setCurp(null);

        // Create the Com_dataunenajenante, which fails.

        restCom_dataunenajenanteMockMvc.perform(post("/api/com-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataunenajenante)))
                .andExpect(status().isBadRequest());

        List<Com_dataunenajenante> com_dataunenajenantes = com_dataunenajenanteRepository.findAll();
        assertThat(com_dataunenajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_dataunenajenantes() throws Exception {
        // Initialize the database
        com_dataunenajenanteRepository.saveAndFlush(com_dataunenajenante);

        // Get all the com_dataunenajenantes
        restCom_dataunenajenanteMockMvc.perform(get("/api/com-dataunenajenantes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_dataunenajenante.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_last_name").value(hasItem(DEFAULT_MOTHER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())));
    }

    @Test
    @Transactional
    public void getCom_dataunenajenante() throws Exception {
        // Initialize the database
        com_dataunenajenanteRepository.saveAndFlush(com_dataunenajenante);

        // Get the com_dataunenajenante
        restCom_dataunenajenanteMockMvc.perform(get("/api/com-dataunenajenantes/{id}", com_dataunenajenante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_dataunenajenante.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mother_last_name").value(DEFAULT_MOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_dataunenajenante() throws Exception {
        // Get the com_dataunenajenante
        restCom_dataunenajenanteMockMvc.perform(get("/api/com-dataunenajenantes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_dataunenajenante() throws Exception {
        // Initialize the database
        com_dataunenajenanteService.save(com_dataunenajenante);

        int databaseSizeBeforeUpdate = com_dataunenajenanteRepository.findAll().size();

        // Update the com_dataunenajenante
        Com_dataunenajenante updatedCom_dataunenajenante = new Com_dataunenajenante();
        updatedCom_dataunenajenante.setId(com_dataunenajenante.getId());
        updatedCom_dataunenajenante.setName(UPDATED_NAME);
        updatedCom_dataunenajenante.setLast_name(UPDATED_LAST_NAME);
        updatedCom_dataunenajenante.setMother_last_name(UPDATED_MOTHER_LAST_NAME);
        updatedCom_dataunenajenante.setRfc(UPDATED_RFC);
        updatedCom_dataunenajenante.setCurp(UPDATED_CURP);

        restCom_dataunenajenanteMockMvc.perform(put("/api/com-dataunenajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_dataunenajenante)))
                .andExpect(status().isOk());

        // Validate the Com_dataunenajenante in the database
        List<Com_dataunenajenante> com_dataunenajenantes = com_dataunenajenanteRepository.findAll();
        assertThat(com_dataunenajenantes).hasSize(databaseSizeBeforeUpdate);
        Com_dataunenajenante testCom_dataunenajenante = com_dataunenajenantes.get(com_dataunenajenantes.size() - 1);
        assertThat(testCom_dataunenajenante.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCom_dataunenajenante.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCom_dataunenajenante.getMother_last_name()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testCom_dataunenajenante.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_dataunenajenante.getCurp()).isEqualTo(UPDATED_CURP);
    }

    @Test
    @Transactional
    public void deleteCom_dataunenajenante() throws Exception {
        // Initialize the database
        com_dataunenajenanteService.save(com_dataunenajenante);

        int databaseSizeBeforeDelete = com_dataunenajenanteRepository.findAll().size();

        // Get the com_dataunenajenante
        restCom_dataunenajenanteMockMvc.perform(delete("/api/com-dataunenajenantes/{id}", com_dataunenajenante.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_dataunenajenante> com_dataunenajenantes = com_dataunenajenanteRepository.findAll();
        assertThat(com_dataunenajenantes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
