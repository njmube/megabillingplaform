package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_ine;
import org.megapractical.billingplatform.repository.Com_ineRepository;
import org.megapractical.billingplatform.service.Com_ineService;

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
 * Test class for the Com_ineResource REST controller.
 *
 * @see Com_ineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_ineResourceIntTest {

    private static final String DEFAULT_VERSION = "AAA";
    private static final String UPDATED_VERSION = "BBB";

    private static final Integer DEFAULT_IDENT = 1;
    private static final Integer UPDATED_IDENT = 2;

    @Inject
    private Com_ineRepository com_ineRepository;

    @Inject
    private Com_ineService com_ineService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_ineMockMvc;

    private Com_ine com_ine;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_ineResource com_ineResource = new Com_ineResource();
        ReflectionTestUtils.setField(com_ineResource, "com_ineService", com_ineService);
        this.restCom_ineMockMvc = MockMvcBuilders.standaloneSetup(com_ineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_ine = new Com_ine();
        com_ine.setVersion(DEFAULT_VERSION);
        com_ine.setIdent(DEFAULT_IDENT);
    }

    @Test
    @Transactional
    public void createCom_ine() throws Exception {
        int databaseSizeBeforeCreate = com_ineRepository.findAll().size();

        // Create the Com_ine

        restCom_ineMockMvc.perform(post("/api/com-ines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ine)))
                .andExpect(status().isCreated());

        // Validate the Com_ine in the database
        List<Com_ine> com_ines = com_ineRepository.findAll();
        assertThat(com_ines).hasSize(databaseSizeBeforeCreate + 1);
        Com_ine testCom_ine = com_ines.get(com_ines.size() - 1);
        assertThat(testCom_ine.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_ine.getIdent()).isEqualTo(DEFAULT_IDENT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ineRepository.findAll().size();
        // set the field null
        com_ine.setVersion(null);

        // Create the Com_ine, which fails.

        restCom_ineMockMvc.perform(post("/api/com-ines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ine)))
                .andExpect(status().isBadRequest());

        List<Com_ine> com_ines = com_ineRepository.findAll();
        assertThat(com_ines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_ines() throws Exception {
        // Initialize the database
        com_ineRepository.saveAndFlush(com_ine);

        // Get all the com_ines
        restCom_ineMockMvc.perform(get("/api/com-ines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_ine.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].ident").value(hasItem(DEFAULT_IDENT)));
    }

    @Test
    @Transactional
    public void getCom_ine() throws Exception {
        // Initialize the database
        com_ineRepository.saveAndFlush(com_ine);

        // Get the com_ine
        restCom_ineMockMvc.perform(get("/api/com-ines/{id}", com_ine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_ine.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.ident").value(DEFAULT_IDENT));
    }

    @Test
    @Transactional
    public void getNonExistingCom_ine() throws Exception {
        // Get the com_ine
        restCom_ineMockMvc.perform(get("/api/com-ines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_ine() throws Exception {
        // Initialize the database
        com_ineService.save(com_ine);

        int databaseSizeBeforeUpdate = com_ineRepository.findAll().size();

        // Update the com_ine
        Com_ine updatedCom_ine = new Com_ine();
        updatedCom_ine.setId(com_ine.getId());
        updatedCom_ine.setVersion(UPDATED_VERSION);
        updatedCom_ine.setIdent(UPDATED_IDENT);

        restCom_ineMockMvc.perform(put("/api/com-ines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_ine)))
                .andExpect(status().isOk());

        // Validate the Com_ine in the database
        List<Com_ine> com_ines = com_ineRepository.findAll();
        assertThat(com_ines).hasSize(databaseSizeBeforeUpdate);
        Com_ine testCom_ine = com_ines.get(com_ines.size() - 1);
        assertThat(testCom_ine.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_ine.getIdent()).isEqualTo(UPDATED_IDENT);
    }

    @Test
    @Transactional
    public void deleteCom_ine() throws Exception {
        // Initialize the database
        com_ineService.save(com_ine);

        int databaseSizeBeforeDelete = com_ineRepository.findAll().size();

        // Get the com_ine
        restCom_ineMockMvc.perform(delete("/api/com-ines/{id}", com_ine.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_ine> com_ines = com_ineRepository.findAll();
        assertThat(com_ines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
