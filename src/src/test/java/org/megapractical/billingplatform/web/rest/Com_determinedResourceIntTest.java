package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_determined;
import org.megapractical.billingplatform.repository.Com_determinedRepository;
import org.megapractical.billingplatform.service.Com_determinedService;

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
 * Test class for the Com_determinedResource REST controller.
 *
 * @see Com_determinedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_determinedResourceIntTest {


    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Com_determinedRepository com_determinedRepository;

    @Inject
    private Com_determinedService com_determinedService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_determinedMockMvc;

    private Com_determined com_determined;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_determinedResource com_determinedResource = new Com_determinedResource();
        ReflectionTestUtils.setField(com_determinedResource, "com_determinedService", com_determinedService);
        this.restCom_determinedMockMvc = MockMvcBuilders.standaloneSetup(com_determinedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_determined = new Com_determined();
        com_determined.setRate(DEFAULT_RATE);
        com_determined.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createCom_determined() throws Exception {
        int databaseSizeBeforeCreate = com_determinedRepository.findAll().size();

        // Create the Com_determined

        restCom_determinedMockMvc.perform(post("/api/com-determineds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_determined)))
                .andExpect(status().isCreated());

        // Validate the Com_determined in the database
        List<Com_determined> com_determineds = com_determinedRepository.findAll();
        assertThat(com_determineds).hasSize(databaseSizeBeforeCreate + 1);
        Com_determined testCom_determined = com_determineds.get(com_determineds.size() - 1);
        assertThat(testCom_determined.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCom_determined.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_determinedRepository.findAll().size();
        // set the field null
        com_determined.setRate(null);

        // Create the Com_determined, which fails.

        restCom_determinedMockMvc.perform(post("/api/com-determineds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_determined)))
                .andExpect(status().isBadRequest());

        List<Com_determined> com_determineds = com_determinedRepository.findAll();
        assertThat(com_determineds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_determinedRepository.findAll().size();
        // set the field null
        com_determined.setAmount(null);

        // Create the Com_determined, which fails.

        restCom_determinedMockMvc.perform(post("/api/com-determineds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_determined)))
                .andExpect(status().isBadRequest());

        List<Com_determined> com_determineds = com_determinedRepository.findAll();
        assertThat(com_determineds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_determineds() throws Exception {
        // Initialize the database
        com_determinedRepository.saveAndFlush(com_determined);

        // Get all the com_determineds
        restCom_determinedMockMvc.perform(get("/api/com-determineds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_determined.getId().intValue())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getCom_determined() throws Exception {
        // Initialize the database
        com_determinedRepository.saveAndFlush(com_determined);

        // Get the com_determined
        restCom_determinedMockMvc.perform(get("/api/com-determineds/{id}", com_determined.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_determined.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_determined() throws Exception {
        // Get the com_determined
        restCom_determinedMockMvc.perform(get("/api/com-determineds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_determined() throws Exception {
        // Initialize the database
        com_determinedService.save(com_determined);

        int databaseSizeBeforeUpdate = com_determinedRepository.findAll().size();

        // Update the com_determined
        Com_determined updatedCom_determined = new Com_determined();
        updatedCom_determined.setId(com_determined.getId());
        updatedCom_determined.setRate(UPDATED_RATE);
        updatedCom_determined.setAmount(UPDATED_AMOUNT);

        restCom_determinedMockMvc.perform(put("/api/com-determineds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_determined)))
                .andExpect(status().isOk());

        // Validate the Com_determined in the database
        List<Com_determined> com_determineds = com_determinedRepository.findAll();
        assertThat(com_determineds).hasSize(databaseSizeBeforeUpdate);
        Com_determined testCom_determined = com_determineds.get(com_determineds.size() - 1);
        assertThat(testCom_determined.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCom_determined.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteCom_determined() throws Exception {
        // Initialize the database
        com_determinedService.save(com_determined);

        int databaseSizeBeforeDelete = com_determinedRepository.findAll().size();

        // Get the com_determined
        restCom_determinedMockMvc.perform(delete("/api/com-determineds/{id}", com_determined.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_determined> com_determineds = com_determinedRepository.findAll();
        assertThat(com_determineds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
