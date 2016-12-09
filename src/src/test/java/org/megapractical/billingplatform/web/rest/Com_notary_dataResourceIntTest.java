package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_notary_data;
import org.megapractical.billingplatform.repository.Com_notary_dataRepository;
import org.megapractical.billingplatform.service.Com_notary_dataService;

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
 * Test class for the Com_notary_dataResource REST controller.
 *
 * @see Com_notary_dataResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_notary_dataResourceIntTest {

    private static final String DEFAULT_CURP = "AAAAA";
    private static final String UPDATED_CURP = "BBBBB";

    private static final Integer DEFAULT_NOTARYNUMBER = 1;
    private static final Integer UPDATED_NOTARYNUMBER = 2;
    private static final String DEFAULT_ASCRIPTION = "AAAAA";
    private static final String UPDATED_ASCRIPTION = "BBBBB";

    @Inject
    private Com_notary_dataRepository com_notary_dataRepository;

    @Inject
    private Com_notary_dataService com_notary_dataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_notary_dataMockMvc;

    private Com_notary_data com_notary_data;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_notary_dataResource com_notary_dataResource = new Com_notary_dataResource();
        ReflectionTestUtils.setField(com_notary_dataResource, "com_notary_dataService", com_notary_dataService);
        this.restCom_notary_dataMockMvc = MockMvcBuilders.standaloneSetup(com_notary_dataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_notary_data = new Com_notary_data();
        com_notary_data.setCurp(DEFAULT_CURP);
        com_notary_data.setNotarynumber(DEFAULT_NOTARYNUMBER);
        com_notary_data.setAscription(DEFAULT_ASCRIPTION);
    }

    @Test
    @Transactional
    public void createCom_notary_data() throws Exception {
        int databaseSizeBeforeCreate = com_notary_dataRepository.findAll().size();

        // Create the Com_notary_data

        restCom_notary_dataMockMvc.perform(post("/api/com-notary-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_notary_data)))
                .andExpect(status().isCreated());

        // Validate the Com_notary_data in the database
        List<Com_notary_data> com_notary_data = com_notary_dataRepository.findAll();
        assertThat(com_notary_data).hasSize(databaseSizeBeforeCreate + 1);
        Com_notary_data testCom_notary_data = com_notary_data.get(com_notary_data.size() - 1);
        assertThat(testCom_notary_data.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testCom_notary_data.getNotarynumber()).isEqualTo(DEFAULT_NOTARYNUMBER);
        assertThat(testCom_notary_data.getAscription()).isEqualTo(DEFAULT_ASCRIPTION);
    }

    @Test
    @Transactional
    public void checkCurpIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_notary_dataRepository.findAll().size();
        // set the field null
        com_notary_data.setCurp(null);

        // Create the Com_notary_data, which fails.

        restCom_notary_dataMockMvc.perform(post("/api/com-notary-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_notary_data)))
                .andExpect(status().isBadRequest());

        List<Com_notary_data> com_notary_data = com_notary_dataRepository.findAll();
        assertThat(com_notary_data).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotarynumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_notary_dataRepository.findAll().size();
        // set the field null
        com_notary_data.setNotarynumber(null);

        // Create the Com_notary_data, which fails.

        restCom_notary_dataMockMvc.perform(post("/api/com-notary-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_notary_data)))
                .andExpect(status().isBadRequest());

        List<Com_notary_data> com_notary_data = com_notary_dataRepository.findAll();
        assertThat(com_notary_data).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_notary_data() throws Exception {
        // Initialize the database
        com_notary_dataRepository.saveAndFlush(com_notary_data);

        // Get all the com_notary_data
        restCom_notary_dataMockMvc.perform(get("/api/com-notary-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_notary_data.getId().intValue())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].notarynumber").value(hasItem(DEFAULT_NOTARYNUMBER)))
                .andExpect(jsonPath("$.[*].ascription").value(hasItem(DEFAULT_ASCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCom_notary_data() throws Exception {
        // Initialize the database
        com_notary_dataRepository.saveAndFlush(com_notary_data);

        // Get the com_notary_data
        restCom_notary_dataMockMvc.perform(get("/api/com-notary-data/{id}", com_notary_data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_notary_data.getId().intValue()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.notarynumber").value(DEFAULT_NOTARYNUMBER))
            .andExpect(jsonPath("$.ascription").value(DEFAULT_ASCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_notary_data() throws Exception {
        // Get the com_notary_data
        restCom_notary_dataMockMvc.perform(get("/api/com-notary-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_notary_data() throws Exception {
        // Initialize the database
        com_notary_dataService.save(com_notary_data);

        int databaseSizeBeforeUpdate = com_notary_dataRepository.findAll().size();

        // Update the com_notary_data
        Com_notary_data updatedCom_notary_data = new Com_notary_data();
        updatedCom_notary_data.setId(com_notary_data.getId());
        updatedCom_notary_data.setCurp(UPDATED_CURP);
        updatedCom_notary_data.setNotarynumber(UPDATED_NOTARYNUMBER);
        updatedCom_notary_data.setAscription(UPDATED_ASCRIPTION);

        restCom_notary_dataMockMvc.perform(put("/api/com-notary-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_notary_data)))
                .andExpect(status().isOk());

        // Validate the Com_notary_data in the database
        List<Com_notary_data> com_notary_data = com_notary_dataRepository.findAll();
        assertThat(com_notary_data).hasSize(databaseSizeBeforeUpdate);
        Com_notary_data testCom_notary_data = com_notary_data.get(com_notary_data.size() - 1);
        assertThat(testCom_notary_data.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testCom_notary_data.getNotarynumber()).isEqualTo(UPDATED_NOTARYNUMBER);
        assertThat(testCom_notary_data.getAscription()).isEqualTo(UPDATED_ASCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCom_notary_data() throws Exception {
        // Initialize the database
        com_notary_dataService.save(com_notary_data);

        int databaseSizeBeforeDelete = com_notary_dataRepository.findAll().size();

        // Get the com_notary_data
        restCom_notary_dataMockMvc.perform(delete("/api/com-notary-data/{id}", com_notary_data.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_notary_data> com_notary_data = com_notary_dataRepository.findAll();
        assertThat(com_notary_data).hasSize(databaseSizeBeforeDelete - 1);
    }
}
