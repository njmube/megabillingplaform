package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_public_notaries;
import org.megapractical.billingplatform.repository.Com_public_notariesRepository;
import org.megapractical.billingplatform.service.Com_public_notariesService;

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
 * Test class for the Com_public_notariesResource REST controller.
 *
 * @see Com_public_notariesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_public_notariesResourceIntTest {

    private static final String DEFAULT_VERSION = "AAA";
    private static final String UPDATED_VERSION = "BBB";

    @Inject
    private Com_public_notariesRepository com_public_notariesRepository;

    @Inject
    private Com_public_notariesService com_public_notariesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_public_notariesMockMvc;

    private Com_public_notaries com_public_notaries;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_public_notariesResource com_public_notariesResource = new Com_public_notariesResource();
        ReflectionTestUtils.setField(com_public_notariesResource, "com_public_notariesService", com_public_notariesService);
        this.restCom_public_notariesMockMvc = MockMvcBuilders.standaloneSetup(com_public_notariesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_public_notaries = new Com_public_notaries();
        com_public_notaries.setVersion(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createCom_public_notaries() throws Exception {
        int databaseSizeBeforeCreate = com_public_notariesRepository.findAll().size();

        // Create the Com_public_notaries

        restCom_public_notariesMockMvc.perform(post("/api/com-public-notaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_public_notaries)))
                .andExpect(status().isCreated());

        // Validate the Com_public_notaries in the database
        List<Com_public_notaries> com_public_notaries = com_public_notariesRepository.findAll();
        assertThat(com_public_notaries).hasSize(databaseSizeBeforeCreate + 1);
        Com_public_notaries testCom_public_notaries = com_public_notaries.get(com_public_notaries.size() - 1);
        assertThat(testCom_public_notaries.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_public_notariesRepository.findAll().size();
        // set the field null
        com_public_notaries.setVersion(null);

        // Create the Com_public_notaries, which fails.

        restCom_public_notariesMockMvc.perform(post("/api/com-public-notaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_public_notaries)))
                .andExpect(status().isBadRequest());

        List<Com_public_notaries> com_public_notaries = com_public_notariesRepository.findAll();
        assertThat(com_public_notaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_public_notaries() throws Exception {
        // Initialize the database
        com_public_notariesRepository.saveAndFlush(com_public_notaries);

        // Get all the com_public_notaries
        restCom_public_notariesMockMvc.perform(get("/api/com-public-notaries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_public_notaries.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getCom_public_notaries() throws Exception {
        // Initialize the database
        com_public_notariesRepository.saveAndFlush(com_public_notaries);

        // Get the com_public_notaries
        restCom_public_notariesMockMvc.perform(get("/api/com-public-notaries/{id}", com_public_notaries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_public_notaries.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_public_notaries() throws Exception {
        // Get the com_public_notaries
        restCom_public_notariesMockMvc.perform(get("/api/com-public-notaries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_public_notaries() throws Exception {
        // Initialize the database
        com_public_notariesService.save(com_public_notaries);

        int databaseSizeBeforeUpdate = com_public_notariesRepository.findAll().size();

        // Update the com_public_notaries
        Com_public_notaries updatedCom_public_notaries = new Com_public_notaries();
        updatedCom_public_notaries.setId(com_public_notaries.getId());
        updatedCom_public_notaries.setVersion(UPDATED_VERSION);

        restCom_public_notariesMockMvc.perform(put("/api/com-public-notaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_public_notaries)))
                .andExpect(status().isOk());

        // Validate the Com_public_notaries in the database
        List<Com_public_notaries> com_public_notaries = com_public_notariesRepository.findAll();
        assertThat(com_public_notaries).hasSize(databaseSizeBeforeUpdate);
        Com_public_notaries testCom_public_notaries = com_public_notaries.get(com_public_notaries.size() - 1);
        assertThat(testCom_public_notaries.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void deleteCom_public_notaries() throws Exception {
        // Initialize the database
        com_public_notariesService.save(com_public_notaries);

        int databaseSizeBeforeDelete = com_public_notariesRepository.findAll().size();

        // Get the com_public_notaries
        restCom_public_notariesMockMvc.perform(delete("/api/com-public-notaries/{id}", com_public_notaries.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_public_notaries> com_public_notaries = com_public_notariesRepository.findAll();
        assertThat(com_public_notaries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
