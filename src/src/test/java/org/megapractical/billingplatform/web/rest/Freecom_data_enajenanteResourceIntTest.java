package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_data_enajenante;
import org.megapractical.billingplatform.repository.Freecom_data_enajenanteRepository;
import org.megapractical.billingplatform.service.Freecom_data_enajenanteService;

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
 * Test class for the Freecom_data_enajenanteResource REST controller.
 *
 * @see Freecom_data_enajenanteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_data_enajenanteResourceIntTest {

    private static final String DEFAULT_COPROSOCCONYUGAIE = "AAAAAAAAAA";
    private static final String UPDATED_COPROSOCCONYUGAIE = "BBBBBBBBBB";

    @Inject
    private Freecom_data_enajenanteRepository freecom_data_enajenanteRepository;

    @Inject
    private Freecom_data_enajenanteService freecom_data_enajenanteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_data_enajenanteMockMvc;

    private Freecom_data_enajenante freecom_data_enajenante;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_data_enajenanteResource freecom_data_enajenanteResource = new Freecom_data_enajenanteResource();
        ReflectionTestUtils.setField(freecom_data_enajenanteResource, "freecom_data_enajenanteService", freecom_data_enajenanteService);
        this.restFreecom_data_enajenanteMockMvc = MockMvcBuilders.standaloneSetup(freecom_data_enajenanteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_data_enajenante = new Freecom_data_enajenante();
        freecom_data_enajenante.setCoprosocconyugaie(DEFAULT_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void createFreecom_data_enajenante() throws Exception {
        int databaseSizeBeforeCreate = freecom_data_enajenanteRepository.findAll().size();

        // Create the Freecom_data_enajenante

        restFreecom_data_enajenanteMockMvc.perform(post("/api/freecom-data-enajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_data_enajenante)))
                .andExpect(status().isCreated());

        // Validate the Freecom_data_enajenante in the database
        List<Freecom_data_enajenante> freecom_data_enajenantes = freecom_data_enajenanteRepository.findAll();
        assertThat(freecom_data_enajenantes).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_data_enajenante testFreecom_data_enajenante = freecom_data_enajenantes.get(freecom_data_enajenantes.size() - 1);
        assertThat(testFreecom_data_enajenante.getCoprosocconyugaie()).isEqualTo(DEFAULT_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void checkCoprosocconyugaieIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_data_enajenanteRepository.findAll().size();
        // set the field null
        freecom_data_enajenante.setCoprosocconyugaie(null);

        // Create the Freecom_data_enajenante, which fails.

        restFreecom_data_enajenanteMockMvc.perform(post("/api/freecom-data-enajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_data_enajenante)))
                .andExpect(status().isBadRequest());

        List<Freecom_data_enajenante> freecom_data_enajenantes = freecom_data_enajenanteRepository.findAll();
        assertThat(freecom_data_enajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_data_enajenantes() throws Exception {
        // Initialize the database
        freecom_data_enajenanteRepository.saveAndFlush(freecom_data_enajenante);

        // Get all the freecom_data_enajenantes
        restFreecom_data_enajenanteMockMvc.perform(get("/api/freecom-data-enajenantes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_data_enajenante.getId().intValue())))
                .andExpect(jsonPath("$.[*].coprosocconyugaie").value(hasItem(DEFAULT_COPROSOCCONYUGAIE.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_data_enajenante() throws Exception {
        // Initialize the database
        freecom_data_enajenanteRepository.saveAndFlush(freecom_data_enajenante);

        // Get the freecom_data_enajenante
        restFreecom_data_enajenanteMockMvc.perform(get("/api/freecom-data-enajenantes/{id}", freecom_data_enajenante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_data_enajenante.getId().intValue()))
            .andExpect(jsonPath("$.coprosocconyugaie").value(DEFAULT_COPROSOCCONYUGAIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_data_enajenante() throws Exception {
        // Get the freecom_data_enajenante
        restFreecom_data_enajenanteMockMvc.perform(get("/api/freecom-data-enajenantes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_data_enajenante() throws Exception {
        // Initialize the database
        freecom_data_enajenanteService.save(freecom_data_enajenante);

        int databaseSizeBeforeUpdate = freecom_data_enajenanteRepository.findAll().size();

        // Update the freecom_data_enajenante
        Freecom_data_enajenante updatedFreecom_data_enajenante = new Freecom_data_enajenante();
        updatedFreecom_data_enajenante.setId(freecom_data_enajenante.getId());
        updatedFreecom_data_enajenante.setCoprosocconyugaie(UPDATED_COPROSOCCONYUGAIE);

        restFreecom_data_enajenanteMockMvc.perform(put("/api/freecom-data-enajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_data_enajenante)))
                .andExpect(status().isOk());

        // Validate the Freecom_data_enajenante in the database
        List<Freecom_data_enajenante> freecom_data_enajenantes = freecom_data_enajenanteRepository.findAll();
        assertThat(freecom_data_enajenantes).hasSize(databaseSizeBeforeUpdate);
        Freecom_data_enajenante testFreecom_data_enajenante = freecom_data_enajenantes.get(freecom_data_enajenantes.size() - 1);
        assertThat(testFreecom_data_enajenante.getCoprosocconyugaie()).isEqualTo(UPDATED_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void deleteFreecom_data_enajenante() throws Exception {
        // Initialize the database
        freecom_data_enajenanteService.save(freecom_data_enajenante);

        int databaseSizeBeforeDelete = freecom_data_enajenanteRepository.findAll().size();

        // Get the freecom_data_enajenante
        restFreecom_data_enajenanteMockMvc.perform(delete("/api/freecom-data-enajenantes/{id}", freecom_data_enajenante.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_data_enajenante> freecom_data_enajenantes = freecom_data_enajenanteRepository.findAll();
        assertThat(freecom_data_enajenantes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
