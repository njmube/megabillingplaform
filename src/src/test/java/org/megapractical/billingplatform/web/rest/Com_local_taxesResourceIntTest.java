package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_local_taxes;
import org.megapractical.billingplatform.repository.Com_local_taxesRepository;
import org.megapractical.billingplatform.service.Com_local_taxesService;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_local_taxesResource REST controller.
 *
 * @see Com_local_taxesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_local_taxesResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_LOCAL_RETENTIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_LOCAL_RETENTIONS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_LOCAL_TRANSFERED = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_LOCAL_TRANSFERED = new BigDecimal(2);

    @Inject
    private Com_local_taxesRepository com_local_taxesRepository;

    @Inject
    private Com_local_taxesService com_local_taxesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_local_taxesMockMvc;

    private Com_local_taxes com_local_taxes;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_local_taxesResource com_local_taxesResource = new Com_local_taxesResource();
        ReflectionTestUtils.setField(com_local_taxesResource, "com_local_taxesService", com_local_taxesService);
        this.restCom_local_taxesMockMvc = MockMvcBuilders.standaloneSetup(com_local_taxesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_local_taxes = new Com_local_taxes();
        com_local_taxes.setVersion(DEFAULT_VERSION);
        com_local_taxes.setTotal_local_retentions(DEFAULT_TOTAL_LOCAL_RETENTIONS);
        com_local_taxes.setTotal_local_transfered(DEFAULT_TOTAL_LOCAL_TRANSFERED);
    }

    @Test
    @Transactional
    public void createCom_local_taxes() throws Exception {
        int databaseSizeBeforeCreate = com_local_taxesRepository.findAll().size();

        // Create the Com_local_taxes

        restCom_local_taxesMockMvc.perform(post("/api/com-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_taxes)))
                .andExpect(status().isCreated());

        // Validate the Com_local_taxes in the database
        List<Com_local_taxes> com_local_taxes = com_local_taxesRepository.findAll();
        assertThat(com_local_taxes).hasSize(databaseSizeBeforeCreate + 1);
        Com_local_taxes testCom_local_taxes = com_local_taxes.get(com_local_taxes.size() - 1);
        assertThat(testCom_local_taxes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_local_taxes.getTotal_local_retentions()).isEqualTo(DEFAULT_TOTAL_LOCAL_RETENTIONS);
        assertThat(testCom_local_taxes.getTotal_local_transfered()).isEqualTo(DEFAULT_TOTAL_LOCAL_TRANSFERED);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_taxesRepository.findAll().size();
        // set the field null
        com_local_taxes.setVersion(null);

        // Create the Com_local_taxes, which fails.

        restCom_local_taxesMockMvc.perform(post("/api/com-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_taxes)))
                .andExpect(status().isBadRequest());

        List<Com_local_taxes> com_local_taxes = com_local_taxesRepository.findAll();
        assertThat(com_local_taxes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotal_local_retentionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_taxesRepository.findAll().size();
        // set the field null
        com_local_taxes.setTotal_local_retentions(null);

        // Create the Com_local_taxes, which fails.

        restCom_local_taxesMockMvc.perform(post("/api/com-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_taxes)))
                .andExpect(status().isBadRequest());

        List<Com_local_taxes> com_local_taxes = com_local_taxesRepository.findAll();
        assertThat(com_local_taxes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotal_local_transferedIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_taxesRepository.findAll().size();
        // set the field null
        com_local_taxes.setTotal_local_transfered(null);

        // Create the Com_local_taxes, which fails.

        restCom_local_taxesMockMvc.perform(post("/api/com-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_taxes)))
                .andExpect(status().isBadRequest());

        List<Com_local_taxes> com_local_taxes = com_local_taxesRepository.findAll();
        assertThat(com_local_taxes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_local_taxes() throws Exception {
        // Initialize the database
        com_local_taxesRepository.saveAndFlush(com_local_taxes);

        // Get all the com_local_taxes
        restCom_local_taxesMockMvc.perform(get("/api/com-local-taxes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_local_taxes.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].total_local_retentions").value(hasItem(DEFAULT_TOTAL_LOCAL_RETENTIONS.intValue())))
                .andExpect(jsonPath("$.[*].total_local_transfered").value(hasItem(DEFAULT_TOTAL_LOCAL_TRANSFERED.intValue())));
    }

    @Test
    @Transactional
    public void getCom_local_taxes() throws Exception {
        // Initialize the database
        com_local_taxesRepository.saveAndFlush(com_local_taxes);

        // Get the com_local_taxes
        restCom_local_taxesMockMvc.perform(get("/api/com-local-taxes/{id}", com_local_taxes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_local_taxes.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.total_local_retentions").value(DEFAULT_TOTAL_LOCAL_RETENTIONS.intValue()))
            .andExpect(jsonPath("$.total_local_transfered").value(DEFAULT_TOTAL_LOCAL_TRANSFERED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_local_taxes() throws Exception {
        // Get the com_local_taxes
        restCom_local_taxesMockMvc.perform(get("/api/com-local-taxes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_local_taxes() throws Exception {
        // Initialize the database
        com_local_taxesService.save(com_local_taxes);

        int databaseSizeBeforeUpdate = com_local_taxesRepository.findAll().size();

        // Update the com_local_taxes
        Com_local_taxes updatedCom_local_taxes = new Com_local_taxes();
        updatedCom_local_taxes.setId(com_local_taxes.getId());
        updatedCom_local_taxes.setVersion(UPDATED_VERSION);
        updatedCom_local_taxes.setTotal_local_retentions(UPDATED_TOTAL_LOCAL_RETENTIONS);
        updatedCom_local_taxes.setTotal_local_transfered(UPDATED_TOTAL_LOCAL_TRANSFERED);

        restCom_local_taxesMockMvc.perform(put("/api/com-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_local_taxes)))
                .andExpect(status().isOk());

        // Validate the Com_local_taxes in the database
        List<Com_local_taxes> com_local_taxes = com_local_taxesRepository.findAll();
        assertThat(com_local_taxes).hasSize(databaseSizeBeforeUpdate);
        Com_local_taxes testCom_local_taxes = com_local_taxes.get(com_local_taxes.size() - 1);
        assertThat(testCom_local_taxes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_local_taxes.getTotal_local_retentions()).isEqualTo(UPDATED_TOTAL_LOCAL_RETENTIONS);
        assertThat(testCom_local_taxes.getTotal_local_transfered()).isEqualTo(UPDATED_TOTAL_LOCAL_TRANSFERED);
    }

    @Test
    @Transactional
    public void deleteCom_local_taxes() throws Exception {
        // Initialize the database
        com_local_taxesService.save(com_local_taxes);

        int databaseSizeBeforeDelete = com_local_taxesRepository.findAll().size();

        // Get the com_local_taxes
        restCom_local_taxesMockMvc.perform(delete("/api/com-local-taxes/{id}", com_local_taxes.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_local_taxes> com_local_taxes = com_local_taxesRepository.findAll();
        assertThat(com_local_taxes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
