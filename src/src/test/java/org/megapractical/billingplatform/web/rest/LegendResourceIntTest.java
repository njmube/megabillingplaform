package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Legend;
import org.megapractical.billingplatform.repository.LegendRepository;
import org.megapractical.billingplatform.service.LegendService;

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
 * Test class for the LegendResource REST controller.
 *
 * @see LegendResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class LegendResourceIntTest {

    private static final String DEFAULT_TAX_PROVISION = "AAAAA";
    private static final String UPDATED_TAX_PROVISION = "BBBBB";
    private static final String DEFAULT_NORM = "AAAAA";
    private static final String UPDATED_NORM = "BBBBB";
    private static final String DEFAULT_TEXT_LEGEND = "AAAAA";
    private static final String UPDATED_TEXT_LEGEND = "BBBBB";

    @Inject
    private LegendRepository legendRepository;

    @Inject
    private LegendService legendService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLegendMockMvc;

    private Legend legend;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LegendResource legendResource = new LegendResource();
        ReflectionTestUtils.setField(legendResource, "legendService", legendService);
        this.restLegendMockMvc = MockMvcBuilders.standaloneSetup(legendResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        legend = new Legend();
        legend.setTax_provision(DEFAULT_TAX_PROVISION);
        legend.setNorm(DEFAULT_NORM);
        legend.setText_legend(DEFAULT_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void createLegend() throws Exception {
        int databaseSizeBeforeCreate = legendRepository.findAll().size();

        // Create the Legend

        restLegendMockMvc.perform(post("/api/legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(legend)))
                .andExpect(status().isCreated());

        // Validate the Legend in the database
        List<Legend> legends = legendRepository.findAll();
        assertThat(legends).hasSize(databaseSizeBeforeCreate + 1);
        Legend testLegend = legends.get(legends.size() - 1);
        assertThat(testLegend.getTax_provision()).isEqualTo(DEFAULT_TAX_PROVISION);
        assertThat(testLegend.getNorm()).isEqualTo(DEFAULT_NORM);
        assertThat(testLegend.getText_legend()).isEqualTo(DEFAULT_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void checkText_legendIsRequired() throws Exception {
        int databaseSizeBeforeTest = legendRepository.findAll().size();
        // set the field null
        legend.setText_legend(null);

        // Create the Legend, which fails.

        restLegendMockMvc.perform(post("/api/legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(legend)))
                .andExpect(status().isBadRequest());

        List<Legend> legends = legendRepository.findAll();
        assertThat(legends).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLegends() throws Exception {
        // Initialize the database
        legendRepository.saveAndFlush(legend);

        // Get all the legends
        restLegendMockMvc.perform(get("/api/legends?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(legend.getId().intValue())))
                .andExpect(jsonPath("$.[*].tax_provision").value(hasItem(DEFAULT_TAX_PROVISION.toString())))
                .andExpect(jsonPath("$.[*].norm").value(hasItem(DEFAULT_NORM.toString())))
                .andExpect(jsonPath("$.[*].text_legend").value(hasItem(DEFAULT_TEXT_LEGEND.toString())));
    }

    @Test
    @Transactional
    public void getLegend() throws Exception {
        // Initialize the database
        legendRepository.saveAndFlush(legend);

        // Get the legend
        restLegendMockMvc.perform(get("/api/legends/{id}", legend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(legend.getId().intValue()))
            .andExpect(jsonPath("$.tax_provision").value(DEFAULT_TAX_PROVISION.toString()))
            .andExpect(jsonPath("$.norm").value(DEFAULT_NORM.toString()))
            .andExpect(jsonPath("$.text_legend").value(DEFAULT_TEXT_LEGEND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLegend() throws Exception {
        // Get the legend
        restLegendMockMvc.perform(get("/api/legends/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLegend() throws Exception {
        // Initialize the database
        legendService.save(legend);

        int databaseSizeBeforeUpdate = legendRepository.findAll().size();

        // Update the legend
        Legend updatedLegend = new Legend();
        updatedLegend.setId(legend.getId());
        updatedLegend.setTax_provision(UPDATED_TAX_PROVISION);
        updatedLegend.setNorm(UPDATED_NORM);
        updatedLegend.setText_legend(UPDATED_TEXT_LEGEND);

        restLegendMockMvc.perform(put("/api/legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLegend)))
                .andExpect(status().isOk());

        // Validate the Legend in the database
        List<Legend> legends = legendRepository.findAll();
        assertThat(legends).hasSize(databaseSizeBeforeUpdate);
        Legend testLegend = legends.get(legends.size() - 1);
        assertThat(testLegend.getTax_provision()).isEqualTo(UPDATED_TAX_PROVISION);
        assertThat(testLegend.getNorm()).isEqualTo(UPDATED_NORM);
        assertThat(testLegend.getText_legend()).isEqualTo(UPDATED_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void deleteLegend() throws Exception {
        // Initialize the database
        legendService.save(legend);

        int databaseSizeBeforeDelete = legendRepository.findAll().size();

        // Get the legend
        restLegendMockMvc.perform(delete("/api/legends/{id}", legend.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Legend> legends = legendRepository.findAll();
        assertThat(legends).hasSize(databaseSizeBeforeDelete - 1);
    }
}
