package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_acquiring_data;
import org.megapractical.billingplatform.repository.Com_acquiring_dataRepository;
import org.megapractical.billingplatform.service.Com_acquiring_dataService;

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
 * Test class for the Com_acquiring_dataResource REST controller.
 *
 * @see Com_acquiring_dataResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_acquiring_dataResourceIntTest {

    private static final String DEFAULT_COPROSOCCONYUGAIE = "AAAAAAAAAA";
    private static final String UPDATED_COPROSOCCONYUGAIE = "BBBBBBBBBB";

    @Inject
    private Com_acquiring_dataRepository com_acquiring_dataRepository;

    @Inject
    private Com_acquiring_dataService com_acquiring_dataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_acquiring_dataMockMvc;

    private Com_acquiring_data com_acquiring_data;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_acquiring_dataResource com_acquiring_dataResource = new Com_acquiring_dataResource();
        ReflectionTestUtils.setField(com_acquiring_dataResource, "com_acquiring_dataService", com_acquiring_dataService);
        this.restCom_acquiring_dataMockMvc = MockMvcBuilders.standaloneSetup(com_acquiring_dataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_acquiring_data = new Com_acquiring_data();
        com_acquiring_data.setCoprosocconyugaie(DEFAULT_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void createCom_acquiring_data() throws Exception {
        int databaseSizeBeforeCreate = com_acquiring_dataRepository.findAll().size();

        // Create the Com_acquiring_data

        restCom_acquiring_dataMockMvc.perform(post("/api/com-acquiring-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_acquiring_data)))
                .andExpect(status().isCreated());

        // Validate the Com_acquiring_data in the database
        List<Com_acquiring_data> com_acquiring_data = com_acquiring_dataRepository.findAll();
        assertThat(com_acquiring_data).hasSize(databaseSizeBeforeCreate + 1);
        Com_acquiring_data testCom_acquiring_data = com_acquiring_data.get(com_acquiring_data.size() - 1);
        assertThat(testCom_acquiring_data.getCoprosocconyugaie()).isEqualTo(DEFAULT_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void checkCoprosocconyugaieIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_acquiring_dataRepository.findAll().size();
        // set the field null
        com_acquiring_data.setCoprosocconyugaie(null);

        // Create the Com_acquiring_data, which fails.

        restCom_acquiring_dataMockMvc.perform(post("/api/com-acquiring-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_acquiring_data)))
                .andExpect(status().isBadRequest());

        List<Com_acquiring_data> com_acquiring_data = com_acquiring_dataRepository.findAll();
        assertThat(com_acquiring_data).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_acquiring_data() throws Exception {
        // Initialize the database
        com_acquiring_dataRepository.saveAndFlush(com_acquiring_data);

        // Get all the com_acquiring_data
        restCom_acquiring_dataMockMvc.perform(get("/api/com-acquiring-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_acquiring_data.getId().intValue())))
                .andExpect(jsonPath("$.[*].coprosocconyugaie").value(hasItem(DEFAULT_COPROSOCCONYUGAIE.toString())));
    }

    @Test
    @Transactional
    public void getCom_acquiring_data() throws Exception {
        // Initialize the database
        com_acquiring_dataRepository.saveAndFlush(com_acquiring_data);

        // Get the com_acquiring_data
        restCom_acquiring_dataMockMvc.perform(get("/api/com-acquiring-data/{id}", com_acquiring_data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_acquiring_data.getId().intValue()))
            .andExpect(jsonPath("$.coprosocconyugaie").value(DEFAULT_COPROSOCCONYUGAIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_acquiring_data() throws Exception {
        // Get the com_acquiring_data
        restCom_acquiring_dataMockMvc.perform(get("/api/com-acquiring-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_acquiring_data() throws Exception {
        // Initialize the database
        com_acquiring_dataService.save(com_acquiring_data);

        int databaseSizeBeforeUpdate = com_acquiring_dataRepository.findAll().size();

        // Update the com_acquiring_data
        Com_acquiring_data updatedCom_acquiring_data = new Com_acquiring_data();
        updatedCom_acquiring_data.setId(com_acquiring_data.getId());
        updatedCom_acquiring_data.setCoprosocconyugaie(UPDATED_COPROSOCCONYUGAIE);

        restCom_acquiring_dataMockMvc.perform(put("/api/com-acquiring-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_acquiring_data)))
                .andExpect(status().isOk());

        // Validate the Com_acquiring_data in the database
        List<Com_acquiring_data> com_acquiring_data = com_acquiring_dataRepository.findAll();
        assertThat(com_acquiring_data).hasSize(databaseSizeBeforeUpdate);
        Com_acquiring_data testCom_acquiring_data = com_acquiring_data.get(com_acquiring_data.size() - 1);
        assertThat(testCom_acquiring_data.getCoprosocconyugaie()).isEqualTo(UPDATED_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void deleteCom_acquiring_data() throws Exception {
        // Initialize the database
        com_acquiring_dataService.save(com_acquiring_data);

        int databaseSizeBeforeDelete = com_acquiring_dataRepository.findAll().size();

        // Get the com_acquiring_data
        restCom_acquiring_dataMockMvc.perform(delete("/api/com-acquiring-data/{id}", com_acquiring_data.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_acquiring_data> com_acquiring_data = com_acquiring_dataRepository.findAll();
        assertThat(com_acquiring_data).hasSize(databaseSizeBeforeDelete - 1);
    }
}
