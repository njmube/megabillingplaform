package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_accreditation_ieps;
import org.megapractical.billingplatform.repository.Com_accreditation_iepsRepository;
import org.megapractical.billingplatform.service.Com_accreditation_iepsService;

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
 * Test class for the Com_accreditation_iepsResource REST controller.
 *
 * @see Com_accreditation_iepsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_accreditation_iepsResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    @Inject
    private Com_accreditation_iepsRepository com_accreditation_iepsRepository;

    @Inject
    private Com_accreditation_iepsService com_accreditation_iepsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_accreditation_iepsMockMvc;

    private Com_accreditation_ieps com_accreditation_ieps;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_accreditation_iepsResource com_accreditation_iepsResource = new Com_accreditation_iepsResource();
        ReflectionTestUtils.setField(com_accreditation_iepsResource, "com_accreditation_iepsService", com_accreditation_iepsService);
        this.restCom_accreditation_iepsMockMvc = MockMvcBuilders.standaloneSetup(com_accreditation_iepsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_accreditation_ieps = new Com_accreditation_ieps();
        com_accreditation_ieps.setVersion(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createCom_accreditation_ieps() throws Exception {
        int databaseSizeBeforeCreate = com_accreditation_iepsRepository.findAll().size();

        // Create the Com_accreditation_ieps

        restCom_accreditation_iepsMockMvc.perform(post("/api/com-accreditation-ieps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_accreditation_ieps)))
                .andExpect(status().isCreated());

        // Validate the Com_accreditation_ieps in the database
        List<Com_accreditation_ieps> com_accreditation_ieps = com_accreditation_iepsRepository.findAll();
        assertThat(com_accreditation_ieps).hasSize(databaseSizeBeforeCreate + 1);
        Com_accreditation_ieps testCom_accreditation_ieps = com_accreditation_ieps.get(com_accreditation_ieps.size() - 1);
        assertThat(testCom_accreditation_ieps.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_accreditation_iepsRepository.findAll().size();
        // set the field null
        com_accreditation_ieps.setVersion(null);

        // Create the Com_accreditation_ieps, which fails.

        restCom_accreditation_iepsMockMvc.perform(post("/api/com-accreditation-ieps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_accreditation_ieps)))
                .andExpect(status().isBadRequest());

        List<Com_accreditation_ieps> com_accreditation_ieps = com_accreditation_iepsRepository.findAll();
        assertThat(com_accreditation_ieps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_accreditation_ieps() throws Exception {
        // Initialize the database
        com_accreditation_iepsRepository.saveAndFlush(com_accreditation_ieps);

        // Get all the com_accreditation_ieps
        restCom_accreditation_iepsMockMvc.perform(get("/api/com-accreditation-ieps?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_accreditation_ieps.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getCom_accreditation_ieps() throws Exception {
        // Initialize the database
        com_accreditation_iepsRepository.saveAndFlush(com_accreditation_ieps);

        // Get the com_accreditation_ieps
        restCom_accreditation_iepsMockMvc.perform(get("/api/com-accreditation-ieps/{id}", com_accreditation_ieps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_accreditation_ieps.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_accreditation_ieps() throws Exception {
        // Get the com_accreditation_ieps
        restCom_accreditation_iepsMockMvc.perform(get("/api/com-accreditation-ieps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_accreditation_ieps() throws Exception {
        // Initialize the database
        com_accreditation_iepsService.save(com_accreditation_ieps);

        int databaseSizeBeforeUpdate = com_accreditation_iepsRepository.findAll().size();

        // Update the com_accreditation_ieps
        Com_accreditation_ieps updatedCom_accreditation_ieps = new Com_accreditation_ieps();
        updatedCom_accreditation_ieps.setId(com_accreditation_ieps.getId());
        updatedCom_accreditation_ieps.setVersion(UPDATED_VERSION);

        restCom_accreditation_iepsMockMvc.perform(put("/api/com-accreditation-ieps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_accreditation_ieps)))
                .andExpect(status().isOk());

        // Validate the Com_accreditation_ieps in the database
        List<Com_accreditation_ieps> com_accreditation_ieps = com_accreditation_iepsRepository.findAll();
        assertThat(com_accreditation_ieps).hasSize(databaseSizeBeforeUpdate);
        Com_accreditation_ieps testCom_accreditation_ieps = com_accreditation_ieps.get(com_accreditation_ieps.size() - 1);
        assertThat(testCom_accreditation_ieps.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void deleteCom_accreditation_ieps() throws Exception {
        // Initialize the database
        com_accreditation_iepsService.save(com_accreditation_ieps);

        int databaseSizeBeforeDelete = com_accreditation_iepsRepository.findAll().size();

        // Get the com_accreditation_ieps
        restCom_accreditation_iepsMockMvc.perform(delete("/api/com-accreditation-ieps/{id}", com_accreditation_ieps.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_accreditation_ieps> com_accreditation_ieps = com_accreditation_iepsRepository.findAll();
        assertThat(com_accreditation_ieps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
