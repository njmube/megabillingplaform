package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_kind_payment;
import org.megapractical.billingplatform.repository.Com_kind_paymentRepository;
import org.megapractical.billingplatform.service.Com_kind_paymentService;

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
 * Test class for the Com_kind_paymentResource REST controller.
 *
 * @see Com_kind_paymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_kind_paymentResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_CVEPIC = "AAAAA";
    private static final String UPDATED_CVEPIC = "BBBBB";
    private static final String DEFAULT_FOLIOSOLDON = "AAAAA";
    private static final String UPDATED_FOLIOSOLDON = "BBBBB";
    private static final String DEFAULT_ART_PIECE_NAME = "AAAAA";
    private static final String UPDATED_ART_PIECE_NAME = "BBBBB";
    private static final String DEFAULT_TECHNICAL_ART_PIECE = "AAAAA";
    private static final String UPDATED_TECHNICAL_ART_PIECE = "BBBBB";

    private static final Integer DEFAULT_YEAR_ART_PIECE = 1;
    private static final Integer UPDATED_YEAR_ART_PIECE = 2;
    private static final String DEFAULT_DIMENSIONAL_ART_PIECE = "AAAAA";
    private static final String UPDATED_DIMENSIONAL_ART_PIECE = "BBBBB";

    @Inject
    private Com_kind_paymentRepository com_kind_paymentRepository;

    @Inject
    private Com_kind_paymentService com_kind_paymentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_kind_paymentMockMvc;

    private Com_kind_payment com_kind_payment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_kind_paymentResource com_kind_paymentResource = new Com_kind_paymentResource();
        ReflectionTestUtils.setField(com_kind_paymentResource, "com_kind_paymentService", com_kind_paymentService);
        this.restCom_kind_paymentMockMvc = MockMvcBuilders.standaloneSetup(com_kind_paymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_kind_payment = new Com_kind_payment();
        com_kind_payment.setVersion(DEFAULT_VERSION);
        com_kind_payment.setCvepic(DEFAULT_CVEPIC);
        com_kind_payment.setFoliosoldon(DEFAULT_FOLIOSOLDON);
        com_kind_payment.setArt_piece_name(DEFAULT_ART_PIECE_NAME);
        com_kind_payment.setTechnical_art_piece(DEFAULT_TECHNICAL_ART_PIECE);
        com_kind_payment.setYear_art_piece(DEFAULT_YEAR_ART_PIECE);
        com_kind_payment.setDimensional_art_piece(DEFAULT_DIMENSIONAL_ART_PIECE);
    }

    @Test
    @Transactional
    public void createCom_kind_payment() throws Exception {
        int databaseSizeBeforeCreate = com_kind_paymentRepository.findAll().size();

        // Create the Com_kind_payment

        restCom_kind_paymentMockMvc.perform(post("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_kind_payment)))
                .andExpect(status().isCreated());

        // Validate the Com_kind_payment in the database
        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeCreate + 1);
        Com_kind_payment testCom_kind_payment = com_kind_payments.get(com_kind_payments.size() - 1);
        assertThat(testCom_kind_payment.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_kind_payment.getCvepic()).isEqualTo(DEFAULT_CVEPIC);
        assertThat(testCom_kind_payment.getFoliosoldon()).isEqualTo(DEFAULT_FOLIOSOLDON);
        assertThat(testCom_kind_payment.getArt_piece_name()).isEqualTo(DEFAULT_ART_PIECE_NAME);
        assertThat(testCom_kind_payment.getTechnical_art_piece()).isEqualTo(DEFAULT_TECHNICAL_ART_PIECE);
        assertThat(testCom_kind_payment.getYear_art_piece()).isEqualTo(DEFAULT_YEAR_ART_PIECE);
        assertThat(testCom_kind_payment.getDimensional_art_piece()).isEqualTo(DEFAULT_DIMENSIONAL_ART_PIECE);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_kind_paymentRepository.findAll().size();
        // set the field null
        com_kind_payment.setVersion(null);

        // Create the Com_kind_payment, which fails.

        restCom_kind_paymentMockMvc.perform(post("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCvepicIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_kind_paymentRepository.findAll().size();
        // set the field null
        com_kind_payment.setCvepic(null);

        // Create the Com_kind_payment, which fails.

        restCom_kind_paymentMockMvc.perform(post("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFoliosoldonIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_kind_paymentRepository.findAll().size();
        // set the field null
        com_kind_payment.setFoliosoldon(null);

        // Create the Com_kind_payment, which fails.

        restCom_kind_paymentMockMvc.perform(post("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArt_piece_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_kind_paymentRepository.findAll().size();
        // set the field null
        com_kind_payment.setArt_piece_name(null);

        // Create the Com_kind_payment, which fails.

        restCom_kind_paymentMockMvc.perform(post("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTechnical_art_pieceIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_kind_paymentRepository.findAll().size();
        // set the field null
        com_kind_payment.setTechnical_art_piece(null);

        // Create the Com_kind_payment, which fails.

        restCom_kind_paymentMockMvc.perform(post("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYear_art_pieceIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_kind_paymentRepository.findAll().size();
        // set the field null
        com_kind_payment.setYear_art_piece(null);

        // Create the Com_kind_payment, which fails.

        restCom_kind_paymentMockMvc.perform(post("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimensional_art_pieceIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_kind_paymentRepository.findAll().size();
        // set the field null
        com_kind_payment.setDimensional_art_piece(null);

        // Create the Com_kind_payment, which fails.

        restCom_kind_paymentMockMvc.perform(post("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_kind_payments() throws Exception {
        // Initialize the database
        com_kind_paymentRepository.saveAndFlush(com_kind_payment);

        // Get all the com_kind_payments
        restCom_kind_paymentMockMvc.perform(get("/api/com-kind-payments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_kind_payment.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].cvepic").value(hasItem(DEFAULT_CVEPIC.toString())))
                .andExpect(jsonPath("$.[*].foliosoldon").value(hasItem(DEFAULT_FOLIOSOLDON.toString())))
                .andExpect(jsonPath("$.[*].art_piece_name").value(hasItem(DEFAULT_ART_PIECE_NAME.toString())))
                .andExpect(jsonPath("$.[*].technical_art_piece").value(hasItem(DEFAULT_TECHNICAL_ART_PIECE.toString())))
                .andExpect(jsonPath("$.[*].year_art_piece").value(hasItem(DEFAULT_YEAR_ART_PIECE)))
                .andExpect(jsonPath("$.[*].dimensional_art_piece").value(hasItem(DEFAULT_DIMENSIONAL_ART_PIECE.toString())));
    }

    @Test
    @Transactional
    public void getCom_kind_payment() throws Exception {
        // Initialize the database
        com_kind_paymentRepository.saveAndFlush(com_kind_payment);

        // Get the com_kind_payment
        restCom_kind_paymentMockMvc.perform(get("/api/com-kind-payments/{id}", com_kind_payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_kind_payment.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.cvepic").value(DEFAULT_CVEPIC.toString()))
            .andExpect(jsonPath("$.foliosoldon").value(DEFAULT_FOLIOSOLDON.toString()))
            .andExpect(jsonPath("$.art_piece_name").value(DEFAULT_ART_PIECE_NAME.toString()))
            .andExpect(jsonPath("$.technical_art_piece").value(DEFAULT_TECHNICAL_ART_PIECE.toString()))
            .andExpect(jsonPath("$.year_art_piece").value(DEFAULT_YEAR_ART_PIECE))
            .andExpect(jsonPath("$.dimensional_art_piece").value(DEFAULT_DIMENSIONAL_ART_PIECE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_kind_payment() throws Exception {
        // Get the com_kind_payment
        restCom_kind_paymentMockMvc.perform(get("/api/com-kind-payments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_kind_payment() throws Exception {
        // Initialize the database
        com_kind_paymentService.save(com_kind_payment);

        int databaseSizeBeforeUpdate = com_kind_paymentRepository.findAll().size();

        // Update the com_kind_payment
        Com_kind_payment updatedCom_kind_payment = new Com_kind_payment();
        updatedCom_kind_payment.setId(com_kind_payment.getId());
        updatedCom_kind_payment.setVersion(UPDATED_VERSION);
        updatedCom_kind_payment.setCvepic(UPDATED_CVEPIC);
        updatedCom_kind_payment.setFoliosoldon(UPDATED_FOLIOSOLDON);
        updatedCom_kind_payment.setArt_piece_name(UPDATED_ART_PIECE_NAME);
        updatedCom_kind_payment.setTechnical_art_piece(UPDATED_TECHNICAL_ART_PIECE);
        updatedCom_kind_payment.setYear_art_piece(UPDATED_YEAR_ART_PIECE);
        updatedCom_kind_payment.setDimensional_art_piece(UPDATED_DIMENSIONAL_ART_PIECE);

        restCom_kind_paymentMockMvc.perform(put("/api/com-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_kind_payment)))
                .andExpect(status().isOk());

        // Validate the Com_kind_payment in the database
        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeUpdate);
        Com_kind_payment testCom_kind_payment = com_kind_payments.get(com_kind_payments.size() - 1);
        assertThat(testCom_kind_payment.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_kind_payment.getCvepic()).isEqualTo(UPDATED_CVEPIC);
        assertThat(testCom_kind_payment.getFoliosoldon()).isEqualTo(UPDATED_FOLIOSOLDON);
        assertThat(testCom_kind_payment.getArt_piece_name()).isEqualTo(UPDATED_ART_PIECE_NAME);
        assertThat(testCom_kind_payment.getTechnical_art_piece()).isEqualTo(UPDATED_TECHNICAL_ART_PIECE);
        assertThat(testCom_kind_payment.getYear_art_piece()).isEqualTo(UPDATED_YEAR_ART_PIECE);
        assertThat(testCom_kind_payment.getDimensional_art_piece()).isEqualTo(UPDATED_DIMENSIONAL_ART_PIECE);
    }

    @Test
    @Transactional
    public void deleteCom_kind_payment() throws Exception {
        // Initialize the database
        com_kind_paymentService.save(com_kind_payment);

        int databaseSizeBeforeDelete = com_kind_paymentRepository.findAll().size();

        // Get the com_kind_payment
        restCom_kind_paymentMockMvc.perform(delete("/api/com-kind-payments/{id}", com_kind_payment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_kind_payment> com_kind_payments = com_kind_paymentRepository.findAll();
        assertThat(com_kind_payments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
