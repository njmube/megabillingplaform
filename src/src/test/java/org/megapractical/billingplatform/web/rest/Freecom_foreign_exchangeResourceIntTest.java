package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_foreign_exchange;
import org.megapractical.billingplatform.repository.Freecom_foreign_exchangeRepository;
import org.megapractical.billingplatform.service.Freecom_foreign_exchangeService;

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
 * Test class for the Freecom_foreign_exchangeResource REST controller.
 *
 * @see Freecom_foreign_exchangeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_foreign_exchangeResourceIntTest {


    @Inject
    private Freecom_foreign_exchangeRepository freecom_foreign_exchangeRepository;

    @Inject
    private Freecom_foreign_exchangeService freecom_foreign_exchangeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_foreign_exchangeMockMvc;

    private Freecom_foreign_exchange freecom_foreign_exchange;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_foreign_exchangeResource freecom_foreign_exchangeResource = new Freecom_foreign_exchangeResource();
        ReflectionTestUtils.setField(freecom_foreign_exchangeResource, "freecom_foreign_exchangeService", freecom_foreign_exchangeService);
        this.restFreecom_foreign_exchangeMockMvc = MockMvcBuilders.standaloneSetup(freecom_foreign_exchangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_foreign_exchange = new Freecom_foreign_exchange();
    }

    @Test
    @Transactional
    public void createFreecom_foreign_exchange() throws Exception {
        int databaseSizeBeforeCreate = freecom_foreign_exchangeRepository.findAll().size();

        // Create the Freecom_foreign_exchange

        restFreecom_foreign_exchangeMockMvc.perform(post("/api/freecom-foreign-exchanges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_foreign_exchange)))
                .andExpect(status().isCreated());

        // Validate the Freecom_foreign_exchange in the database
        List<Freecom_foreign_exchange> freecom_foreign_exchanges = freecom_foreign_exchangeRepository.findAll();
        assertThat(freecom_foreign_exchanges).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_foreign_exchange testFreecom_foreign_exchange = freecom_foreign_exchanges.get(freecom_foreign_exchanges.size() - 1);
    }

    @Test
    @Transactional
    public void getAllFreecom_foreign_exchanges() throws Exception {
        // Initialize the database
        freecom_foreign_exchangeRepository.saveAndFlush(freecom_foreign_exchange);

        // Get all the freecom_foreign_exchanges
        restFreecom_foreign_exchangeMockMvc.perform(get("/api/freecom-foreign-exchanges?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_foreign_exchange.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_foreign_exchange() throws Exception {
        // Initialize the database
        freecom_foreign_exchangeRepository.saveAndFlush(freecom_foreign_exchange);

        // Get the freecom_foreign_exchange
        restFreecom_foreign_exchangeMockMvc.perform(get("/api/freecom-foreign-exchanges/{id}", freecom_foreign_exchange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_foreign_exchange.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_foreign_exchange() throws Exception {
        // Get the freecom_foreign_exchange
        restFreecom_foreign_exchangeMockMvc.perform(get("/api/freecom-foreign-exchanges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_foreign_exchange() throws Exception {
        // Initialize the database
        freecom_foreign_exchangeService.save(freecom_foreign_exchange);

        int databaseSizeBeforeUpdate = freecom_foreign_exchangeRepository.findAll().size();

        // Update the freecom_foreign_exchange
        Freecom_foreign_exchange updatedFreecom_foreign_exchange = new Freecom_foreign_exchange();
        updatedFreecom_foreign_exchange.setId(freecom_foreign_exchange.getId());

        restFreecom_foreign_exchangeMockMvc.perform(put("/api/freecom-foreign-exchanges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_foreign_exchange)))
                .andExpect(status().isOk());

        // Validate the Freecom_foreign_exchange in the database
        List<Freecom_foreign_exchange> freecom_foreign_exchanges = freecom_foreign_exchangeRepository.findAll();
        assertThat(freecom_foreign_exchanges).hasSize(databaseSizeBeforeUpdate);
        Freecom_foreign_exchange testFreecom_foreign_exchange = freecom_foreign_exchanges.get(freecom_foreign_exchanges.size() - 1);
    }

    @Test
    @Transactional
    public void deleteFreecom_foreign_exchange() throws Exception {
        // Initialize the database
        freecom_foreign_exchangeService.save(freecom_foreign_exchange);

        int databaseSizeBeforeDelete = freecom_foreign_exchangeRepository.findAll().size();

        // Get the freecom_foreign_exchange
        restFreecom_foreign_exchangeMockMvc.perform(delete("/api/freecom-foreign-exchanges/{id}", freecom_foreign_exchange.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_foreign_exchange> freecom_foreign_exchanges = freecom_foreign_exchangeRepository.findAll();
        assertThat(freecom_foreign_exchanges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
