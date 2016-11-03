package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_legends;
import org.megapractical.billingplatform.repository.Com_legendsRepository;
import org.megapractical.billingplatform.service.Com_legendsService;

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
 * Test class for the Com_legendsResource REST controller.
 *
 * @see Com_legendsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_legendsResourceIntTest {

    private static final String DEFAULT_TAX_PROVISION = "AAAAA";
    private static final String UPDATED_TAX_PROVISION = "BBBBB";
    private static final String DEFAULT_NORM = "AAAAA";
    private static final String UPDATED_NORM = "BBBBB";
    private static final String DEFAULT_TEXT_LEGEND = "AAAAA";
    private static final String UPDATED_TEXT_LEGEND = "BBBBB";

    @Inject
    private Com_legendsRepository com_legendsRepository;

    @Inject
    private Com_legendsService com_legendsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_legendsMockMvc;

    private Com_legends com_legends;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_legendsResource com_legendsResource = new Com_legendsResource();
        ReflectionTestUtils.setField(com_legendsResource, "com_legendsService", com_legendsService);
        this.restCom_legendsMockMvc = MockMvcBuilders.standaloneSetup(com_legendsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_legends = new Com_legends();
        com_legends.setTax_provision(DEFAULT_TAX_PROVISION);
        com_legends.setNorm(DEFAULT_NORM);
        com_legends.setText_legend(DEFAULT_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void createCom_legends() throws Exception {
        int databaseSizeBeforeCreate = com_legendsRepository.findAll().size();

        // Create the Com_legends

        restCom_legendsMockMvc.perform(post("/api/com-legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_legends)))
                .andExpect(status().isCreated());

        // Validate the Com_legends in the database
        List<Com_legends> com_legends = com_legendsRepository.findAll();
        assertThat(com_legends).hasSize(databaseSizeBeforeCreate + 1);
        Com_legends testCom_legends = com_legends.get(com_legends.size() - 1);
        assertThat(testCom_legends.getTax_provision()).isEqualTo(DEFAULT_TAX_PROVISION);
        assertThat(testCom_legends.getNorm()).isEqualTo(DEFAULT_NORM);
        assertThat(testCom_legends.getText_legend()).isEqualTo(DEFAULT_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void checkText_legendIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_legendsRepository.findAll().size();
        // set the field null
        com_legends.setText_legend(null);

        // Create the Com_legends, which fails.

        restCom_legendsMockMvc.perform(post("/api/com-legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_legends)))
                .andExpect(status().isBadRequest());

        List<Com_legends> com_legends = com_legendsRepository.findAll();
        assertThat(com_legends).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_legends() throws Exception {
        // Initialize the database
        com_legendsRepository.saveAndFlush(com_legends);

        // Get all the com_legends
        restCom_legendsMockMvc.perform(get("/api/com-legends?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_legends.getId().intValue())))
                .andExpect(jsonPath("$.[*].tax_provision").value(hasItem(DEFAULT_TAX_PROVISION.toString())))
                .andExpect(jsonPath("$.[*].norm").value(hasItem(DEFAULT_NORM.toString())))
                .andExpect(jsonPath("$.[*].text_legend").value(hasItem(DEFAULT_TEXT_LEGEND.toString())));
    }

    @Test
    @Transactional
    public void getCom_legends() throws Exception {
        // Initialize the database
        com_legendsRepository.saveAndFlush(com_legends);

        // Get the com_legends
        restCom_legendsMockMvc.perform(get("/api/com-legends/{id}", com_legends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_legends.getId().intValue()))
            .andExpect(jsonPath("$.tax_provision").value(DEFAULT_TAX_PROVISION.toString()))
            .andExpect(jsonPath("$.norm").value(DEFAULT_NORM.toString()))
            .andExpect(jsonPath("$.text_legend").value(DEFAULT_TEXT_LEGEND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_legends() throws Exception {
        // Get the com_legends
        restCom_legendsMockMvc.perform(get("/api/com-legends/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_legends() throws Exception {
        // Initialize the database
        com_legendsService.save(com_legends);

        int databaseSizeBeforeUpdate = com_legendsRepository.findAll().size();

        // Update the com_legends
        Com_legends updatedCom_legends = new Com_legends();
        updatedCom_legends.setId(com_legends.getId());
        updatedCom_legends.setTax_provision(UPDATED_TAX_PROVISION);
        updatedCom_legends.setNorm(UPDATED_NORM);
        updatedCom_legends.setText_legend(UPDATED_TEXT_LEGEND);

        restCom_legendsMockMvc.perform(put("/api/com-legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_legends)))
                .andExpect(status().isOk());

        // Validate the Com_legends in the database
        List<Com_legends> com_legends = com_legendsRepository.findAll();
        assertThat(com_legends).hasSize(databaseSizeBeforeUpdate);
        Com_legends testCom_legends = com_legends.get(com_legends.size() - 1);
        assertThat(testCom_legends.getTax_provision()).isEqualTo(UPDATED_TAX_PROVISION);
        assertThat(testCom_legends.getNorm()).isEqualTo(UPDATED_NORM);
        assertThat(testCom_legends.getText_legend()).isEqualTo(UPDATED_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void deleteCom_legends() throws Exception {
        // Initialize the database
        com_legendsService.save(com_legends);

        int databaseSizeBeforeDelete = com_legendsRepository.findAll().size();

        // Get the com_legends
        restCom_legendsMockMvc.perform(delete("/api/com-legends/{id}", com_legends.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_legends> com_legends = com_legendsRepository.findAll();
        assertThat(com_legends).hasSize(databaseSizeBeforeDelete - 1);
    }
}
