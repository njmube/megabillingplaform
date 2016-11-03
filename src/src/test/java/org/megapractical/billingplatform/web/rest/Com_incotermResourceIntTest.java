package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_incoterm;
import org.megapractical.billingplatform.repository.Com_incotermRepository;
import org.megapractical.billingplatform.service.Com_incotermService;

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
 * Test class for the Com_incotermResource REST controller.
 *
 * @see Com_incotermResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_incotermResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private Com_incotermRepository com_incotermRepository;

    @Inject
    private Com_incotermService com_incotermService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_incotermMockMvc;

    private Com_incoterm com_incoterm;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_incotermResource com_incotermResource = new Com_incotermResource();
        ReflectionTestUtils.setField(com_incotermResource, "com_incotermService", com_incotermService);
        this.restCom_incotermMockMvc = MockMvcBuilders.standaloneSetup(com_incotermResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_incoterm = new Com_incoterm();
        com_incoterm.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCom_incoterm() throws Exception {
        int databaseSizeBeforeCreate = com_incotermRepository.findAll().size();

        // Create the Com_incoterm

        restCom_incotermMockMvc.perform(post("/api/com-incoterms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_incoterm)))
                .andExpect(status().isCreated());

        // Validate the Com_incoterm in the database
        List<Com_incoterm> com_incoterms = com_incotermRepository.findAll();
        assertThat(com_incoterms).hasSize(databaseSizeBeforeCreate + 1);
        Com_incoterm testCom_incoterm = com_incoterms.get(com_incoterms.size() - 1);
        assertThat(testCom_incoterm.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_incotermRepository.findAll().size();
        // set the field null
        com_incoterm.setValue(null);

        // Create the Com_incoterm, which fails.

        restCom_incotermMockMvc.perform(post("/api/com-incoterms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_incoterm)))
                .andExpect(status().isBadRequest());

        List<Com_incoterm> com_incoterms = com_incotermRepository.findAll();
        assertThat(com_incoterms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_incoterms() throws Exception {
        // Initialize the database
        com_incotermRepository.saveAndFlush(com_incoterm);

        // Get all the com_incoterms
        restCom_incotermMockMvc.perform(get("/api/com-incoterms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_incoterm.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCom_incoterm() throws Exception {
        // Initialize the database
        com_incotermRepository.saveAndFlush(com_incoterm);

        // Get the com_incoterm
        restCom_incotermMockMvc.perform(get("/api/com-incoterms/{id}", com_incoterm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_incoterm.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_incoterm() throws Exception {
        // Get the com_incoterm
        restCom_incotermMockMvc.perform(get("/api/com-incoterms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_incoterm() throws Exception {
        // Initialize the database
        com_incotermService.save(com_incoterm);

        int databaseSizeBeforeUpdate = com_incotermRepository.findAll().size();

        // Update the com_incoterm
        Com_incoterm updatedCom_incoterm = new Com_incoterm();
        updatedCom_incoterm.setId(com_incoterm.getId());
        updatedCom_incoterm.setValue(UPDATED_VALUE);

        restCom_incotermMockMvc.perform(put("/api/com-incoterms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_incoterm)))
                .andExpect(status().isOk());

        // Validate the Com_incoterm in the database
        List<Com_incoterm> com_incoterms = com_incotermRepository.findAll();
        assertThat(com_incoterms).hasSize(databaseSizeBeforeUpdate);
        Com_incoterm testCom_incoterm = com_incoterms.get(com_incoterms.size() - 1);
        assertThat(testCom_incoterm.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteCom_incoterm() throws Exception {
        // Initialize the database
        com_incotermService.save(com_incoterm);

        int databaseSizeBeforeDelete = com_incotermRepository.findAll().size();

        // Get the com_incoterm
        restCom_incotermMockMvc.perform(delete("/api/com-incoterms/{id}", com_incoterm.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_incoterm> com_incoterms = com_incotermRepository.findAll();
        assertThat(com_incoterms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
