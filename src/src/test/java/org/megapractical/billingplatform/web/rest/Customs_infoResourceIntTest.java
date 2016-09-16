package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Customs_info;
import org.megapractical.billingplatform.repository.Customs_infoRepository;
import org.megapractical.billingplatform.service.Customs_infoService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Customs_infoResource REST controller.
 *
 * @see Customs_infoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Customs_infoResourceIntTest {

    private static final String DEFAULT_NUMBER_DOC = "AAAAA";
    private static final String UPDATED_NUMBER_DOC = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CUSTOMS = "AAAAA";
    private static final String UPDATED_CUSTOMS = "BBBBB";

    @Inject
    private Customs_infoRepository customs_infoRepository;

    @Inject
    private Customs_infoService customs_infoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustoms_infoMockMvc;

    private Customs_info customs_info;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Customs_infoResource customs_infoResource = new Customs_infoResource();
        ReflectionTestUtils.setField(customs_infoResource, "customs_infoService", customs_infoService);
        this.restCustoms_infoMockMvc = MockMvcBuilders.standaloneSetup(customs_infoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customs_info = new Customs_info();
        customs_info.setNumber_doc(DEFAULT_NUMBER_DOC);
        customs_info.setDate(DEFAULT_DATE);
        customs_info.setCustoms(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void createCustoms_info() throws Exception {
        int databaseSizeBeforeCreate = customs_infoRepository.findAll().size();

        // Create the Customs_info

        restCustoms_infoMockMvc.perform(post("/api/customs-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customs_info)))
                .andExpect(status().isCreated());

        // Validate the Customs_info in the database
        List<Customs_info> customs_infos = customs_infoRepository.findAll();
        assertThat(customs_infos).hasSize(databaseSizeBeforeCreate + 1);
        Customs_info testCustoms_info = customs_infos.get(customs_infos.size() - 1);
        assertThat(testCustoms_info.getNumber_doc()).isEqualTo(DEFAULT_NUMBER_DOC);
        assertThat(testCustoms_info.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCustoms_info.getCustoms()).isEqualTo(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void checkNumber_docIsRequired() throws Exception {
        int databaseSizeBeforeTest = customs_infoRepository.findAll().size();
        // set the field null
        customs_info.setNumber_doc(null);

        // Create the Customs_info, which fails.

        restCustoms_infoMockMvc.perform(post("/api/customs-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customs_info)))
                .andExpect(status().isBadRequest());

        List<Customs_info> customs_infos = customs_infoRepository.findAll();
        assertThat(customs_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customs_infoRepository.findAll().size();
        // set the field null
        customs_info.setDate(null);

        // Create the Customs_info, which fails.

        restCustoms_infoMockMvc.perform(post("/api/customs-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customs_info)))
                .andExpect(status().isBadRequest());

        List<Customs_info> customs_infos = customs_infoRepository.findAll();
        assertThat(customs_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustoms_infos() throws Exception {
        // Initialize the database
        customs_infoRepository.saveAndFlush(customs_info);

        // Get all the customs_infos
        restCustoms_infoMockMvc.perform(get("/api/customs-infos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customs_info.getId().intValue())))
                .andExpect(jsonPath("$.[*].number_doc").value(hasItem(DEFAULT_NUMBER_DOC.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].customs").value(hasItem(DEFAULT_CUSTOMS.toString())));
    }

    @Test
    @Transactional
    public void getCustoms_info() throws Exception {
        // Initialize the database
        customs_infoRepository.saveAndFlush(customs_info);

        // Get the customs_info
        restCustoms_infoMockMvc.perform(get("/api/customs-infos/{id}", customs_info.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customs_info.getId().intValue()))
            .andExpect(jsonPath("$.number_doc").value(DEFAULT_NUMBER_DOC.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.customs").value(DEFAULT_CUSTOMS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustoms_info() throws Exception {
        // Get the customs_info
        restCustoms_infoMockMvc.perform(get("/api/customs-infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustoms_info() throws Exception {
        // Initialize the database
        customs_infoService.save(customs_info);

        int databaseSizeBeforeUpdate = customs_infoRepository.findAll().size();

        // Update the customs_info
        Customs_info updatedCustoms_info = new Customs_info();
        updatedCustoms_info.setId(customs_info.getId());
        updatedCustoms_info.setNumber_doc(UPDATED_NUMBER_DOC);
        updatedCustoms_info.setDate(UPDATED_DATE);
        updatedCustoms_info.setCustoms(UPDATED_CUSTOMS);

        restCustoms_infoMockMvc.perform(put("/api/customs-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCustoms_info)))
                .andExpect(status().isOk());

        // Validate the Customs_info in the database
        List<Customs_info> customs_infos = customs_infoRepository.findAll();
        assertThat(customs_infos).hasSize(databaseSizeBeforeUpdate);
        Customs_info testCustoms_info = customs_infos.get(customs_infos.size() - 1);
        assertThat(testCustoms_info.getNumber_doc()).isEqualTo(UPDATED_NUMBER_DOC);
        assertThat(testCustoms_info.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCustoms_info.getCustoms()).isEqualTo(UPDATED_CUSTOMS);
    }

    @Test
    @Transactional
    public void deleteCustoms_info() throws Exception {
        // Initialize the database
        customs_infoService.save(customs_info);

        int databaseSizeBeforeDelete = customs_infoRepository.findAll().size();

        // Get the customs_info
        restCustoms_infoMockMvc.perform(delete("/api/customs-infos/{id}", customs_info.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Customs_info> customs_infos = customs_infoRepository.findAll();
        assertThat(customs_infos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
