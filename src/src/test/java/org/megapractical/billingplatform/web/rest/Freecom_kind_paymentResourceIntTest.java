package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_kind_payment;
import org.megapractical.billingplatform.repository.Freecom_kind_paymentRepository;
import org.megapractical.billingplatform.service.Freecom_kind_paymentService;

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
 * Test class for the Freecom_kind_paymentResource REST controller.
 *
 * @see Freecom_kind_paymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_kind_paymentResourceIntTest {

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
    private Freecom_kind_paymentRepository freecom_kind_paymentRepository;

    @Inject
    private Freecom_kind_paymentService freecom_kind_paymentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_kind_paymentMockMvc;

    private Freecom_kind_payment freecom_kind_payment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_kind_paymentResource freecom_kind_paymentResource = new Freecom_kind_paymentResource();
        ReflectionTestUtils.setField(freecom_kind_paymentResource, "freecom_kind_paymentService", freecom_kind_paymentService);
        this.restFreecom_kind_paymentMockMvc = MockMvcBuilders.standaloneSetup(freecom_kind_paymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_kind_payment = new Freecom_kind_payment();
        freecom_kind_payment.setVersion(DEFAULT_VERSION);
        freecom_kind_payment.setCvepic(DEFAULT_CVEPIC);
        freecom_kind_payment.setFoliosoldon(DEFAULT_FOLIOSOLDON);
        freecom_kind_payment.setArt_piece_name(DEFAULT_ART_PIECE_NAME);
        freecom_kind_payment.setTechnical_art_piece(DEFAULT_TECHNICAL_ART_PIECE);
        freecom_kind_payment.setYear_art_piece(DEFAULT_YEAR_ART_PIECE);
        freecom_kind_payment.setDimensional_art_piece(DEFAULT_DIMENSIONAL_ART_PIECE);
    }

    @Test
    @Transactional
    public void createFreecom_kind_payment() throws Exception {
        int databaseSizeBeforeCreate = freecom_kind_paymentRepository.findAll().size();

        // Create the Freecom_kind_payment

        restFreecom_kind_paymentMockMvc.perform(post("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_kind_payment)))
                .andExpect(status().isCreated());

        // Validate the Freecom_kind_payment in the database
        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_kind_payment testFreecom_kind_payment = freecom_kind_payments.get(freecom_kind_payments.size() - 1);
        assertThat(testFreecom_kind_payment.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_kind_payment.getCvepic()).isEqualTo(DEFAULT_CVEPIC);
        assertThat(testFreecom_kind_payment.getFoliosoldon()).isEqualTo(DEFAULT_FOLIOSOLDON);
        assertThat(testFreecom_kind_payment.getArt_piece_name()).isEqualTo(DEFAULT_ART_PIECE_NAME);
        assertThat(testFreecom_kind_payment.getTechnical_art_piece()).isEqualTo(DEFAULT_TECHNICAL_ART_PIECE);
        assertThat(testFreecom_kind_payment.getYear_art_piece()).isEqualTo(DEFAULT_YEAR_ART_PIECE);
        assertThat(testFreecom_kind_payment.getDimensional_art_piece()).isEqualTo(DEFAULT_DIMENSIONAL_ART_PIECE);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_kind_paymentRepository.findAll().size();
        // set the field null
        freecom_kind_payment.setVersion(null);

        // Create the Freecom_kind_payment, which fails.

        restFreecom_kind_paymentMockMvc.perform(post("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCvepicIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_kind_paymentRepository.findAll().size();
        // set the field null
        freecom_kind_payment.setCvepic(null);

        // Create the Freecom_kind_payment, which fails.

        restFreecom_kind_paymentMockMvc.perform(post("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFoliosoldonIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_kind_paymentRepository.findAll().size();
        // set the field null
        freecom_kind_payment.setFoliosoldon(null);

        // Create the Freecom_kind_payment, which fails.

        restFreecom_kind_paymentMockMvc.perform(post("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArt_piece_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_kind_paymentRepository.findAll().size();
        // set the field null
        freecom_kind_payment.setArt_piece_name(null);

        // Create the Freecom_kind_payment, which fails.

        restFreecom_kind_paymentMockMvc.perform(post("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTechnical_art_pieceIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_kind_paymentRepository.findAll().size();
        // set the field null
        freecom_kind_payment.setTechnical_art_piece(null);

        // Create the Freecom_kind_payment, which fails.

        restFreecom_kind_paymentMockMvc.perform(post("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYear_art_pieceIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_kind_paymentRepository.findAll().size();
        // set the field null
        freecom_kind_payment.setYear_art_piece(null);

        // Create the Freecom_kind_payment, which fails.

        restFreecom_kind_paymentMockMvc.perform(post("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimensional_art_pieceIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_kind_paymentRepository.findAll().size();
        // set the field null
        freecom_kind_payment.setDimensional_art_piece(null);

        // Create the Freecom_kind_payment, which fails.

        restFreecom_kind_paymentMockMvc.perform(post("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_kind_payment)))
                .andExpect(status().isBadRequest());

        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_kind_payments() throws Exception {
        // Initialize the database
        freecom_kind_paymentRepository.saveAndFlush(freecom_kind_payment);

        // Get all the freecom_kind_payments
        restFreecom_kind_paymentMockMvc.perform(get("/api/freecom-kind-payments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_kind_payment.getId().intValue())))
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
    public void getFreecom_kind_payment() throws Exception {
        // Initialize the database
        freecom_kind_paymentRepository.saveAndFlush(freecom_kind_payment);

        // Get the freecom_kind_payment
        restFreecom_kind_paymentMockMvc.perform(get("/api/freecom-kind-payments/{id}", freecom_kind_payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_kind_payment.getId().intValue()))
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
    public void getNonExistingFreecom_kind_payment() throws Exception {
        // Get the freecom_kind_payment
        restFreecom_kind_paymentMockMvc.perform(get("/api/freecom-kind-payments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_kind_payment() throws Exception {
        // Initialize the database
        freecom_kind_paymentService.save(freecom_kind_payment);

        int databaseSizeBeforeUpdate = freecom_kind_paymentRepository.findAll().size();

        // Update the freecom_kind_payment
        Freecom_kind_payment updatedFreecom_kind_payment = new Freecom_kind_payment();
        updatedFreecom_kind_payment.setId(freecom_kind_payment.getId());
        updatedFreecom_kind_payment.setVersion(UPDATED_VERSION);
        updatedFreecom_kind_payment.setCvepic(UPDATED_CVEPIC);
        updatedFreecom_kind_payment.setFoliosoldon(UPDATED_FOLIOSOLDON);
        updatedFreecom_kind_payment.setArt_piece_name(UPDATED_ART_PIECE_NAME);
        updatedFreecom_kind_payment.setTechnical_art_piece(UPDATED_TECHNICAL_ART_PIECE);
        updatedFreecom_kind_payment.setYear_art_piece(UPDATED_YEAR_ART_PIECE);
        updatedFreecom_kind_payment.setDimensional_art_piece(UPDATED_DIMENSIONAL_ART_PIECE);

        restFreecom_kind_paymentMockMvc.perform(put("/api/freecom-kind-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_kind_payment)))
                .andExpect(status().isOk());

        // Validate the Freecom_kind_payment in the database
        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeUpdate);
        Freecom_kind_payment testFreecom_kind_payment = freecom_kind_payments.get(freecom_kind_payments.size() - 1);
        assertThat(testFreecom_kind_payment.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_kind_payment.getCvepic()).isEqualTo(UPDATED_CVEPIC);
        assertThat(testFreecom_kind_payment.getFoliosoldon()).isEqualTo(UPDATED_FOLIOSOLDON);
        assertThat(testFreecom_kind_payment.getArt_piece_name()).isEqualTo(UPDATED_ART_PIECE_NAME);
        assertThat(testFreecom_kind_payment.getTechnical_art_piece()).isEqualTo(UPDATED_TECHNICAL_ART_PIECE);
        assertThat(testFreecom_kind_payment.getYear_art_piece()).isEqualTo(UPDATED_YEAR_ART_PIECE);
        assertThat(testFreecom_kind_payment.getDimensional_art_piece()).isEqualTo(UPDATED_DIMENSIONAL_ART_PIECE);
    }

    @Test
    @Transactional
    public void deleteFreecom_kind_payment() throws Exception {
        // Initialize the database
        freecom_kind_paymentService.save(freecom_kind_payment);

        int databaseSizeBeforeDelete = freecom_kind_paymentRepository.findAll().size();

        // Get the freecom_kind_payment
        restFreecom_kind_paymentMockMvc.perform(delete("/api/freecom-kind-payments/{id}", freecom_kind_payment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_kind_payment> freecom_kind_payments = freecom_kind_paymentRepository.findAll();
        assertThat(freecom_kind_payments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
