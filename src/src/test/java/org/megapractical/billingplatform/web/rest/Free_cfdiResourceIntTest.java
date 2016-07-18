package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_cfdi;
import org.megapractical.billingplatform.repository.Free_cfdiRepository;
import org.megapractical.billingplatform.service.Free_cfdiService;

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
 * Test class for the Free_cfdiResource REST controller.
 *
 * @see Free_cfdiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_cfdiResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_VERSION = "AAA";
    private static final String UPDATED_VERSION = "BBB";
    private static final String DEFAULT_SERIAL = "A";
    private static final String UPDATED_SERIAL = "B";
    private static final String DEFAULT_FOLIO = "A";
    private static final String UPDATED_FOLIO = "B";

    private static final ZonedDateTime DEFAULT_DATE_EXPEDITION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_EXPEDITION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_EXPEDITION_STR = dateTimeFormatter.format(DEFAULT_DATE_EXPEDITION);
    private static final String DEFAULT_PAYMENT_CONDITIONS = "AAAAA";
    private static final String UPDATED_PAYMENT_CONDITIONS = "BBBBB";
    private static final String DEFAULT_CHANGE_TYPE = "AAAAA";
    private static final String UPDATED_CHANGE_TYPE = "BBBBB";
    private static final String DEFAULT_PLACE_EXPEDITION = "AAAAA";
    private static final String UPDATED_PLACE_EXPEDITION = "BBBBB";
    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBB";
    private static final String DEFAULT_FOLIO_FISCAL_ORIG = "AAAAA";
    private static final String UPDATED_FOLIO_FISCAL_ORIG = "BBBBB";
    private static final String DEFAULT_SERIAL_FOLIO_FISCAL_ORIG = "AAAAA";
    private static final String UPDATED_SERIAL_FOLIO_FISCAL_ORIG = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE_FOLIO_FISCAL_ORIG = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_FOLIO_FISCAL_ORIG = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_FOLIO_FISCAL_ORIG_STR = dateTimeFormatter.format(DEFAULT_DATE_FOLIO_FISCAL_ORIG);

    private static final BigDecimal DEFAULT_MONT_FOLIO_FISCAL_ORIG = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONT_FOLIO_FISCAL_ORIG = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_TAX_RETENTION = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_RETENTION = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_TAX_TRANSFERED = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_TRANSFERED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);
    private static final String DEFAULT_DISCOUNT_REASON = "AAAAA";
    private static final String UPDATED_DISCOUNT_REASON = "BBBBB";

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);
    private static final String DEFAULT_ADDENDA = "AAAAA";
    private static final String UPDATED_ADDENDA = "BBBBB";
    private static final String DEFAULT_STAMP = "AAAAA";
    private static final String UPDATED_STAMP = "BBBBB";
    private static final String DEFAULT_NO_CERTIFICATE = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NO_CERTIFICATE = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CERTIFICATE = "AAAAA";
    private static final String UPDATED_CERTIFICATE = "BBBBB";
    private static final String DEFAULT_WAY_PAYMENT = "AAAAA";
    private static final String UPDATED_WAY_PAYMENT = "BBBBB";
    private static final String DEFAULT_PATH_CFDI = "AAAAA";
    private static final String UPDATED_PATH_CFDI = "BBBBB";

    @Inject
    private Free_cfdiRepository free_cfdiRepository;

    @Inject
    private Free_cfdiService free_cfdiService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_cfdiMockMvc;

    private Free_cfdi free_cfdi;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_cfdiResource free_cfdiResource = new Free_cfdiResource();
        ReflectionTestUtils.setField(free_cfdiResource, "free_cfdiService", free_cfdiService);
        this.restFree_cfdiMockMvc = MockMvcBuilders.standaloneSetup(free_cfdiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_cfdi = new Free_cfdi();
        free_cfdi.setVersion(DEFAULT_VERSION);
        free_cfdi.setSerial(DEFAULT_SERIAL);
        free_cfdi.setFolio(DEFAULT_FOLIO);
        free_cfdi.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        free_cfdi.setPayment_conditions(DEFAULT_PAYMENT_CONDITIONS);
        free_cfdi.setChange_type(DEFAULT_CHANGE_TYPE);
        free_cfdi.setPlace_expedition(DEFAULT_PLACE_EXPEDITION);
        free_cfdi.setAccount_number(DEFAULT_ACCOUNT_NUMBER);
        free_cfdi.setFolio_fiscal_orig(DEFAULT_FOLIO_FISCAL_ORIG);
        free_cfdi.setSerial_folio_fiscal_orig(DEFAULT_SERIAL_FOLIO_FISCAL_ORIG);
        free_cfdi.setDate_folio_fiscal_orig(DEFAULT_DATE_FOLIO_FISCAL_ORIG);
        free_cfdi.setMont_folio_fiscal_orig(DEFAULT_MONT_FOLIO_FISCAL_ORIG);
        free_cfdi.setTotal_tax_retention(DEFAULT_TOTAL_TAX_RETENTION);
        free_cfdi.setTotal_tax_transfered(DEFAULT_TOTAL_TAX_TRANSFERED);
        free_cfdi.setDiscount(DEFAULT_DISCOUNT);
        free_cfdi.setDiscount_reason(DEFAULT_DISCOUNT_REASON);
        free_cfdi.setSubtotal(DEFAULT_SUBTOTAL);
        free_cfdi.setTotal(DEFAULT_TOTAL);
        free_cfdi.setAddenda(DEFAULT_ADDENDA);
        free_cfdi.setStamp(DEFAULT_STAMP);
        free_cfdi.setNo_certificate(DEFAULT_NO_CERTIFICATE);
        free_cfdi.setCertificate(DEFAULT_CERTIFICATE);
        free_cfdi.setWay_payment(DEFAULT_WAY_PAYMENT);
        free_cfdi.setPath_cfdi(DEFAULT_PATH_CFDI);
    }

    @Test
    @Transactional
    public void createFree_cfdi() throws Exception {
        /*
        int databaseSizeBeforeCreate = free_cfdiRepository.findAll().size();

        // Create the Free_cfdi

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isCreated());

        // Validate the Free_cfdi in the database
        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeCreate + 1);
        Free_cfdi testFree_cfdi = free_cfdis.get(free_cfdis.size() - 1);
        assertThat(testFree_cfdi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFree_cfdi.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testFree_cfdi.getFolio()).isEqualTo(DEFAULT_FOLIO);
        assertThat(testFree_cfdi.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testFree_cfdi.getPayment_conditions()).isEqualTo(DEFAULT_PAYMENT_CONDITIONS);
        assertThat(testFree_cfdi.getChange_type()).isEqualTo(DEFAULT_CHANGE_TYPE);
        assertThat(testFree_cfdi.getPlace_expedition()).isEqualTo(DEFAULT_PLACE_EXPEDITION);
        assertThat(testFree_cfdi.getAccount_number()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testFree_cfdi.getFolio_fiscal_orig()).isEqualTo(DEFAULT_FOLIO_FISCAL_ORIG);
        assertThat(testFree_cfdi.getSerial_folio_fiscal_orig()).isEqualTo(DEFAULT_SERIAL_FOLIO_FISCAL_ORIG);
        assertThat(testFree_cfdi.getDate_folio_fiscal_orig()).isEqualTo(DEFAULT_DATE_FOLIO_FISCAL_ORIG);
        assertThat(testFree_cfdi.getMont_folio_fiscal_orig()).isEqualTo(DEFAULT_MONT_FOLIO_FISCAL_ORIG);
        assertThat(testFree_cfdi.getTotal_tax_retention()).isEqualTo(DEFAULT_TOTAL_TAX_RETENTION);
        assertThat(testFree_cfdi.getTotal_tax_transfered()).isEqualTo(DEFAULT_TOTAL_TAX_TRANSFERED);
        assertThat(testFree_cfdi.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testFree_cfdi.getDiscount_reason()).isEqualTo(DEFAULT_DISCOUNT_REASON);
        assertThat(testFree_cfdi.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testFree_cfdi.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testFree_cfdi.getAddenda()).isEqualTo(DEFAULT_ADDENDA);
        assertThat(testFree_cfdi.getStamp()).isEqualTo(DEFAULT_STAMP);
        assertThat(testFree_cfdi.getNo_certificate()).isEqualTo(DEFAULT_NO_CERTIFICATE);
        assertThat(testFree_cfdi.getCertificate()).isEqualTo(DEFAULT_CERTIFICATE);
        assertThat(testFree_cfdi.getWay_payment()).isEqualTo(DEFAULT_WAY_PAYMENT);
        assertThat(testFree_cfdi.getPath_cfdi()).isEqualTo(DEFAULT_PATH_CFDI);*/
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setVersion(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setDate_expedition(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkPlace_expeditionIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setPlace_expedition(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setSubtotal(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setTotal(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkStampIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setStamp(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkNo_certificateIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setNo_certificate(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkCertificateIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setCertificate(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkWay_paymentIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_cfdiRepository.findAll().size();
        // set the field null
        free_cfdi.setWay_payment(null);

        // Create the Free_cfdi, which fails.

        restFree_cfdiMockMvc.perform(post("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_cfdi)))
                .andExpect(status().isBadRequest());

        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void getAllFree_cfdis() throws Exception {
        /*
        // Initialize the database
        free_cfdiRepository.saveAndFlush(free_cfdi);

        // Get all the free_cfdis
        restFree_cfdiMockMvc.perform(get("/api/free-cfdis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_cfdi.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL.toString())))
                .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO.toString())))
                .andExpect(jsonPath("$.[*].date_expedition").value(hasItem(DEFAULT_DATE_EXPEDITION_STR)))
                .andExpect(jsonPath("$.[*].payment_conditions").value(hasItem(DEFAULT_PAYMENT_CONDITIONS.toString())))
                .andExpect(jsonPath("$.[*].change_type").value(hasItem(DEFAULT_CHANGE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].place_expedition").value(hasItem(DEFAULT_PLACE_EXPEDITION.toString())))
                .andExpect(jsonPath("$.[*].account_number").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].folio_fiscal_orig").value(hasItem(DEFAULT_FOLIO_FISCAL_ORIG.toString())))
                .andExpect(jsonPath("$.[*].serial_folio_fiscal_orig").value(hasItem(DEFAULT_SERIAL_FOLIO_FISCAL_ORIG.toString())))
                .andExpect(jsonPath("$.[*].date_folio_fiscal_orig").value(hasItem(DEFAULT_DATE_FOLIO_FISCAL_ORIG_STR)))
                .andExpect(jsonPath("$.[*].mont_folio_fiscal_orig").value(hasItem(DEFAULT_MONT_FOLIO_FISCAL_ORIG.intValue())))
                .andExpect(jsonPath("$.[*].total_tax_retention").value(hasItem(DEFAULT_TOTAL_TAX_RETENTION.intValue())))
                .andExpect(jsonPath("$.[*].total_tax_transfered").value(hasItem(DEFAULT_TOTAL_TAX_TRANSFERED.intValue())))
                .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].discount_reason").value(hasItem(DEFAULT_DISCOUNT_REASON.toString())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
                .andExpect(jsonPath("$.[*].addenda").value(hasItem(DEFAULT_ADDENDA.toString())))
                .andExpect(jsonPath("$.[*].stamp").value(hasItem(DEFAULT_STAMP.toString())))
                .andExpect(jsonPath("$.[*].no_certificate").value(hasItem(DEFAULT_NO_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].certificate").value(hasItem(DEFAULT_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].way_payment").value(hasItem(DEFAULT_WAY_PAYMENT.toString())))
                .andExpect(jsonPath("$.[*].path_cfdi").value(hasItem(DEFAULT_PATH_CFDI.toString())));*/
    }

    @Test
    @Transactional
    public void getFree_cfdi() throws Exception {
        /*
        // Initialize the database
        free_cfdiRepository.saveAndFlush(free_cfdi);

        // Get the free_cfdi
        restFree_cfdiMockMvc.perform(get("/api/free-cfdis/{id}", free_cfdi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_cfdi.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL.toString()))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO.toString()))
            .andExpect(jsonPath("$.date_expedition").value(DEFAULT_DATE_EXPEDITION_STR))
            .andExpect(jsonPath("$.payment_conditions").value(DEFAULT_PAYMENT_CONDITIONS.toString()))
            .andExpect(jsonPath("$.change_type").value(DEFAULT_CHANGE_TYPE.toString()))
            .andExpect(jsonPath("$.place_expedition").value(DEFAULT_PLACE_EXPEDITION.toString()))
            .andExpect(jsonPath("$.account_number").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.folio_fiscal_orig").value(DEFAULT_FOLIO_FISCAL_ORIG.toString()))
            .andExpect(jsonPath("$.serial_folio_fiscal_orig").value(DEFAULT_SERIAL_FOLIO_FISCAL_ORIG.toString()))
            .andExpect(jsonPath("$.date_folio_fiscal_orig").value(DEFAULT_DATE_FOLIO_FISCAL_ORIG_STR))
            .andExpect(jsonPath("$.mont_folio_fiscal_orig").value(DEFAULT_MONT_FOLIO_FISCAL_ORIG.intValue()))
            .andExpect(jsonPath("$.total_tax_retention").value(DEFAULT_TOTAL_TAX_RETENTION.intValue()))
            .andExpect(jsonPath("$.total_tax_transfered").value(DEFAULT_TOTAL_TAX_TRANSFERED.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.discount_reason").value(DEFAULT_DISCOUNT_REASON.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.addenda").value(DEFAULT_ADDENDA.toString()))
            .andExpect(jsonPath("$.stamp").value(DEFAULT_STAMP.toString()))
            .andExpect(jsonPath("$.no_certificate").value(DEFAULT_NO_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.certificate").value(DEFAULT_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.way_payment").value(DEFAULT_WAY_PAYMENT.toString()))
            .andExpect(jsonPath("$.path_cfdi").value(DEFAULT_PATH_CFDI.toString()));*/
    }

    @Test
    @Transactional
    public void getNonExistingFree_cfdi() throws Exception {
        /*
        // Get the free_cfdi
        restFree_cfdiMockMvc.perform(get("/api/free-cfdis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());*/
    }

    @Test
    @Transactional
    public void updateFree_cfdi() throws Exception {
        /*
        // Initialize the database
        free_cfdiService.save(free_cfdi);

        int databaseSizeBeforeUpdate = free_cfdiRepository.findAll().size();

        // Update the free_cfdi
        Free_cfdi updatedFree_cfdi = new Free_cfdi();
        updatedFree_cfdi.setId(free_cfdi.getId());
        updatedFree_cfdi.setVersion(UPDATED_VERSION);
        updatedFree_cfdi.setSerial(UPDATED_SERIAL);
        updatedFree_cfdi.setFolio(UPDATED_FOLIO);
        updatedFree_cfdi.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedFree_cfdi.setPayment_conditions(UPDATED_PAYMENT_CONDITIONS);
        updatedFree_cfdi.setChange_type(UPDATED_CHANGE_TYPE);
        updatedFree_cfdi.setPlace_expedition(UPDATED_PLACE_EXPEDITION);
        updatedFree_cfdi.setAccount_number(UPDATED_ACCOUNT_NUMBER);
        updatedFree_cfdi.setFolio_fiscal_orig(UPDATED_FOLIO_FISCAL_ORIG);
        updatedFree_cfdi.setSerial_folio_fiscal_orig(UPDATED_SERIAL_FOLIO_FISCAL_ORIG);
        updatedFree_cfdi.setDate_folio_fiscal_orig(UPDATED_DATE_FOLIO_FISCAL_ORIG);
        updatedFree_cfdi.setMont_folio_fiscal_orig(UPDATED_MONT_FOLIO_FISCAL_ORIG);
        updatedFree_cfdi.setTotal_tax_retention(UPDATED_TOTAL_TAX_RETENTION);
        updatedFree_cfdi.setTotal_tax_transfered(UPDATED_TOTAL_TAX_TRANSFERED);
        updatedFree_cfdi.setDiscount(UPDATED_DISCOUNT);
        updatedFree_cfdi.setDiscount_reason(UPDATED_DISCOUNT_REASON);
        updatedFree_cfdi.setSubtotal(UPDATED_SUBTOTAL);
        updatedFree_cfdi.setTotal(UPDATED_TOTAL);
        updatedFree_cfdi.setAddenda(UPDATED_ADDENDA);
        updatedFree_cfdi.setStamp(UPDATED_STAMP);
        updatedFree_cfdi.setNo_certificate(UPDATED_NO_CERTIFICATE);
        updatedFree_cfdi.setCertificate(UPDATED_CERTIFICATE);
        updatedFree_cfdi.setWay_payment(UPDATED_WAY_PAYMENT);
        updatedFree_cfdi.setPath_cfdi(UPDATED_PATH_CFDI);

        restFree_cfdiMockMvc.perform(put("/api/free-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_cfdi)))
                .andExpect(status().isOk());

        // Validate the Free_cfdi in the database
        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeUpdate);
        Free_cfdi testFree_cfdi = free_cfdis.get(free_cfdis.size() - 1);
        assertThat(testFree_cfdi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFree_cfdi.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testFree_cfdi.getFolio()).isEqualTo(UPDATED_FOLIO);
        assertThat(testFree_cfdi.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testFree_cfdi.getPayment_conditions()).isEqualTo(UPDATED_PAYMENT_CONDITIONS);
        assertThat(testFree_cfdi.getChange_type()).isEqualTo(UPDATED_CHANGE_TYPE);
        assertThat(testFree_cfdi.getPlace_expedition()).isEqualTo(UPDATED_PLACE_EXPEDITION);
        assertThat(testFree_cfdi.getAccount_number()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testFree_cfdi.getFolio_fiscal_orig()).isEqualTo(UPDATED_FOLIO_FISCAL_ORIG);
        assertThat(testFree_cfdi.getSerial_folio_fiscal_orig()).isEqualTo(UPDATED_SERIAL_FOLIO_FISCAL_ORIG);
        assertThat(testFree_cfdi.getDate_folio_fiscal_orig()).isEqualTo(UPDATED_DATE_FOLIO_FISCAL_ORIG);
        assertThat(testFree_cfdi.getMont_folio_fiscal_orig()).isEqualTo(UPDATED_MONT_FOLIO_FISCAL_ORIG);
        assertThat(testFree_cfdi.getTotal_tax_retention()).isEqualTo(UPDATED_TOTAL_TAX_RETENTION);
        assertThat(testFree_cfdi.getTotal_tax_transfered()).isEqualTo(UPDATED_TOTAL_TAX_TRANSFERED);
        assertThat(testFree_cfdi.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testFree_cfdi.getDiscount_reason()).isEqualTo(UPDATED_DISCOUNT_REASON);
        assertThat(testFree_cfdi.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testFree_cfdi.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testFree_cfdi.getAddenda()).isEqualTo(UPDATED_ADDENDA);
        assertThat(testFree_cfdi.getStamp()).isEqualTo(UPDATED_STAMP);
        assertThat(testFree_cfdi.getNo_certificate()).isEqualTo(UPDATED_NO_CERTIFICATE);
        assertThat(testFree_cfdi.getCertificate()).isEqualTo(UPDATED_CERTIFICATE);
        assertThat(testFree_cfdi.getWay_payment()).isEqualTo(UPDATED_WAY_PAYMENT);
        assertThat(testFree_cfdi.getPath_cfdi()).isEqualTo(UPDATED_PATH_CFDI);*/
    }

    @Test
    @Transactional
    public void deleteFree_cfdi() throws Exception {
        /*
        // Initialize the database
        free_cfdiService.save(free_cfdi);

        int databaseSizeBeforeDelete = free_cfdiRepository.findAll().size();

        // Get the free_cfdi
        restFree_cfdiMockMvc.perform(delete("/api/free-cfdis/{id}", free_cfdi.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_cfdi> free_cfdis = free_cfdiRepository.findAll();
        assertThat(free_cfdis).hasSize(databaseSizeBeforeDelete - 1);*/
    }
}
