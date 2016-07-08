package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Request_state;
import org.megapractical.billingplatform.repository.Request_stateRepository;
import org.megapractical.billingplatform.service.Request_stateService;

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
 * Test class for the Request_stateResource REST controller.
 *
 * @see Request_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Request_stateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Request_stateRepository request_stateRepository;

    @Inject
    private Request_stateService request_stateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRequest_stateMockMvc;

    private Request_state request_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Request_stateResource request_stateResource = new Request_stateResource();
        ReflectionTestUtils.setField(request_stateResource, "request_stateService", request_stateService);
        this.restRequest_stateMockMvc = MockMvcBuilders.standaloneSetup(request_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        request_state = new Request_state();
        request_state.setName(DEFAULT_NAME);
        request_state.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRequest_state() throws Exception {
        int databaseSizeBeforeCreate = request_stateRepository.findAll().size();

        // Create the Request_state

        restRequest_stateMockMvc.perform(post("/api/request-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request_state)))
                .andExpect(status().isCreated());

        // Validate the Request_state in the database
        List<Request_state> request_states = request_stateRepository.findAll();
        assertThat(request_states).hasSize(databaseSizeBeforeCreate + 1);
        Request_state testRequest_state = request_states.get(request_states.size() - 1);
        assertThat(testRequest_state.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRequest_state.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRequest_states() throws Exception {
        // Initialize the database
        request_stateRepository.saveAndFlush(request_state);

        // Get all the request_states
        restRequest_stateMockMvc.perform(get("/api/request-states?sort=id,desc&filtername= "))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(request_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRequest_state() throws Exception {
        // Initialize the database
        request_stateRepository.saveAndFlush(request_state);

        // Get the request_state
        restRequest_stateMockMvc.perform(get("/api/request-states/{id}", request_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(request_state.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequest_state() throws Exception {
        // Get the request_state
        restRequest_stateMockMvc.perform(get("/api/request-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequest_state() throws Exception {
        // Initialize the database
        request_stateService.save(request_state);

        int databaseSizeBeforeUpdate = request_stateRepository.findAll().size();

        // Update the request_state
        Request_state updatedRequest_state = new Request_state();
        updatedRequest_state.setId(request_state.getId());
        updatedRequest_state.setName(UPDATED_NAME);
        updatedRequest_state.setDescription(UPDATED_DESCRIPTION);

        restRequest_stateMockMvc.perform(put("/api/request-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRequest_state)))
                .andExpect(status().isOk());

        // Validate the Request_state in the database
        List<Request_state> request_states = request_stateRepository.findAll();
        assertThat(request_states).hasSize(databaseSizeBeforeUpdate);
        Request_state testRequest_state = request_states.get(request_states.size() - 1);
        assertThat(testRequest_state.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequest_state.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteRequest_state() throws Exception {
        // Initialize the database
        request_stateService.save(request_state);

        int databaseSizeBeforeDelete = request_stateRepository.findAll().size();

        // Get the request_state
        restRequest_stateMockMvc.perform(delete("/api/request-states/{id}", request_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Request_state> request_states = request_stateRepository.findAll();
        assertThat(request_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
