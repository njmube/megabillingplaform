package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_accounting;
import org.megapractical.billingplatform.repository.Com_accountingRepository;
import org.megapractical.billingplatform.service.Com_accountingService;

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
 * Test class for the Com_accountingResource REST controller.
 *
 * @see Com_accountingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_accountingResourceIntTest {


    private static final Integer DEFAULT_KEYACCOUNTING = 1;
    private static final Integer UPDATED_KEYACCOUNTING = 2;

    @Inject
    private Com_accountingRepository com_accountingRepository;

    @Inject
    private Com_accountingService com_accountingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_accountingMockMvc;

    private Com_accounting com_accounting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_accountingResource com_accountingResource = new Com_accountingResource();
        ReflectionTestUtils.setField(com_accountingResource, "com_accountingService", com_accountingService);
        this.restCom_accountingMockMvc = MockMvcBuilders.standaloneSetup(com_accountingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_accounting = new Com_accounting();
        com_accounting.setKeyaccounting(DEFAULT_KEYACCOUNTING);
    }

    @Test
    @Transactional
    public void createCom_accounting() throws Exception {
        int databaseSizeBeforeCreate = com_accountingRepository.findAll().size();

        // Create the Com_accounting

        restCom_accountingMockMvc.perform(post("/api/com-accountings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_accounting)))
                .andExpect(status().isCreated());

        // Validate the Com_accounting in the database
        List<Com_accounting> com_accountings = com_accountingRepository.findAll();
        assertThat(com_accountings).hasSize(databaseSizeBeforeCreate + 1);
        Com_accounting testCom_accounting = com_accountings.get(com_accountings.size() - 1);
        assertThat(testCom_accounting.getKeyaccounting()).isEqualTo(DEFAULT_KEYACCOUNTING);
    }

    @Test
    @Transactional
    public void checkKeyaccountingIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_accountingRepository.findAll().size();
        // set the field null
        com_accounting.setKeyaccounting(null);

        // Create the Com_accounting, which fails.

        restCom_accountingMockMvc.perform(post("/api/com-accountings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_accounting)))
                .andExpect(status().isBadRequest());

        List<Com_accounting> com_accountings = com_accountingRepository.findAll();
        assertThat(com_accountings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_accountings() throws Exception {
        // Initialize the database
        com_accountingRepository.saveAndFlush(com_accounting);

        // Get all the com_accountings
        restCom_accountingMockMvc.perform(get("/api/com-accountings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_accounting.getId().intValue())))
                .andExpect(jsonPath("$.[*].keyaccounting").value(hasItem(DEFAULT_KEYACCOUNTING)));
    }

    @Test
    @Transactional
    public void getCom_accounting() throws Exception {
        // Initialize the database
        com_accountingRepository.saveAndFlush(com_accounting);

        // Get the com_accounting
        restCom_accountingMockMvc.perform(get("/api/com-accountings/{id}", com_accounting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_accounting.getId().intValue()))
            .andExpect(jsonPath("$.keyaccounting").value(DEFAULT_KEYACCOUNTING));
    }

    @Test
    @Transactional
    public void getNonExistingCom_accounting() throws Exception {
        // Get the com_accounting
        restCom_accountingMockMvc.perform(get("/api/com-accountings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_accounting() throws Exception {
        // Initialize the database
        com_accountingService.save(com_accounting);

        int databaseSizeBeforeUpdate = com_accountingRepository.findAll().size();

        // Update the com_accounting
        Com_accounting updatedCom_accounting = new Com_accounting();
        updatedCom_accounting.setId(com_accounting.getId());
        updatedCom_accounting.setKeyaccounting(UPDATED_KEYACCOUNTING);

        restCom_accountingMockMvc.perform(put("/api/com-accountings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_accounting)))
                .andExpect(status().isOk());

        // Validate the Com_accounting in the database
        List<Com_accounting> com_accountings = com_accountingRepository.findAll();
        assertThat(com_accountings).hasSize(databaseSizeBeforeUpdate);
        Com_accounting testCom_accounting = com_accountings.get(com_accountings.size() - 1);
        assertThat(testCom_accounting.getKeyaccounting()).isEqualTo(UPDATED_KEYACCOUNTING);
    }

    @Test
    @Transactional
    public void deleteCom_accounting() throws Exception {
        // Initialize the database
        com_accountingService.save(com_accounting);

        int databaseSizeBeforeDelete = com_accountingRepository.findAll().size();

        // Get the com_accounting
        restCom_accountingMockMvc.perform(delete("/api/com-accountings/{id}", com_accounting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_accounting> com_accountings = com_accountingRepository.findAll();
        assertThat(com_accountings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
