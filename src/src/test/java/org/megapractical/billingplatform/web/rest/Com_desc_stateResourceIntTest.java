package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_desc_state;
import org.megapractical.billingplatform.repository.Com_desc_stateRepository;
import org.megapractical.billingplatform.service.Com_desc_stateService;

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
 * Test class for the Com_desc_stateResource REST controller.
 *
 * @see Com_desc_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_desc_stateResourceIntTest {

    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_NOEXT = "AAAAA";
    private static final String UPDATED_NOEXT = "BBBBB";
    private static final String DEFAULT_NOINT = "AAAAA";
    private static final String UPDATED_NOINT = "BBBBB";
    private static final String DEFAULT_LOCALE = "AAAAA";
    private static final String UPDATED_LOCALE = "BBBBB";
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";

    @Inject
    private Com_desc_stateRepository com_desc_stateRepository;

    @Inject
    private Com_desc_stateService com_desc_stateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_desc_stateMockMvc;

    private Com_desc_state com_desc_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_desc_stateResource com_desc_stateResource = new Com_desc_stateResource();
        ReflectionTestUtils.setField(com_desc_stateResource, "com_desc_stateService", com_desc_stateService);
        this.restCom_desc_stateMockMvc = MockMvcBuilders.standaloneSetup(com_desc_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_desc_state = new Com_desc_state();
        com_desc_state.setStreet(DEFAULT_STREET);
        com_desc_state.setNoext(DEFAULT_NOEXT);
        com_desc_state.setNoint(DEFAULT_NOINT);
        com_desc_state.setLocale(DEFAULT_LOCALE);
        com_desc_state.setReference(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createCom_desc_state() throws Exception {
        int databaseSizeBeforeCreate = com_desc_stateRepository.findAll().size();

        // Create the Com_desc_state

        restCom_desc_stateMockMvc.perform(post("/api/com-desc-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_desc_state)))
                .andExpect(status().isCreated());

        // Validate the Com_desc_state in the database
        List<Com_desc_state> com_desc_states = com_desc_stateRepository.findAll();
        assertThat(com_desc_states).hasSize(databaseSizeBeforeCreate + 1);
        Com_desc_state testCom_desc_state = com_desc_states.get(com_desc_states.size() - 1);
        assertThat(testCom_desc_state.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCom_desc_state.getNoext()).isEqualTo(DEFAULT_NOEXT);
        assertThat(testCom_desc_state.getNoint()).isEqualTo(DEFAULT_NOINT);
        assertThat(testCom_desc_state.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testCom_desc_state.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_desc_stateRepository.findAll().size();
        // set the field null
        com_desc_state.setStreet(null);

        // Create the Com_desc_state, which fails.

        restCom_desc_stateMockMvc.perform(post("/api/com-desc-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_desc_state)))
                .andExpect(status().isBadRequest());

        List<Com_desc_state> com_desc_states = com_desc_stateRepository.findAll();
        assertThat(com_desc_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_desc_states() throws Exception {
        // Initialize the database
        com_desc_stateRepository.saveAndFlush(com_desc_state);

        // Get all the com_desc_states
        restCom_desc_stateMockMvc.perform(get("/api/com-desc-states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_desc_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].noext").value(hasItem(DEFAULT_NOEXT.toString())))
                .andExpect(jsonPath("$.[*].noint").value(hasItem(DEFAULT_NOINT.toString())))
                .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getCom_desc_state() throws Exception {
        // Initialize the database
        com_desc_stateRepository.saveAndFlush(com_desc_state);

        // Get the com_desc_state
        restCom_desc_stateMockMvc.perform(get("/api/com-desc-states/{id}", com_desc_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_desc_state.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.noext").value(DEFAULT_NOEXT.toString()))
            .andExpect(jsonPath("$.noint").value(DEFAULT_NOINT.toString()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_desc_state() throws Exception {
        // Get the com_desc_state
        restCom_desc_stateMockMvc.perform(get("/api/com-desc-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_desc_state() throws Exception {
        // Initialize the database
        com_desc_stateService.save(com_desc_state);

        int databaseSizeBeforeUpdate = com_desc_stateRepository.findAll().size();

        // Update the com_desc_state
        Com_desc_state updatedCom_desc_state = new Com_desc_state();
        updatedCom_desc_state.setId(com_desc_state.getId());
        updatedCom_desc_state.setStreet(UPDATED_STREET);
        updatedCom_desc_state.setNoext(UPDATED_NOEXT);
        updatedCom_desc_state.setNoint(UPDATED_NOINT);
        updatedCom_desc_state.setLocale(UPDATED_LOCALE);
        updatedCom_desc_state.setReference(UPDATED_REFERENCE);

        restCom_desc_stateMockMvc.perform(put("/api/com-desc-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_desc_state)))
                .andExpect(status().isOk());

        // Validate the Com_desc_state in the database
        List<Com_desc_state> com_desc_states = com_desc_stateRepository.findAll();
        assertThat(com_desc_states).hasSize(databaseSizeBeforeUpdate);
        Com_desc_state testCom_desc_state = com_desc_states.get(com_desc_states.size() - 1);
        assertThat(testCom_desc_state.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCom_desc_state.getNoext()).isEqualTo(UPDATED_NOEXT);
        assertThat(testCom_desc_state.getNoint()).isEqualTo(UPDATED_NOINT);
        assertThat(testCom_desc_state.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testCom_desc_state.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void deleteCom_desc_state() throws Exception {
        // Initialize the database
        com_desc_stateService.save(com_desc_state);

        int databaseSizeBeforeDelete = com_desc_stateRepository.findAll().size();

        // Get the com_desc_state
        restCom_desc_stateMockMvc.perform(delete("/api/com-desc-states/{id}", com_desc_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_desc_state> com_desc_states = com_desc_stateRepository.findAll();
        assertThat(com_desc_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
