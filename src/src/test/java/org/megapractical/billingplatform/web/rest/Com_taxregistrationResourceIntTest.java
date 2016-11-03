package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_taxregistration;
import org.megapractical.billingplatform.repository.Com_taxregistrationRepository;
import org.megapractical.billingplatform.service.Com_taxregistrationService;

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
 * Test class for the Com_taxregistrationResource REST controller.
 *
 * @see Com_taxregistrationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_taxregistrationResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_FOLIO = "AAAAA";
    private static final String UPDATED_FOLIO = "BBBBB";

    @Inject
    private Com_taxregistrationRepository com_taxregistrationRepository;

    @Inject
    private Com_taxregistrationService com_taxregistrationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_taxregistrationMockMvc;

    private Com_taxregistration com_taxregistration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_taxregistrationResource com_taxregistrationResource = new Com_taxregistrationResource();
        ReflectionTestUtils.setField(com_taxregistrationResource, "com_taxregistrationService", com_taxregistrationService);
        this.restCom_taxregistrationMockMvc = MockMvcBuilders.standaloneSetup(com_taxregistrationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_taxregistration = new Com_taxregistration();
        com_taxregistration.setVersion(DEFAULT_VERSION);
        com_taxregistration.setFolio(DEFAULT_FOLIO);
    }

    @Test
    @Transactional
    public void createCom_taxregistration() throws Exception {
        int databaseSizeBeforeCreate = com_taxregistrationRepository.findAll().size();

        // Create the Com_taxregistration

        restCom_taxregistrationMockMvc.perform(post("/api/com-taxregistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_taxregistration)))
                .andExpect(status().isCreated());

        // Validate the Com_taxregistration in the database
        List<Com_taxregistration> com_taxregistrations = com_taxregistrationRepository.findAll();
        assertThat(com_taxregistrations).hasSize(databaseSizeBeforeCreate + 1);
        Com_taxregistration testCom_taxregistration = com_taxregistrations.get(com_taxregistrations.size() - 1);
        assertThat(testCom_taxregistration.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_taxregistration.getFolio()).isEqualTo(DEFAULT_FOLIO);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_taxregistrationRepository.findAll().size();
        // set the field null
        com_taxregistration.setVersion(null);

        // Create the Com_taxregistration, which fails.

        restCom_taxregistrationMockMvc.perform(post("/api/com-taxregistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_taxregistration)))
                .andExpect(status().isBadRequest());

        List<Com_taxregistration> com_taxregistrations = com_taxregistrationRepository.findAll();
        assertThat(com_taxregistrations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolioIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_taxregistrationRepository.findAll().size();
        // set the field null
        com_taxregistration.setFolio(null);

        // Create the Com_taxregistration, which fails.

        restCom_taxregistrationMockMvc.perform(post("/api/com-taxregistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_taxregistration)))
                .andExpect(status().isBadRequest());

        List<Com_taxregistration> com_taxregistrations = com_taxregistrationRepository.findAll();
        assertThat(com_taxregistrations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_taxregistrations() throws Exception {
        // Initialize the database
        com_taxregistrationRepository.saveAndFlush(com_taxregistration);

        // Get all the com_taxregistrations
        restCom_taxregistrationMockMvc.perform(get("/api/com-taxregistrations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_taxregistration.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO.toString())));
    }

    @Test
    @Transactional
    public void getCom_taxregistration() throws Exception {
        // Initialize the database
        com_taxregistrationRepository.saveAndFlush(com_taxregistration);

        // Get the com_taxregistration
        restCom_taxregistrationMockMvc.perform(get("/api/com-taxregistrations/{id}", com_taxregistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_taxregistration.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_taxregistration() throws Exception {
        // Get the com_taxregistration
        restCom_taxregistrationMockMvc.perform(get("/api/com-taxregistrations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_taxregistration() throws Exception {
        // Initialize the database
        com_taxregistrationService.save(com_taxregistration);

        int databaseSizeBeforeUpdate = com_taxregistrationRepository.findAll().size();

        // Update the com_taxregistration
        Com_taxregistration updatedCom_taxregistration = new Com_taxregistration();
        updatedCom_taxregistration.setId(com_taxregistration.getId());
        updatedCom_taxregistration.setVersion(UPDATED_VERSION);
        updatedCom_taxregistration.setFolio(UPDATED_FOLIO);

        restCom_taxregistrationMockMvc.perform(put("/api/com-taxregistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_taxregistration)))
                .andExpect(status().isOk());

        // Validate the Com_taxregistration in the database
        List<Com_taxregistration> com_taxregistrations = com_taxregistrationRepository.findAll();
        assertThat(com_taxregistrations).hasSize(databaseSizeBeforeUpdate);
        Com_taxregistration testCom_taxregistration = com_taxregistrations.get(com_taxregistrations.size() - 1);
        assertThat(testCom_taxregistration.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_taxregistration.getFolio()).isEqualTo(UPDATED_FOLIO);
    }

    @Test
    @Transactional
    public void deleteCom_taxregistration() throws Exception {
        // Initialize the database
        com_taxregistrationService.save(com_taxregistration);

        int databaseSizeBeforeDelete = com_taxregistrationRepository.findAll().size();

        // Get the com_taxregistration
        restCom_taxregistrationMockMvc.perform(delete("/api/com-taxregistrations/{id}", com_taxregistration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_taxregistration> com_taxregistrations = com_taxregistrationRepository.findAll();
        assertThat(com_taxregistrations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
