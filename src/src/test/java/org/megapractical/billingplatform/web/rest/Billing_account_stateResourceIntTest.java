package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Billing_account_state;
import org.megapractical.billingplatform.repository.Billing_account_stateRepository;
import org.megapractical.billingplatform.service.Billing_account_stateService;

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
 * Test class for the Billing_account_stateResource REST controller.
 *
 * @see Billing_account_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Billing_account_stateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Billing_account_stateRepository billing_account_stateRepository;

    @Inject
    private Billing_account_stateService billing_account_stateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBilling_account_stateMockMvc;

    private Billing_account_state billing_account_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Billing_account_stateResource billing_account_stateResource = new Billing_account_stateResource();
        ReflectionTestUtils.setField(billing_account_stateResource, "billing_account_stateService", billing_account_stateService);
        this.restBilling_account_stateMockMvc = MockMvcBuilders.standaloneSetup(billing_account_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        billing_account_state = new Billing_account_state();
        billing_account_state.setName(DEFAULT_NAME);
        billing_account_state.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBilling_account_state() throws Exception {
        int databaseSizeBeforeCreate = billing_account_stateRepository.findAll().size();

        // Create the Billing_account_state

        restBilling_account_stateMockMvc.perform(post("/api/billing-account-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billing_account_state)))
                .andExpect(status().isCreated());

        // Validate the Billing_account_state in the database
        List<Billing_account_state> billing_account_states = billing_account_stateRepository.findAll();
        assertThat(billing_account_states).hasSize(databaseSizeBeforeCreate + 1);
        Billing_account_state testBilling_account_state = billing_account_states.get(billing_account_states.size() - 1);
        assertThat(testBilling_account_state.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBilling_account_state.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBilling_account_states() throws Exception {
        // Initialize the database
        billing_account_stateRepository.saveAndFlush(billing_account_state);

        // Get all the billing_account_states
        restBilling_account_stateMockMvc.perform(get("/api/billing-account-states?sort=id,desc&filtername= "))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(billing_account_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getBilling_account_state() throws Exception {
        // Initialize the database
        billing_account_stateRepository.saveAndFlush(billing_account_state);

        // Get the billing_account_state
        restBilling_account_stateMockMvc.perform(get("/api/billing-account-states/{id}", billing_account_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(billing_account_state.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBilling_account_state() throws Exception {
        // Get the billing_account_state
        restBilling_account_stateMockMvc.perform(get("/api/billing-account-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBilling_account_state() throws Exception {
        // Initialize the database
        billing_account_stateService.save(billing_account_state);

        int databaseSizeBeforeUpdate = billing_account_stateRepository.findAll().size();

        // Update the billing_account_state
        Billing_account_state updatedBilling_account_state = new Billing_account_state();
        updatedBilling_account_state.setId(billing_account_state.getId());
        updatedBilling_account_state.setName(UPDATED_NAME);
        updatedBilling_account_state.setDescription(UPDATED_DESCRIPTION);

        restBilling_account_stateMockMvc.perform(put("/api/billing-account-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBilling_account_state)))
                .andExpect(status().isOk());

        // Validate the Billing_account_state in the database
        List<Billing_account_state> billing_account_states = billing_account_stateRepository.findAll();
        assertThat(billing_account_states).hasSize(databaseSizeBeforeUpdate);
        Billing_account_state testBilling_account_state = billing_account_states.get(billing_account_states.size() - 1);
        assertThat(testBilling_account_state.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBilling_account_state.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteBilling_account_state() throws Exception {
        // Initialize the database
        billing_account_stateService.save(billing_account_state);

        int databaseSizeBeforeDelete = billing_account_stateRepository.findAll().size();

        // Get the billing_account_state
        restBilling_account_stateMockMvc.perform(delete("/api/billing-account-states/{id}", billing_account_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Billing_account_state> billing_account_states = billing_account_stateRepository.findAll();
        assertThat(billing_account_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
