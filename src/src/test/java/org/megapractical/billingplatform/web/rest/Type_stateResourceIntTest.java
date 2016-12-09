package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Type_state;
import org.megapractical.billingplatform.repository.Type_stateRepository;
import org.megapractical.billingplatform.service.Type_stateService;

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
 * Test class for the Type_stateResource REST controller.
 *
 * @see Type_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Type_stateResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Type_stateRepository type_stateRepository;

    @Inject
    private Type_stateService type_stateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restType_stateMockMvc;

    private Type_state type_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Type_stateResource type_stateResource = new Type_stateResource();
        ReflectionTestUtils.setField(type_stateResource, "type_stateService", type_stateService);
        this.restType_stateMockMvc = MockMvcBuilders.standaloneSetup(type_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        type_state = new Type_state();
        type_state.setCode(DEFAULT_CODE);
        type_state.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createType_state() throws Exception {
        int databaseSizeBeforeCreate = type_stateRepository.findAll().size();

        // Create the Type_state

        restType_stateMockMvc.perform(post("/api/type-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_state)))
                .andExpect(status().isCreated());

        // Validate the Type_state in the database
        List<Type_state> type_states = type_stateRepository.findAll();
        assertThat(type_states).hasSize(databaseSizeBeforeCreate + 1);
        Type_state testType_state = type_states.get(type_states.size() - 1);
        assertThat(testType_state.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testType_state.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = type_stateRepository.findAll().size();
        // set the field null
        type_state.setCode(null);

        // Create the Type_state, which fails.

        restType_stateMockMvc.perform(post("/api/type-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_state)))
                .andExpect(status().isBadRequest());

        List<Type_state> type_states = type_stateRepository.findAll();
        assertThat(type_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllType_states() throws Exception {
        // Initialize the database
        type_stateRepository.saveAndFlush(type_state);

        // Get all the type_states
        restType_stateMockMvc.perform(get("/api/type-states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(type_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getType_state() throws Exception {
        // Initialize the database
        type_stateRepository.saveAndFlush(type_state);

        // Get the type_state
        restType_stateMockMvc.perform(get("/api/type-states/{id}", type_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(type_state.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingType_state() throws Exception {
        // Get the type_state
        restType_stateMockMvc.perform(get("/api/type-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType_state() throws Exception {
        // Initialize the database
        type_stateService.save(type_state);

        int databaseSizeBeforeUpdate = type_stateRepository.findAll().size();

        // Update the type_state
        Type_state updatedType_state = new Type_state();
        updatedType_state.setId(type_state.getId());
        updatedType_state.setCode(UPDATED_CODE);
        updatedType_state.setDescription(UPDATED_DESCRIPTION);

        restType_stateMockMvc.perform(put("/api/type-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedType_state)))
                .andExpect(status().isOk());

        // Validate the Type_state in the database
        List<Type_state> type_states = type_stateRepository.findAll();
        assertThat(type_states).hasSize(databaseSizeBeforeUpdate);
        Type_state testType_state = type_states.get(type_states.size() - 1);
        assertThat(testType_state.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testType_state.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteType_state() throws Exception {
        // Initialize the database
        type_stateService.save(type_state);

        int databaseSizeBeforeDelete = type_stateRepository.findAll().size();

        // Get the type_state
        restType_stateMockMvc.perform(delete("/api/type-states/{id}", type_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Type_state> type_states = type_stateRepository.findAll();
        assertThat(type_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
