package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_paybill_concept;
import org.megapractical.billingplatform.repository.Com_paybill_conceptRepository;
import org.megapractical.billingplatform.service.Com_paybill_conceptService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_paybill_conceptResource REST controller.
 *
 * @see Com_paybill_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_paybill_conceptResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_IDENTIFICATION_NUMBER = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION_NUMBER = "BBBBBBBBBBBBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_EXPEDITION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_EXPEDITION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_EXPEDITION_STR = dateTimeFormatter.format(DEFAULT_DATE_EXPEDITION);
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_CURP = "AAAAA";
    private static final String UPDATED_CURP = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SOCIAL_SECURITY_NUMBER = "AAAAAAAAAAAAAAA";
    private static final String UPDATED_SOCIAL_SECURITY_NUMBER = "BBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Com_paybill_conceptRepository com_paybill_conceptRepository;

    @Inject
    private Com_paybill_conceptService com_paybill_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_paybill_conceptMockMvc;

    private Com_paybill_concept com_paybill_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_paybill_conceptResource com_paybill_conceptResource = new Com_paybill_conceptResource();
        ReflectionTestUtils.setField(com_paybill_conceptResource, "com_paybill_conceptService", com_paybill_conceptService);
        this.restCom_paybill_conceptMockMvc = MockMvcBuilders.standaloneSetup(com_paybill_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_paybill_concept = new Com_paybill_concept();
        com_paybill_concept.setIdentification_number(DEFAULT_IDENTIFICATION_NUMBER);
        com_paybill_concept.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        com_paybill_concept.setRfc(DEFAULT_RFC);
        com_paybill_concept.setCurp(DEFAULT_CURP);
        com_paybill_concept.setName(DEFAULT_NAME);
        com_paybill_concept.setSocial_security_number(DEFAULT_SOCIAL_SECURITY_NUMBER);
        com_paybill_concept.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createCom_paybill_concept() throws Exception {
        int databaseSizeBeforeCreate = com_paybill_conceptRepository.findAll().size();

        // Create the Com_paybill_concept

        restCom_paybill_conceptMockMvc.perform(post("/api/com-paybill-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_paybill_concept)))
                .andExpect(status().isCreated());

        // Validate the Com_paybill_concept in the database
        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Com_paybill_concept testCom_paybill_concept = com_paybill_concepts.get(com_paybill_concepts.size() - 1);
        assertThat(testCom_paybill_concept.getIdentification_number()).isEqualTo(DEFAULT_IDENTIFICATION_NUMBER);
        assertThat(testCom_paybill_concept.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testCom_paybill_concept.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_paybill_concept.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testCom_paybill_concept.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCom_paybill_concept.getSocial_security_number()).isEqualTo(DEFAULT_SOCIAL_SECURITY_NUMBER);
        assertThat(testCom_paybill_concept.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkIdentification_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_paybill_conceptRepository.findAll().size();
        // set the field null
        com_paybill_concept.setIdentification_number(null);

        // Create the Com_paybill_concept, which fails.

        restCom_paybill_conceptMockMvc.perform(post("/api/com-paybill-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_paybill_concept)))
                .andExpect(status().isBadRequest());

        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_paybill_conceptRepository.findAll().size();
        // set the field null
        com_paybill_concept.setDate_expedition(null);

        // Create the Com_paybill_concept, which fails.

        restCom_paybill_conceptMockMvc.perform(post("/api/com-paybill-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_paybill_concept)))
                .andExpect(status().isBadRequest());

        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_paybill_conceptRepository.findAll().size();
        // set the field null
        com_paybill_concept.setRfc(null);

        // Create the Com_paybill_concept, which fails.

        restCom_paybill_conceptMockMvc.perform(post("/api/com-paybill-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_paybill_concept)))
                .andExpect(status().isBadRequest());

        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurpIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_paybill_conceptRepository.findAll().size();
        // set the field null
        com_paybill_concept.setCurp(null);

        // Create the Com_paybill_concept, which fails.

        restCom_paybill_conceptMockMvc.perform(post("/api/com-paybill-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_paybill_concept)))
                .andExpect(status().isBadRequest());

        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_paybill_conceptRepository.findAll().size();
        // set the field null
        com_paybill_concept.setName(null);

        // Create the Com_paybill_concept, which fails.

        restCom_paybill_conceptMockMvc.perform(post("/api/com-paybill-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_paybill_concept)))
                .andExpect(status().isBadRequest());

        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_paybill_conceptRepository.findAll().size();
        // set the field null
        com_paybill_concept.setAmount(null);

        // Create the Com_paybill_concept, which fails.

        restCom_paybill_conceptMockMvc.perform(post("/api/com-paybill-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_paybill_concept)))
                .andExpect(status().isBadRequest());

        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_paybill_concepts() throws Exception {
        // Initialize the database
        com_paybill_conceptRepository.saveAndFlush(com_paybill_concept);

        // Get all the com_paybill_concepts
        restCom_paybill_conceptMockMvc.perform(get("/api/com-paybill-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_paybill_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].identification_number").value(hasItem(DEFAULT_IDENTIFICATION_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].date_expedition").value(hasItem(DEFAULT_DATE_EXPEDITION_STR)))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].social_security_number").value(hasItem(DEFAULT_SOCIAL_SECURITY_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getCom_paybill_concept() throws Exception {
        // Initialize the database
        com_paybill_conceptRepository.saveAndFlush(com_paybill_concept);

        // Get the com_paybill_concept
        restCom_paybill_conceptMockMvc.perform(get("/api/com-paybill-concepts/{id}", com_paybill_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_paybill_concept.getId().intValue()))
            .andExpect(jsonPath("$.identification_number").value(DEFAULT_IDENTIFICATION_NUMBER.toString()))
            .andExpect(jsonPath("$.date_expedition").value(DEFAULT_DATE_EXPEDITION_STR))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.social_security_number").value(DEFAULT_SOCIAL_SECURITY_NUMBER.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_paybill_concept() throws Exception {
        // Get the com_paybill_concept
        restCom_paybill_conceptMockMvc.perform(get("/api/com-paybill-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_paybill_concept() throws Exception {
        // Initialize the database
        com_paybill_conceptService.save(com_paybill_concept);

        int databaseSizeBeforeUpdate = com_paybill_conceptRepository.findAll().size();

        // Update the com_paybill_concept
        Com_paybill_concept updatedCom_paybill_concept = new Com_paybill_concept();
        updatedCom_paybill_concept.setId(com_paybill_concept.getId());
        updatedCom_paybill_concept.setIdentification_number(UPDATED_IDENTIFICATION_NUMBER);
        updatedCom_paybill_concept.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedCom_paybill_concept.setRfc(UPDATED_RFC);
        updatedCom_paybill_concept.setCurp(UPDATED_CURP);
        updatedCom_paybill_concept.setName(UPDATED_NAME);
        updatedCom_paybill_concept.setSocial_security_number(UPDATED_SOCIAL_SECURITY_NUMBER);
        updatedCom_paybill_concept.setAmount(UPDATED_AMOUNT);

        restCom_paybill_conceptMockMvc.perform(put("/api/com-paybill-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_paybill_concept)))
                .andExpect(status().isOk());

        // Validate the Com_paybill_concept in the database
        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeUpdate);
        Com_paybill_concept testCom_paybill_concept = com_paybill_concepts.get(com_paybill_concepts.size() - 1);
        assertThat(testCom_paybill_concept.getIdentification_number()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
        assertThat(testCom_paybill_concept.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testCom_paybill_concept.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_paybill_concept.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testCom_paybill_concept.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCom_paybill_concept.getSocial_security_number()).isEqualTo(UPDATED_SOCIAL_SECURITY_NUMBER);
        assertThat(testCom_paybill_concept.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteCom_paybill_concept() throws Exception {
        // Initialize the database
        com_paybill_conceptService.save(com_paybill_concept);

        int databaseSizeBeforeDelete = com_paybill_conceptRepository.findAll().size();

        // Get the com_paybill_concept
        restCom_paybill_conceptMockMvc.perform(delete("/api/com-paybill-concepts/{id}", com_paybill_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_paybill_concept> com_paybill_concepts = com_paybill_conceptRepository.findAll();
        assertThat(com_paybill_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
