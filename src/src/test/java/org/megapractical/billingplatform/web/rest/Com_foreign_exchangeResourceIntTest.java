package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_foreign_exchange;
import org.megapractical.billingplatform.repository.Com_foreign_exchangeRepository;
import org.megapractical.billingplatform.service.Com_foreign_exchangeService;

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
 * Test class for the Com_foreign_exchangeResource REST controller.
 *
 * @see Com_foreign_exchangeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_foreign_exchangeResourceIntTest {


    @Inject
    private Com_foreign_exchangeRepository com_foreign_exchangeRepository;

    @Inject
    private Com_foreign_exchangeService com_foreign_exchangeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_foreign_exchangeMockMvc;

    private Com_foreign_exchange com_foreign_exchange;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_foreign_exchangeResource com_foreign_exchangeResource = new Com_foreign_exchangeResource();
        ReflectionTestUtils.setField(com_foreign_exchangeResource, "com_foreign_exchangeService", com_foreign_exchangeService);
        this.restCom_foreign_exchangeMockMvc = MockMvcBuilders.standaloneSetup(com_foreign_exchangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_foreign_exchange = new Com_foreign_exchange();
    }

    @Test
    @Transactional
    public void createCom_foreign_exchange() throws Exception {
        int databaseSizeBeforeCreate = com_foreign_exchangeRepository.findAll().size();

        // Create the Com_foreign_exchange

        restCom_foreign_exchangeMockMvc.perform(post("/api/com-foreign-exchanges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_exchange)))
                .andExpect(status().isCreated());

        // Validate the Com_foreign_exchange in the database
        List<Com_foreign_exchange> com_foreign_exchanges = com_foreign_exchangeRepository.findAll();
        assertThat(com_foreign_exchanges).hasSize(databaseSizeBeforeCreate + 1);
        Com_foreign_exchange testCom_foreign_exchange = com_foreign_exchanges.get(com_foreign_exchanges.size() - 1);
    }

    @Test
    @Transactional
    public void getAllCom_foreign_exchanges() throws Exception {
        // Initialize the database
        com_foreign_exchangeRepository.saveAndFlush(com_foreign_exchange);

        // Get all the com_foreign_exchanges
        restCom_foreign_exchangeMockMvc.perform(get("/api/com-foreign-exchanges?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_foreign_exchange.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCom_foreign_exchange() throws Exception {
        // Initialize the database
        com_foreign_exchangeRepository.saveAndFlush(com_foreign_exchange);

        // Get the com_foreign_exchange
        restCom_foreign_exchangeMockMvc.perform(get("/api/com-foreign-exchanges/{id}", com_foreign_exchange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_foreign_exchange.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_foreign_exchange() throws Exception {
        // Get the com_foreign_exchange
        restCom_foreign_exchangeMockMvc.perform(get("/api/com-foreign-exchanges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_foreign_exchange() throws Exception {
        // Initialize the database
        com_foreign_exchangeService.save(com_foreign_exchange);

        int databaseSizeBeforeUpdate = com_foreign_exchangeRepository.findAll().size();

        // Update the com_foreign_exchange
        Com_foreign_exchange updatedCom_foreign_exchange = new Com_foreign_exchange();
        updatedCom_foreign_exchange.setId(com_foreign_exchange.getId());

        restCom_foreign_exchangeMockMvc.perform(put("/api/com-foreign-exchanges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_foreign_exchange)))
                .andExpect(status().isOk());

        // Validate the Com_foreign_exchange in the database
        List<Com_foreign_exchange> com_foreign_exchanges = com_foreign_exchangeRepository.findAll();
        assertThat(com_foreign_exchanges).hasSize(databaseSizeBeforeUpdate);
        Com_foreign_exchange testCom_foreign_exchange = com_foreign_exchanges.get(com_foreign_exchanges.size() - 1);
    }

    @Test
    @Transactional
    public void deleteCom_foreign_exchange() throws Exception {
        // Initialize the database
        com_foreign_exchangeService.save(com_foreign_exchange);

        int databaseSizeBeforeDelete = com_foreign_exchangeRepository.findAll().size();

        // Get the com_foreign_exchange
        restCom_foreign_exchangeMockMvc.perform(delete("/api/com-foreign-exchanges/{id}", com_foreign_exchange.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_foreign_exchange> com_foreign_exchanges = com_foreign_exchangeRepository.findAll();
        assertThat(com_foreign_exchanges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
