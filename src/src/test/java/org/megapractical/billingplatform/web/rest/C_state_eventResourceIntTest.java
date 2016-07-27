package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.repository.C_state_eventRepository;
import org.megapractical.billingplatform.service.C_state_eventService;

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
 * Test class for the C_state_eventResource REST controller.
 *
 * @see C_state_eventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_state_eventResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private C_state_eventRepository c_state_eventRepository;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_state_eventMockMvc;

    private C_state_event c_state_event;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_state_eventResource c_state_eventResource = new C_state_eventResource();
        ReflectionTestUtils.setField(c_state_eventResource, "c_state_eventService", c_state_eventService);
        this.restC_state_eventMockMvc = MockMvcBuilders.standaloneSetup(c_state_eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_state_event = new C_state_event();
        c_state_event.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createC_state_event() throws Exception {
        int databaseSizeBeforeCreate = c_state_eventRepository.findAll().size();

        // Create the C_state_event

        restC_state_eventMockMvc.perform(post("/api/c-state-events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_state_event)))
                .andExpect(status().isCreated());

        // Validate the C_state_event in the database
        List<C_state_event> c_state_events = c_state_eventRepository.findAll();
        assertThat(c_state_events).hasSize(databaseSizeBeforeCreate + 1);
        C_state_event testC_state_event = c_state_events.get(c_state_events.size() - 1);
        assertThat(testC_state_event.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_state_eventRepository.findAll().size();
        // set the field null
        c_state_event.setName(null);

        // Create the C_state_event, which fails.

        restC_state_eventMockMvc.perform(post("/api/c-state-events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_state_event)))
                .andExpect(status().isBadRequest());

        List<C_state_event> c_state_events = c_state_eventRepository.findAll();
        assertThat(c_state_events).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_state_events() throws Exception {
        // Initialize the database
        c_state_eventRepository.saveAndFlush(c_state_event);

        // Get all the c_state_events
        restC_state_eventMockMvc.perform(get("/api/c-state-events?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_state_event.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getC_state_event() throws Exception {
        // Initialize the database
        c_state_eventRepository.saveAndFlush(c_state_event);

        // Get the c_state_event
        restC_state_eventMockMvc.perform(get("/api/c-state-events/{id}", c_state_event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_state_event.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_state_event() throws Exception {
        // Get the c_state_event
        restC_state_eventMockMvc.perform(get("/api/c-state-events/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_state_event() throws Exception {
        // Initialize the database
        c_state_eventService.save(c_state_event);

        int databaseSizeBeforeUpdate = c_state_eventRepository.findAll().size();

        // Update the c_state_event
        C_state_event updatedC_state_event = new C_state_event();
        updatedC_state_event.setId(c_state_event.getId());
        updatedC_state_event.setName(UPDATED_NAME);

        restC_state_eventMockMvc.perform(put("/api/c-state-events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_state_event)))
                .andExpect(status().isOk());

        // Validate the C_state_event in the database
        List<C_state_event> c_state_events = c_state_eventRepository.findAll();
        assertThat(c_state_events).hasSize(databaseSizeBeforeUpdate);
        C_state_event testC_state_event = c_state_events.get(c_state_events.size() - 1);
        assertThat(testC_state_event.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteC_state_event() throws Exception {
        // Initialize the database
        c_state_eventService.save(c_state_event);

        int databaseSizeBeforeDelete = c_state_eventRepository.findAll().size();

        // Get the c_state_event
        restC_state_eventMockMvc.perform(delete("/api/c-state-events/{id}", c_state_event.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_state_event> c_state_events = c_state_eventRepository.findAll();
        assertThat(c_state_events).hasSize(databaseSizeBeforeDelete - 1);
    }
}
