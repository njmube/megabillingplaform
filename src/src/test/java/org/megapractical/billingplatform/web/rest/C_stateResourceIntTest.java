package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_state;
import org.megapractical.billingplatform.repository.C_stateRepository;
import org.megapractical.billingplatform.service.C_stateService;

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
 * Test class for the C_stateResource REST controller.
 *
 * @see C_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_stateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_ABREV = "AAAAA";
    private static final String UPDATED_ABREV = "BBBBB";

    @Inject
    private C_stateRepository c_stateRepository;

    @Inject
    private C_stateService c_stateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_stateMockMvc;

    private C_state c_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_stateResource c_stateResource = new C_stateResource();
        ReflectionTestUtils.setField(c_stateResource, "c_stateService", c_stateService);
        this.restC_stateMockMvc = MockMvcBuilders.standaloneSetup(c_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_state = new C_state();
        c_state.setName(DEFAULT_NAME);
        c_state.setAbrev(DEFAULT_ABREV);
    }

    @Test
    @Transactional
    public void createC_state() throws Exception {
        int databaseSizeBeforeCreate = c_stateRepository.findAll().size();

        // Create the C_state

        restC_stateMockMvc.perform(post("/api/c-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_state)))
                .andExpect(status().isCreated());

        // Validate the C_state in the database
        List<C_state> c_states = c_stateRepository.findAll();
        assertThat(c_states).hasSize(databaseSizeBeforeCreate + 1);
        C_state testC_state = c_states.get(c_states.size() - 1);
        assertThat(testC_state.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_state.getAbrev()).isEqualTo(DEFAULT_ABREV);
    }

    @Test
    @Transactional
    public void getAllC_states() throws Exception {
        // Initialize the database
        c_stateRepository.saveAndFlush(c_state);

        // Get all the c_states
        restC_stateMockMvc.perform(get("/api/c-states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].abrev").value(hasItem(DEFAULT_ABREV.toString())));
    }

    @Test
    @Transactional
    public void getC_state() throws Exception {
        // Initialize the database
        c_stateRepository.saveAndFlush(c_state);

        // Get the c_state
        restC_stateMockMvc.perform(get("/api/c-states/{id}", c_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_state.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.abrev").value(DEFAULT_ABREV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_state() throws Exception {
        // Get the c_state
        restC_stateMockMvc.perform(get("/api/c-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_state() throws Exception {
        // Initialize the database
        c_stateService.save(c_state);

        int databaseSizeBeforeUpdate = c_stateRepository.findAll().size();

        // Update the c_state
        C_state updatedC_state = new C_state();
        updatedC_state.setId(c_state.getId());
        updatedC_state.setName(UPDATED_NAME);
        updatedC_state.setAbrev(UPDATED_ABREV);

        restC_stateMockMvc.perform(put("/api/c-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_state)))
                .andExpect(status().isOk());

        // Validate the C_state in the database
        List<C_state> c_states = c_stateRepository.findAll();
        assertThat(c_states).hasSize(databaseSizeBeforeUpdate);
        C_state testC_state = c_states.get(c_states.size() - 1);
        assertThat(testC_state.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_state.getAbrev()).isEqualTo(UPDATED_ABREV);
    }

    @Test
    @Transactional
    public void deleteC_state() throws Exception {
        // Initialize the database
        c_stateService.save(c_state);

        int databaseSizeBeforeDelete = c_stateRepository.findAll().size();

        // Get the c_state
        restC_stateMockMvc.perform(delete("/api/c-states/{id}", c_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_state> c_states = c_stateRepository.findAll();
        assertThat(c_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
