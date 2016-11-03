package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_taxlegends;
import org.megapractical.billingplatform.repository.Com_taxlegendsRepository;
import org.megapractical.billingplatform.service.Com_taxlegendsService;

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
 * Test class for the Com_taxlegendsResource REST controller.
 *
 * @see Com_taxlegendsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_taxlegendsResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    @Inject
    private Com_taxlegendsRepository com_taxlegendsRepository;

    @Inject
    private Com_taxlegendsService com_taxlegendsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_taxlegendsMockMvc;

    private Com_taxlegends com_taxlegends;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_taxlegendsResource com_taxlegendsResource = new Com_taxlegendsResource();
        ReflectionTestUtils.setField(com_taxlegendsResource, "com_taxlegendsService", com_taxlegendsService);
        this.restCom_taxlegendsMockMvc = MockMvcBuilders.standaloneSetup(com_taxlegendsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_taxlegends = new Com_taxlegends();
        com_taxlegends.setVersion(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createCom_taxlegends() throws Exception {
        int databaseSizeBeforeCreate = com_taxlegendsRepository.findAll().size();

        // Create the Com_taxlegends

        restCom_taxlegendsMockMvc.perform(post("/api/com-taxlegends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_taxlegends)))
                .andExpect(status().isCreated());

        // Validate the Com_taxlegends in the database
        List<Com_taxlegends> com_taxlegends = com_taxlegendsRepository.findAll();
        assertThat(com_taxlegends).hasSize(databaseSizeBeforeCreate + 1);
        Com_taxlegends testCom_taxlegends = com_taxlegends.get(com_taxlegends.size() - 1);
        assertThat(testCom_taxlegends.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_taxlegendsRepository.findAll().size();
        // set the field null
        com_taxlegends.setVersion(null);

        // Create the Com_taxlegends, which fails.

        restCom_taxlegendsMockMvc.perform(post("/api/com-taxlegends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_taxlegends)))
                .andExpect(status().isBadRequest());

        List<Com_taxlegends> com_taxlegends = com_taxlegendsRepository.findAll();
        assertThat(com_taxlegends).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_taxlegends() throws Exception {
        // Initialize the database
        com_taxlegendsRepository.saveAndFlush(com_taxlegends);

        // Get all the com_taxlegends
        restCom_taxlegendsMockMvc.perform(get("/api/com-taxlegends?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_taxlegends.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getCom_taxlegends() throws Exception {
        // Initialize the database
        com_taxlegendsRepository.saveAndFlush(com_taxlegends);

        // Get the com_taxlegends
        restCom_taxlegendsMockMvc.perform(get("/api/com-taxlegends/{id}", com_taxlegends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_taxlegends.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_taxlegends() throws Exception {
        // Get the com_taxlegends
        restCom_taxlegendsMockMvc.perform(get("/api/com-taxlegends/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_taxlegends() throws Exception {
        // Initialize the database
        com_taxlegendsService.save(com_taxlegends);

        int databaseSizeBeforeUpdate = com_taxlegendsRepository.findAll().size();

        // Update the com_taxlegends
        Com_taxlegends updatedCom_taxlegends = new Com_taxlegends();
        updatedCom_taxlegends.setId(com_taxlegends.getId());
        updatedCom_taxlegends.setVersion(UPDATED_VERSION);

        restCom_taxlegendsMockMvc.perform(put("/api/com-taxlegends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_taxlegends)))
                .andExpect(status().isOk());

        // Validate the Com_taxlegends in the database
        List<Com_taxlegends> com_taxlegends = com_taxlegendsRepository.findAll();
        assertThat(com_taxlegends).hasSize(databaseSizeBeforeUpdate);
        Com_taxlegends testCom_taxlegends = com_taxlegends.get(com_taxlegends.size() - 1);
        assertThat(testCom_taxlegends.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void deleteCom_taxlegends() throws Exception {
        // Initialize the database
        com_taxlegendsService.save(com_taxlegends);

        int databaseSizeBeforeDelete = com_taxlegendsRepository.findAll().size();

        // Get the com_taxlegends
        restCom_taxlegendsMockMvc.perform(delete("/api/com-taxlegends/{id}", com_taxlegends.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_taxlegends> com_taxlegends = com_taxlegendsRepository.findAll();
        assertThat(com_taxlegends).hasSize(databaseSizeBeforeDelete - 1);
    }
}
