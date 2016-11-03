package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_spei;
import org.megapractical.billingplatform.repository.Com_speiRepository;
import org.megapractical.billingplatform.service.Com_speiService;

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
 * Test class for the Com_speiResource REST controller.
 *
 * @see Com_speiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_speiResourceIntTest {


    @Inject
    private Com_speiRepository com_speiRepository;

    @Inject
    private Com_speiService com_speiService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_speiMockMvc;

    private Com_spei com_spei;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_speiResource com_speiResource = new Com_speiResource();
        ReflectionTestUtils.setField(com_speiResource, "com_speiService", com_speiService);
        this.restCom_speiMockMvc = MockMvcBuilders.standaloneSetup(com_speiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_spei = new Com_spei();
    }

    @Test
    @Transactional
    public void createCom_spei() throws Exception {
        int databaseSizeBeforeCreate = com_speiRepository.findAll().size();

        // Create the Com_spei

        restCom_speiMockMvc.perform(post("/api/com-speis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_spei)))
                .andExpect(status().isCreated());

        // Validate the Com_spei in the database
        List<Com_spei> com_speis = com_speiRepository.findAll();
        assertThat(com_speis).hasSize(databaseSizeBeforeCreate + 1);
        Com_spei testCom_spei = com_speis.get(com_speis.size() - 1);
    }

    @Test
    @Transactional
    public void getAllCom_speis() throws Exception {
        // Initialize the database
        com_speiRepository.saveAndFlush(com_spei);

        // Get all the com_speis
        restCom_speiMockMvc.perform(get("/api/com-speis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_spei.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCom_spei() throws Exception {
        // Initialize the database
        com_speiRepository.saveAndFlush(com_spei);

        // Get the com_spei
        restCom_speiMockMvc.perform(get("/api/com-speis/{id}", com_spei.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_spei.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_spei() throws Exception {
        // Get the com_spei
        restCom_speiMockMvc.perform(get("/api/com-speis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_spei() throws Exception {
        // Initialize the database
        com_speiService.save(com_spei);

        int databaseSizeBeforeUpdate = com_speiRepository.findAll().size();

        // Update the com_spei
        Com_spei updatedCom_spei = new Com_spei();
        updatedCom_spei.setId(com_spei.getId());

        restCom_speiMockMvc.perform(put("/api/com-speis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_spei)))
                .andExpect(status().isOk());

        // Validate the Com_spei in the database
        List<Com_spei> com_speis = com_speiRepository.findAll();
        assertThat(com_speis).hasSize(databaseSizeBeforeUpdate);
        Com_spei testCom_spei = com_speis.get(com_speis.size() - 1);
    }

    @Test
    @Transactional
    public void deleteCom_spei() throws Exception {
        // Initialize the database
        com_speiService.save(com_spei);

        int databaseSizeBeforeDelete = com_speiRepository.findAll().size();

        // Get the com_spei
        restCom_speiMockMvc.perform(delete("/api/com-speis/{id}", com_spei.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_spei> com_speis = com_speiRepository.findAll();
        assertThat(com_speis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
