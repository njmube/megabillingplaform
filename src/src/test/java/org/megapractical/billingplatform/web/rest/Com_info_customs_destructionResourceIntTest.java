package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_info_customs_destruction;
import org.megapractical.billingplatform.repository.Com_info_customs_destructionRepository;
import org.megapractical.billingplatform.service.Com_info_customs_destructionService;

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
 * Test class for the Com_info_customs_destructionResource REST controller.
 *
 * @see Com_info_customs_destructionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_info_customs_destructionResourceIntTest {

    private static final String DEFAULT_NUMPEDIMP = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMPEDIMP = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_EXPEDITION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPEDITION = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CUSTOMS = "AAAAA";
    private static final String UPDATED_CUSTOMS = "BBBBB";

    @Inject
    private Com_info_customs_destructionRepository com_info_customs_destructionRepository;

    @Inject
    private Com_info_customs_destructionService com_info_customs_destructionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_info_customs_destructionMockMvc;

    private Com_info_customs_destruction com_info_customs_destruction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_info_customs_destructionResource com_info_customs_destructionResource = new Com_info_customs_destructionResource();
        ReflectionTestUtils.setField(com_info_customs_destructionResource, "com_info_customs_destructionService", com_info_customs_destructionService);
        this.restCom_info_customs_destructionMockMvc = MockMvcBuilders.standaloneSetup(com_info_customs_destructionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_info_customs_destruction = new Com_info_customs_destruction();
        com_info_customs_destruction.setNumpedimp(DEFAULT_NUMPEDIMP);
        com_info_customs_destruction.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        com_info_customs_destruction.setCustoms(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void createCom_info_customs_destruction() throws Exception {
        int databaseSizeBeforeCreate = com_info_customs_destructionRepository.findAll().size();

        // Create the Com_info_customs_destruction

        restCom_info_customs_destructionMockMvc.perform(post("/api/com-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_info_customs_destruction)))
                .andExpect(status().isCreated());

        // Validate the Com_info_customs_destruction in the database
        List<Com_info_customs_destruction> com_info_customs_destructions = com_info_customs_destructionRepository.findAll();
        assertThat(com_info_customs_destructions).hasSize(databaseSizeBeforeCreate + 1);
        Com_info_customs_destruction testCom_info_customs_destruction = com_info_customs_destructions.get(com_info_customs_destructions.size() - 1);
        assertThat(testCom_info_customs_destruction.getNumpedimp()).isEqualTo(DEFAULT_NUMPEDIMP);
        assertThat(testCom_info_customs_destruction.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testCom_info_customs_destruction.getCustoms()).isEqualTo(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void checkNumpedimpIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_info_customs_destructionRepository.findAll().size();
        // set the field null
        com_info_customs_destruction.setNumpedimp(null);

        // Create the Com_info_customs_destruction, which fails.

        restCom_info_customs_destructionMockMvc.perform(post("/api/com-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_info_customs_destruction)))
                .andExpect(status().isBadRequest());

        List<Com_info_customs_destruction> com_info_customs_destructions = com_info_customs_destructionRepository.findAll();
        assertThat(com_info_customs_destructions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_info_customs_destructionRepository.findAll().size();
        // set the field null
        com_info_customs_destruction.setDate_expedition(null);

        // Create the Com_info_customs_destruction, which fails.

        restCom_info_customs_destructionMockMvc.perform(post("/api/com-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_info_customs_destruction)))
                .andExpect(status().isBadRequest());

        List<Com_info_customs_destruction> com_info_customs_destructions = com_info_customs_destructionRepository.findAll();
        assertThat(com_info_customs_destructions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_info_customs_destructionRepository.findAll().size();
        // set the field null
        com_info_customs_destruction.setCustoms(null);

        // Create the Com_info_customs_destruction, which fails.

        restCom_info_customs_destructionMockMvc.perform(post("/api/com-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_info_customs_destruction)))
                .andExpect(status().isBadRequest());

        List<Com_info_customs_destruction> com_info_customs_destructions = com_info_customs_destructionRepository.findAll();
        assertThat(com_info_customs_destructions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_info_customs_destructions() throws Exception {
        // Initialize the database
        com_info_customs_destructionRepository.saveAndFlush(com_info_customs_destruction);

        // Get all the com_info_customs_destructions
        restCom_info_customs_destructionMockMvc.perform(get("/api/com-info-customs-destructions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_info_customs_destruction.getId().intValue())))
                .andExpect(jsonPath("$.[*].numpedimp").value(hasItem(DEFAULT_NUMPEDIMP.toString())))
                .andExpect(jsonPath("$.[*].date_expedition").value(hasItem(DEFAULT_DATE_EXPEDITION.toString())))
                .andExpect(jsonPath("$.[*].customs").value(hasItem(DEFAULT_CUSTOMS.toString())));
    }

    @Test
    @Transactional
    public void getCom_info_customs_destruction() throws Exception {
        // Initialize the database
        com_info_customs_destructionRepository.saveAndFlush(com_info_customs_destruction);

        // Get the com_info_customs_destruction
        restCom_info_customs_destructionMockMvc.perform(get("/api/com-info-customs-destructions/{id}", com_info_customs_destruction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_info_customs_destruction.getId().intValue()))
            .andExpect(jsonPath("$.numpedimp").value(DEFAULT_NUMPEDIMP.toString()))
            .andExpect(jsonPath("$.date_expedition").value(DEFAULT_DATE_EXPEDITION.toString()))
            .andExpect(jsonPath("$.customs").value(DEFAULT_CUSTOMS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_info_customs_destruction() throws Exception {
        // Get the com_info_customs_destruction
        restCom_info_customs_destructionMockMvc.perform(get("/api/com-info-customs-destructions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_info_customs_destruction() throws Exception {
        // Initialize the database
        com_info_customs_destructionService.save(com_info_customs_destruction);

        int databaseSizeBeforeUpdate = com_info_customs_destructionRepository.findAll().size();

        // Update the com_info_customs_destruction
        Com_info_customs_destruction updatedCom_info_customs_destruction = new Com_info_customs_destruction();
        updatedCom_info_customs_destruction.setId(com_info_customs_destruction.getId());
        updatedCom_info_customs_destruction.setNumpedimp(UPDATED_NUMPEDIMP);
        updatedCom_info_customs_destruction.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedCom_info_customs_destruction.setCustoms(UPDATED_CUSTOMS);

        restCom_info_customs_destructionMockMvc.perform(put("/api/com-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_info_customs_destruction)))
                .andExpect(status().isOk());

        // Validate the Com_info_customs_destruction in the database
        List<Com_info_customs_destruction> com_info_customs_destructions = com_info_customs_destructionRepository.findAll();
        assertThat(com_info_customs_destructions).hasSize(databaseSizeBeforeUpdate);
        Com_info_customs_destruction testCom_info_customs_destruction = com_info_customs_destructions.get(com_info_customs_destructions.size() - 1);
        assertThat(testCom_info_customs_destruction.getNumpedimp()).isEqualTo(UPDATED_NUMPEDIMP);
        assertThat(testCom_info_customs_destruction.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testCom_info_customs_destruction.getCustoms()).isEqualTo(UPDATED_CUSTOMS);
    }

    @Test
    @Transactional
    public void deleteCom_info_customs_destruction() throws Exception {
        // Initialize the database
        com_info_customs_destructionService.save(com_info_customs_destruction);

        int databaseSizeBeforeDelete = com_info_customs_destructionRepository.findAll().size();

        // Get the com_info_customs_destruction
        restCom_info_customs_destructionMockMvc.perform(delete("/api/com-info-customs-destructions/{id}", com_info_customs_destruction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_info_customs_destruction> com_info_customs_destructions = com_info_customs_destructionRepository.findAll();
        assertThat(com_info_customs_destructions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
