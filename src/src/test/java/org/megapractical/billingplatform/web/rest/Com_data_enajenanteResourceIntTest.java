package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_data_enajenante;
import org.megapractical.billingplatform.repository.Com_data_enajenanteRepository;
import org.megapractical.billingplatform.service.Com_data_enajenanteService;

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
 * Test class for the Com_data_enajenanteResource REST controller.
 *
 * @see Com_data_enajenanteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_data_enajenanteResourceIntTest {

    private static final String DEFAULT_COPROSOCCONYUGAIE = "AAAAAAAAAA";
    private static final String UPDATED_COPROSOCCONYUGAIE = "BBBBBBBBBB";

    @Inject
    private Com_data_enajenanteRepository com_data_enajenanteRepository;

    @Inject
    private Com_data_enajenanteService com_data_enajenanteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_data_enajenanteMockMvc;

    private Com_data_enajenante com_data_enajenante;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_data_enajenanteResource com_data_enajenanteResource = new Com_data_enajenanteResource();
        ReflectionTestUtils.setField(com_data_enajenanteResource, "com_data_enajenanteService", com_data_enajenanteService);
        this.restCom_data_enajenanteMockMvc = MockMvcBuilders.standaloneSetup(com_data_enajenanteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_data_enajenante = new Com_data_enajenante();
        com_data_enajenante.setCoprosocconyugaie(DEFAULT_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void createCom_data_enajenante() throws Exception {
        int databaseSizeBeforeCreate = com_data_enajenanteRepository.findAll().size();

        // Create the Com_data_enajenante

        restCom_data_enajenanteMockMvc.perform(post("/api/com-data-enajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_data_enajenante)))
                .andExpect(status().isCreated());

        // Validate the Com_data_enajenante in the database
        List<Com_data_enajenante> com_data_enajenantes = com_data_enajenanteRepository.findAll();
        assertThat(com_data_enajenantes).hasSize(databaseSizeBeforeCreate + 1);
        Com_data_enajenante testCom_data_enajenante = com_data_enajenantes.get(com_data_enajenantes.size() - 1);
        assertThat(testCom_data_enajenante.getCoprosocconyugaie()).isEqualTo(DEFAULT_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void checkCoprosocconyugaieIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_data_enajenanteRepository.findAll().size();
        // set the field null
        com_data_enajenante.setCoprosocconyugaie(null);

        // Create the Com_data_enajenante, which fails.

        restCom_data_enajenanteMockMvc.perform(post("/api/com-data-enajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_data_enajenante)))
                .andExpect(status().isBadRequest());

        List<Com_data_enajenante> com_data_enajenantes = com_data_enajenanteRepository.findAll();
        assertThat(com_data_enajenantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_data_enajenantes() throws Exception {
        // Initialize the database
        com_data_enajenanteRepository.saveAndFlush(com_data_enajenante);

        // Get all the com_data_enajenantes
        restCom_data_enajenanteMockMvc.perform(get("/api/com-data-enajenantes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_data_enajenante.getId().intValue())))
                .andExpect(jsonPath("$.[*].coprosocconyugaie").value(hasItem(DEFAULT_COPROSOCCONYUGAIE.toString())));
    }

    @Test
    @Transactional
    public void getCom_data_enajenante() throws Exception {
        // Initialize the database
        com_data_enajenanteRepository.saveAndFlush(com_data_enajenante);

        // Get the com_data_enajenante
        restCom_data_enajenanteMockMvc.perform(get("/api/com-data-enajenantes/{id}", com_data_enajenante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_data_enajenante.getId().intValue()))
            .andExpect(jsonPath("$.coprosocconyugaie").value(DEFAULT_COPROSOCCONYUGAIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_data_enajenante() throws Exception {
        // Get the com_data_enajenante
        restCom_data_enajenanteMockMvc.perform(get("/api/com-data-enajenantes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_data_enajenante() throws Exception {
        // Initialize the database
        com_data_enajenanteService.save(com_data_enajenante);

        int databaseSizeBeforeUpdate = com_data_enajenanteRepository.findAll().size();

        // Update the com_data_enajenante
        Com_data_enajenante updatedCom_data_enajenante = new Com_data_enajenante();
        updatedCom_data_enajenante.setId(com_data_enajenante.getId());
        updatedCom_data_enajenante.setCoprosocconyugaie(UPDATED_COPROSOCCONYUGAIE);

        restCom_data_enajenanteMockMvc.perform(put("/api/com-data-enajenantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_data_enajenante)))
                .andExpect(status().isOk());

        // Validate the Com_data_enajenante in the database
        List<Com_data_enajenante> com_data_enajenantes = com_data_enajenanteRepository.findAll();
        assertThat(com_data_enajenantes).hasSize(databaseSizeBeforeUpdate);
        Com_data_enajenante testCom_data_enajenante = com_data_enajenantes.get(com_data_enajenantes.size() - 1);
        assertThat(testCom_data_enajenante.getCoprosocconyugaie()).isEqualTo(UPDATED_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void deleteCom_data_enajenante() throws Exception {
        // Initialize the database
        com_data_enajenanteService.save(com_data_enajenante);

        int databaseSizeBeforeDelete = com_data_enajenanteRepository.findAll().size();

        // Get the com_data_enajenante
        restCom_data_enajenanteMockMvc.perform(delete("/api/com-data-enajenantes/{id}", com_data_enajenante.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_data_enajenante> com_data_enajenantes = com_data_enajenanteRepository.findAll();
        assertThat(com_data_enajenantes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
