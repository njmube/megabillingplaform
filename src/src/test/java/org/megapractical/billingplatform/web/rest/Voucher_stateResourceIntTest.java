package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Voucher_state;
import org.megapractical.billingplatform.repository.Voucher_stateRepository;
import org.megapractical.billingplatform.service.Voucher_stateService;

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
 * Test class for the Voucher_stateResource REST controller.
 *
 * @see Voucher_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Voucher_stateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Voucher_stateRepository voucher_stateRepository;

    @Inject
    private Voucher_stateService voucher_stateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVoucher_stateMockMvc;

    private Voucher_state voucher_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Voucher_stateResource voucher_stateResource = new Voucher_stateResource();
        ReflectionTestUtils.setField(voucher_stateResource, "voucher_stateService", voucher_stateService);
        this.restVoucher_stateMockMvc = MockMvcBuilders.standaloneSetup(voucher_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        voucher_state = new Voucher_state();
        voucher_state.setName(DEFAULT_NAME);
        voucher_state.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createVoucher_state() throws Exception {
        int databaseSizeBeforeCreate = voucher_stateRepository.findAll().size();

        // Create the Voucher_state

        restVoucher_stateMockMvc.perform(post("/api/voucher-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(voucher_state)))
                .andExpect(status().isCreated());

        // Validate the Voucher_state in the database
        List<Voucher_state> voucher_states = voucher_stateRepository.findAll();
        assertThat(voucher_states).hasSize(databaseSizeBeforeCreate + 1);
        Voucher_state testVoucher_state = voucher_states.get(voucher_states.size() - 1);
        assertThat(testVoucher_state.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVoucher_state.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVoucher_states() throws Exception {
        // Initialize the database
        voucher_stateRepository.saveAndFlush(voucher_state);

        // Get all the voucher_states
        restVoucher_stateMockMvc.perform(get("/api/voucher-states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(voucher_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getVoucher_state() throws Exception {
        // Initialize the database
        voucher_stateRepository.saveAndFlush(voucher_state);

        // Get the voucher_state
        restVoucher_stateMockMvc.perform(get("/api/voucher-states/{id}", voucher_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(voucher_state.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucher_state() throws Exception {
        // Get the voucher_state
        restVoucher_stateMockMvc.perform(get("/api/voucher-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucher_state() throws Exception {
        // Initialize the database
        voucher_stateService.save(voucher_state);

        int databaseSizeBeforeUpdate = voucher_stateRepository.findAll().size();

        // Update the voucher_state
        Voucher_state updatedVoucher_state = new Voucher_state();
        updatedVoucher_state.setId(voucher_state.getId());
        updatedVoucher_state.setName(UPDATED_NAME);
        updatedVoucher_state.setDescription(UPDATED_DESCRIPTION);

        restVoucher_stateMockMvc.perform(put("/api/voucher-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVoucher_state)))
                .andExpect(status().isOk());

        // Validate the Voucher_state in the database
        List<Voucher_state> voucher_states = voucher_stateRepository.findAll();
        assertThat(voucher_states).hasSize(databaseSizeBeforeUpdate);
        Voucher_state testVoucher_state = voucher_states.get(voucher_states.size() - 1);
        assertThat(testVoucher_state.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVoucher_state.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteVoucher_state() throws Exception {
        // Initialize the database
        voucher_stateService.save(voucher_state);

        int databaseSizeBeforeDelete = voucher_stateRepository.findAll().size();

        // Get the voucher_state
        restVoucher_stateMockMvc.perform(delete("/api/voucher-states/{id}", voucher_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Voucher_state> voucher_states = voucher_stateRepository.findAll();
        assertThat(voucher_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
