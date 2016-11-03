package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_local_retentions;
import org.megapractical.billingplatform.repository.Com_local_retentionsRepository;
import org.megapractical.billingplatform.service.Com_local_retentionsService;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_local_retentionsResource REST controller.
 *
 * @see Com_local_retentionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_local_retentionsResourceIntTest {

    private static final String DEFAULT_IMPLOCRETENTIONS = "AAAAA";
    private static final String UPDATED_IMPLOCRETENTIONS = "BBBBB";

    private static final BigDecimal DEFAULT_RETENTIONRATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RETENTIONRATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNTRETENTIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNTRETENTIONS = new BigDecimal(2);

    @Inject
    private Com_local_retentionsRepository com_local_retentionsRepository;

    @Inject
    private Com_local_retentionsService com_local_retentionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_local_retentionsMockMvc;

    private Com_local_retentions com_local_retentions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_local_retentionsResource com_local_retentionsResource = new Com_local_retentionsResource();
        ReflectionTestUtils.setField(com_local_retentionsResource, "com_local_retentionsService", com_local_retentionsService);
        this.restCom_local_retentionsMockMvc = MockMvcBuilders.standaloneSetup(com_local_retentionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_local_retentions = new Com_local_retentions();
        com_local_retentions.setImplocretentions(DEFAULT_IMPLOCRETENTIONS);
        com_local_retentions.setRetentionrate(DEFAULT_RETENTIONRATE);
        com_local_retentions.setAmountretentions(DEFAULT_AMOUNTRETENTIONS);
    }

    @Test
    @Transactional
    public void createCom_local_retentions() throws Exception {
        int databaseSizeBeforeCreate = com_local_retentionsRepository.findAll().size();

        // Create the Com_local_retentions

        restCom_local_retentionsMockMvc.perform(post("/api/com-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_retentions)))
                .andExpect(status().isCreated());

        // Validate the Com_local_retentions in the database
        List<Com_local_retentions> com_local_retentions = com_local_retentionsRepository.findAll();
        assertThat(com_local_retentions).hasSize(databaseSizeBeforeCreate + 1);
        Com_local_retentions testCom_local_retentions = com_local_retentions.get(com_local_retentions.size() - 1);
        assertThat(testCom_local_retentions.getImplocretentions()).isEqualTo(DEFAULT_IMPLOCRETENTIONS);
        assertThat(testCom_local_retentions.getRetentionrate()).isEqualTo(DEFAULT_RETENTIONRATE);
        assertThat(testCom_local_retentions.getAmountretentions()).isEqualTo(DEFAULT_AMOUNTRETENTIONS);
    }

    @Test
    @Transactional
    public void checkImplocretentionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_retentionsRepository.findAll().size();
        // set the field null
        com_local_retentions.setImplocretentions(null);

        // Create the Com_local_retentions, which fails.

        restCom_local_retentionsMockMvc.perform(post("/api/com-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_retentions)))
                .andExpect(status().isBadRequest());

        List<Com_local_retentions> com_local_retentions = com_local_retentionsRepository.findAll();
        assertThat(com_local_retentions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetentionrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_retentionsRepository.findAll().size();
        // set the field null
        com_local_retentions.setRetentionrate(null);

        // Create the Com_local_retentions, which fails.

        restCom_local_retentionsMockMvc.perform(post("/api/com-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_retentions)))
                .andExpect(status().isBadRequest());

        List<Com_local_retentions> com_local_retentions = com_local_retentionsRepository.findAll();
        assertThat(com_local_retentions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountretentionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_retentionsRepository.findAll().size();
        // set the field null
        com_local_retentions.setAmountretentions(null);

        // Create the Com_local_retentions, which fails.

        restCom_local_retentionsMockMvc.perform(post("/api/com-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_retentions)))
                .andExpect(status().isBadRequest());

        List<Com_local_retentions> com_local_retentions = com_local_retentionsRepository.findAll();
        assertThat(com_local_retentions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_local_retentions() throws Exception {
        // Initialize the database
        com_local_retentionsRepository.saveAndFlush(com_local_retentions);

        // Get all the com_local_retentions
        restCom_local_retentionsMockMvc.perform(get("/api/com-local-retentions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_local_retentions.getId().intValue())))
                .andExpect(jsonPath("$.[*].implocretentions").value(hasItem(DEFAULT_IMPLOCRETENTIONS.toString())))
                .andExpect(jsonPath("$.[*].retentionrate").value(hasItem(DEFAULT_RETENTIONRATE.intValue())))
                .andExpect(jsonPath("$.[*].amountretentions").value(hasItem(DEFAULT_AMOUNTRETENTIONS.intValue())));
    }

    @Test
    @Transactional
    public void getCom_local_retentions() throws Exception {
        // Initialize the database
        com_local_retentionsRepository.saveAndFlush(com_local_retentions);

        // Get the com_local_retentions
        restCom_local_retentionsMockMvc.perform(get("/api/com-local-retentions/{id}", com_local_retentions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_local_retentions.getId().intValue()))
            .andExpect(jsonPath("$.implocretentions").value(DEFAULT_IMPLOCRETENTIONS.toString()))
            .andExpect(jsonPath("$.retentionrate").value(DEFAULT_RETENTIONRATE.intValue()))
            .andExpect(jsonPath("$.amountretentions").value(DEFAULT_AMOUNTRETENTIONS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_local_retentions() throws Exception {
        // Get the com_local_retentions
        restCom_local_retentionsMockMvc.perform(get("/api/com-local-retentions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_local_retentions() throws Exception {
        // Initialize the database
        com_local_retentionsService.save(com_local_retentions);

        int databaseSizeBeforeUpdate = com_local_retentionsRepository.findAll().size();

        // Update the com_local_retentions
        Com_local_retentions updatedCom_local_retentions = new Com_local_retentions();
        updatedCom_local_retentions.setId(com_local_retentions.getId());
        updatedCom_local_retentions.setImplocretentions(UPDATED_IMPLOCRETENTIONS);
        updatedCom_local_retentions.setRetentionrate(UPDATED_RETENTIONRATE);
        updatedCom_local_retentions.setAmountretentions(UPDATED_AMOUNTRETENTIONS);

        restCom_local_retentionsMockMvc.perform(put("/api/com-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_local_retentions)))
                .andExpect(status().isOk());

        // Validate the Com_local_retentions in the database
        List<Com_local_retentions> com_local_retentions = com_local_retentionsRepository.findAll();
        assertThat(com_local_retentions).hasSize(databaseSizeBeforeUpdate);
        Com_local_retentions testCom_local_retentions = com_local_retentions.get(com_local_retentions.size() - 1);
        assertThat(testCom_local_retentions.getImplocretentions()).isEqualTo(UPDATED_IMPLOCRETENTIONS);
        assertThat(testCom_local_retentions.getRetentionrate()).isEqualTo(UPDATED_RETENTIONRATE);
        assertThat(testCom_local_retentions.getAmountretentions()).isEqualTo(UPDATED_AMOUNTRETENTIONS);
    }

    @Test
    @Transactional
    public void deleteCom_local_retentions() throws Exception {
        // Initialize the database
        com_local_retentionsService.save(com_local_retentions);

        int databaseSizeBeforeDelete = com_local_retentionsRepository.findAll().size();

        // Get the com_local_retentions
        restCom_local_retentionsMockMvc.perform(delete("/api/com-local-retentions/{id}", com_local_retentions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_local_retentions> com_local_retentions = com_local_retentionsRepository.findAll();
        assertThat(com_local_retentions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
