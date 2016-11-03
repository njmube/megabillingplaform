package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_custom_unit;
import org.megapractical.billingplatform.repository.Com_custom_unitRepository;
import org.megapractical.billingplatform.service.Com_custom_unitService;

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
 * Test class for the Com_custom_unitResource REST controller.
 *
 * @see Com_custom_unitResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_custom_unitResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private Com_custom_unitRepository com_custom_unitRepository;

    @Inject
    private Com_custom_unitService com_custom_unitService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_custom_unitMockMvc;

    private Com_custom_unit com_custom_unit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_custom_unitResource com_custom_unitResource = new Com_custom_unitResource();
        ReflectionTestUtils.setField(com_custom_unitResource, "com_custom_unitService", com_custom_unitService);
        this.restCom_custom_unitMockMvc = MockMvcBuilders.standaloneSetup(com_custom_unitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_custom_unit = new Com_custom_unit();
        com_custom_unit.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCom_custom_unit() throws Exception {
        int databaseSizeBeforeCreate = com_custom_unitRepository.findAll().size();

        // Create the Com_custom_unit

        restCom_custom_unitMockMvc.perform(post("/api/com-custom-units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_custom_unit)))
                .andExpect(status().isCreated());

        // Validate the Com_custom_unit in the database
        List<Com_custom_unit> com_custom_units = com_custom_unitRepository.findAll();
        assertThat(com_custom_units).hasSize(databaseSizeBeforeCreate + 1);
        Com_custom_unit testCom_custom_unit = com_custom_units.get(com_custom_units.size() - 1);
        assertThat(testCom_custom_unit.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_custom_unitRepository.findAll().size();
        // set the field null
        com_custom_unit.setValue(null);

        // Create the Com_custom_unit, which fails.

        restCom_custom_unitMockMvc.perform(post("/api/com-custom-units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_custom_unit)))
                .andExpect(status().isBadRequest());

        List<Com_custom_unit> com_custom_units = com_custom_unitRepository.findAll();
        assertThat(com_custom_units).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_custom_units() throws Exception {
        // Initialize the database
        com_custom_unitRepository.saveAndFlush(com_custom_unit);

        // Get all the com_custom_units
        restCom_custom_unitMockMvc.perform(get("/api/com-custom-units?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_custom_unit.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCom_custom_unit() throws Exception {
        // Initialize the database
        com_custom_unitRepository.saveAndFlush(com_custom_unit);

        // Get the com_custom_unit
        restCom_custom_unitMockMvc.perform(get("/api/com-custom-units/{id}", com_custom_unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_custom_unit.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_custom_unit() throws Exception {
        // Get the com_custom_unit
        restCom_custom_unitMockMvc.perform(get("/api/com-custom-units/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_custom_unit() throws Exception {
        // Initialize the database
        com_custom_unitService.save(com_custom_unit);

        int databaseSizeBeforeUpdate = com_custom_unitRepository.findAll().size();

        // Update the com_custom_unit
        Com_custom_unit updatedCom_custom_unit = new Com_custom_unit();
        updatedCom_custom_unit.setId(com_custom_unit.getId());
        updatedCom_custom_unit.setValue(UPDATED_VALUE);

        restCom_custom_unitMockMvc.perform(put("/api/com-custom-units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_custom_unit)))
                .andExpect(status().isOk());

        // Validate the Com_custom_unit in the database
        List<Com_custom_unit> com_custom_units = com_custom_unitRepository.findAll();
        assertThat(com_custom_units).hasSize(databaseSizeBeforeUpdate);
        Com_custom_unit testCom_custom_unit = com_custom_units.get(com_custom_units.size() - 1);
        assertThat(testCom_custom_unit.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteCom_custom_unit() throws Exception {
        // Initialize the database
        com_custom_unitService.save(com_custom_unit);

        int databaseSizeBeforeDelete = com_custom_unitRepository.findAll().size();

        // Get the com_custom_unit
        restCom_custom_unitMockMvc.perform(delete("/api/com-custom-units/{id}", com_custom_unit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_custom_unit> com_custom_units = com_custom_unitRepository.findAll();
        assertThat(com_custom_units).hasSize(databaseSizeBeforeDelete - 1);
    }
}
