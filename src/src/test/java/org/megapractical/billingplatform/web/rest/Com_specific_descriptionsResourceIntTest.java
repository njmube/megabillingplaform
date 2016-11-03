package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_specific_descriptions;
import org.megapractical.billingplatform.repository.Com_specific_descriptionsRepository;
import org.megapractical.billingplatform.service.Com_specific_descriptionsService;

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
 * Test class for the Com_specific_descriptionsResource REST controller.
 *
 * @see Com_specific_descriptionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_specific_descriptionsResourceIntTest {

    private static final String DEFAULT_BRAND = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_MODEL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SUBMODEL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SUBMODEL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private Com_specific_descriptionsRepository com_specific_descriptionsRepository;

    @Inject
    private Com_specific_descriptionsService com_specific_descriptionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_specific_descriptionsMockMvc;

    private Com_specific_descriptions com_specific_descriptions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_specific_descriptionsResource com_specific_descriptionsResource = new Com_specific_descriptionsResource();
        ReflectionTestUtils.setField(com_specific_descriptionsResource, "com_specific_descriptionsService", com_specific_descriptionsService);
        this.restCom_specific_descriptionsMockMvc = MockMvcBuilders.standaloneSetup(com_specific_descriptionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_specific_descriptions = new Com_specific_descriptions();
        com_specific_descriptions.setBrand(DEFAULT_BRAND);
        com_specific_descriptions.setModel(DEFAULT_MODEL);
        com_specific_descriptions.setSubmodel(DEFAULT_SUBMODEL);
        com_specific_descriptions.setSerial_number(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void createCom_specific_descriptions() throws Exception {
        int databaseSizeBeforeCreate = com_specific_descriptionsRepository.findAll().size();

        // Create the Com_specific_descriptions

        restCom_specific_descriptionsMockMvc.perform(post("/api/com-specific-descriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_specific_descriptions)))
                .andExpect(status().isCreated());

        // Validate the Com_specific_descriptions in the database
        List<Com_specific_descriptions> com_specific_descriptions = com_specific_descriptionsRepository.findAll();
        assertThat(com_specific_descriptions).hasSize(databaseSizeBeforeCreate + 1);
        Com_specific_descriptions testCom_specific_descriptions = com_specific_descriptions.get(com_specific_descriptions.size() - 1);
        assertThat(testCom_specific_descriptions.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCom_specific_descriptions.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCom_specific_descriptions.getSubmodel()).isEqualTo(DEFAULT_SUBMODEL);
        assertThat(testCom_specific_descriptions.getSerial_number()).isEqualTo(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_specific_descriptionsRepository.findAll().size();
        // set the field null
        com_specific_descriptions.setBrand(null);

        // Create the Com_specific_descriptions, which fails.

        restCom_specific_descriptionsMockMvc.perform(post("/api/com-specific-descriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_specific_descriptions)))
                .andExpect(status().isBadRequest());

        List<Com_specific_descriptions> com_specific_descriptions = com_specific_descriptionsRepository.findAll();
        assertThat(com_specific_descriptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_specific_descriptions() throws Exception {
        // Initialize the database
        com_specific_descriptionsRepository.saveAndFlush(com_specific_descriptions);

        // Get all the com_specific_descriptions
        restCom_specific_descriptionsMockMvc.perform(get("/api/com-specific-descriptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_specific_descriptions.getId().intValue())))
                .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
                .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
                .andExpect(jsonPath("$.[*].submodel").value(hasItem(DEFAULT_SUBMODEL.toString())))
                .andExpect(jsonPath("$.[*].serial_number").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getCom_specific_descriptions() throws Exception {
        // Initialize the database
        com_specific_descriptionsRepository.saveAndFlush(com_specific_descriptions);

        // Get the com_specific_descriptions
        restCom_specific_descriptionsMockMvc.perform(get("/api/com-specific-descriptions/{id}", com_specific_descriptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_specific_descriptions.getId().intValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.submodel").value(DEFAULT_SUBMODEL.toString()))
            .andExpect(jsonPath("$.serial_number").value(DEFAULT_SERIAL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_specific_descriptions() throws Exception {
        // Get the com_specific_descriptions
        restCom_specific_descriptionsMockMvc.perform(get("/api/com-specific-descriptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_specific_descriptions() throws Exception {
        // Initialize the database
        com_specific_descriptionsService.save(com_specific_descriptions);

        int databaseSizeBeforeUpdate = com_specific_descriptionsRepository.findAll().size();

        // Update the com_specific_descriptions
        Com_specific_descriptions updatedCom_specific_descriptions = new Com_specific_descriptions();
        updatedCom_specific_descriptions.setId(com_specific_descriptions.getId());
        updatedCom_specific_descriptions.setBrand(UPDATED_BRAND);
        updatedCom_specific_descriptions.setModel(UPDATED_MODEL);
        updatedCom_specific_descriptions.setSubmodel(UPDATED_SUBMODEL);
        updatedCom_specific_descriptions.setSerial_number(UPDATED_SERIAL_NUMBER);

        restCom_specific_descriptionsMockMvc.perform(put("/api/com-specific-descriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_specific_descriptions)))
                .andExpect(status().isOk());

        // Validate the Com_specific_descriptions in the database
        List<Com_specific_descriptions> com_specific_descriptions = com_specific_descriptionsRepository.findAll();
        assertThat(com_specific_descriptions).hasSize(databaseSizeBeforeUpdate);
        Com_specific_descriptions testCom_specific_descriptions = com_specific_descriptions.get(com_specific_descriptions.size() - 1);
        assertThat(testCom_specific_descriptions.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCom_specific_descriptions.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCom_specific_descriptions.getSubmodel()).isEqualTo(UPDATED_SUBMODEL);
        assertThat(testCom_specific_descriptions.getSerial_number()).isEqualTo(UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void deleteCom_specific_descriptions() throws Exception {
        // Initialize the database
        com_specific_descriptionsService.save(com_specific_descriptions);

        int databaseSizeBeforeDelete = com_specific_descriptionsRepository.findAll().size();

        // Get the com_specific_descriptions
        restCom_specific_descriptionsMockMvc.perform(delete("/api/com-specific-descriptions/{id}", com_specific_descriptions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_specific_descriptions> com_specific_descriptions = com_specific_descriptionsRepository.findAll();
        assertThat(com_specific_descriptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
