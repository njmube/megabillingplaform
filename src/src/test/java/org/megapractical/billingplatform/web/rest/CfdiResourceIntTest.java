package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Cfdi;
import org.megapractical.billingplatform.repository.CfdiRepository;
import org.megapractical.billingplatform.service.CfdiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;

import org.megapractical.billingplatform.web.rest.dto.CfdiDTO;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the CfdiResource REST controller.
 *
 * @see CfdiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class CfdiResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_VERSION = "AAA";
    private static final String UPDATED_VERSION = "BBB";
    private static final String DEFAULT_SERIAL = "AAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SERIAL = "BBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_FOLIO = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_FOLIO = "BBBBBBBBBBBBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_EXPEDITION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_EXPEDITION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_EXPEDITION_STR = dateTimeFormatter.format(DEFAULT_DATE_EXPEDITION);
    private static final String DEFAULT_PAYMENT_CONDITIONS = "AAAAA";
    private static final String UPDATED_PAYMENT_CONDITIONS = "BBBBB";
    private static final String DEFAULT_CHANGE_TYPE = "AAAAA";
    private static final String UPDATED_CHANGE_TYPE = "BBBBB";
    private static final String DEFAULT_PLACE_EXPEDITION = "AAAAA";
    private static final String UPDATED_PLACE_EXPEDITION = "BBBBB";
    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBB";
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
    private static final String DEFAULT_NUMBER_CERTIFICATE = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMBER_CERTIFICATE = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CERTIFICATE = "AAAAA";
    private static final String UPDATED_CERTIFICATE = "BBBBB";
    private static final String DEFAULT_WAY_PAYMENT = "AAAAA";
    private static final String UPDATED_WAY_PAYMENT = "BBBBB";
    private static final String DEFAULT_PATH_CFDI = "AAAAA";
    private static final String UPDATED_PATH_CFDI = "BBBBB";

    private static final byte[] DEFAULT_FILEPDF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILEPDF = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILEPDF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILEPDF_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FILEXML = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILEXML = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILEXML_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILEXML_CONTENT_TYPE = "image/png";

    @Inject
    private CfdiRepository cfdiRepository;

    @Inject
    private CfdiService cfdiService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCfdiMockMvc;

    private Cfdi cfdi;

    private CfdiDTO cfdiDTO;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CfdiResource cfdiResource = new CfdiResource();
        ReflectionTestUtils.setField(cfdiResource, "cfdiService", cfdiService);
        this.restCfdiMockMvc = MockMvcBuilders.standaloneSetup(cfdiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cfdiDTO = new CfdiDTO();
        cfdi = new Cfdi();
        cfdi.setVersion(DEFAULT_VERSION);
        cfdi.setSerial(DEFAULT_SERIAL);
        cfdi.setFolio(DEFAULT_FOLIO);
        cfdi.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        cfdi.setPayment_conditions(DEFAULT_PAYMENT_CONDITIONS);
        cfdi.setChange_type(DEFAULT_CHANGE_TYPE);
        cfdi.setPlace_expedition(DEFAULT_PLACE_EXPEDITION);
        cfdi.setAccount_number(DEFAULT_ACCOUNT_NUMBER);
        cfdi.setFolio_fiscal_orig(DEFAULT_FOLIO_FISCAL_ORIG);
        cfdi.setSerial_folio_fiscal_orig(DEFAULT_SERIAL_FOLIO_FISCAL_ORIG);
        cfdi.setDate_folio_fiscal_orig(DEFAULT_DATE_FOLIO_FISCAL_ORIG);
        cfdi.setMont_folio_fiscal_orig(DEFAULT_MONT_FOLIO_FISCAL_ORIG);
        cfdi.setTotal_tax_retention(DEFAULT_TOTAL_TAX_RETENTION);
        cfdi.setTotal_tax_transfered(DEFAULT_TOTAL_TAX_TRANSFERED);
        cfdi.setDiscount(DEFAULT_DISCOUNT);
        cfdi.setDiscount_reason(DEFAULT_DISCOUNT_REASON);
        cfdi.setSubtotal(DEFAULT_SUBTOTAL);
        cfdi.setTotal(DEFAULT_TOTAL);
        cfdi.setAddenda(DEFAULT_ADDENDA);
        cfdi.setNumber_certificate(DEFAULT_NUMBER_CERTIFICATE);
        cfdi.setCertificate(DEFAULT_CERTIFICATE);
        cfdi.setWay_payment(DEFAULT_WAY_PAYMENT);
        cfdi.setPath_cfdi(DEFAULT_PATH_CFDI);
        cfdi.setFilepdf(DEFAULT_FILEPDF);
        cfdi.setFilepdfContentType(DEFAULT_FILEPDF_CONTENT_TYPE);
        cfdi.setFilexml(DEFAULT_FILEXML);
        cfdi.setFilexmlContentType(DEFAULT_FILEXML_CONTENT_TYPE);
        cfdiDTO.setCfdi(cfdi);
    }

    @Test
    @Transactional
    public void createCfdi() throws Exception {
        int databaseSizeBeforeCreate = cfdiRepository.findAll().size();

        // Create the Cfdi

        restCfdiMockMvc.perform(post("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi)))
                .andExpect(status().isCreated());

        // Validate the Cfdi in the database
        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeCreate + 1);
        Cfdi testCfdi = cfdis.get(cfdis.size() - 1);
        assertThat(testCfdi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCfdi.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testCfdi.getFolio()).isEqualTo(DEFAULT_FOLIO);
        assertThat(testCfdi.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testCfdi.getPayment_conditions()).isEqualTo(DEFAULT_PAYMENT_CONDITIONS);
        assertThat(testCfdi.getChange_type()).isEqualTo(DEFAULT_CHANGE_TYPE);
        assertThat(testCfdi.getPlace_expedition()).isEqualTo(DEFAULT_PLACE_EXPEDITION);
        assertThat(testCfdi.getAccount_number()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCfdi.getFolio_fiscal_orig()).isEqualTo(DEFAULT_FOLIO_FISCAL_ORIG);
        assertThat(testCfdi.getSerial_folio_fiscal_orig()).isEqualTo(DEFAULT_SERIAL_FOLIO_FISCAL_ORIG);
        assertThat(testCfdi.getDate_folio_fiscal_orig()).isEqualTo(DEFAULT_DATE_FOLIO_FISCAL_ORIG);
        assertThat(testCfdi.getMont_folio_fiscal_orig()).isEqualTo(DEFAULT_MONT_FOLIO_FISCAL_ORIG);
        assertThat(testCfdi.getTotal_tax_retention()).isEqualTo(DEFAULT_TOTAL_TAX_RETENTION);
        assertThat(testCfdi.getTotal_tax_transfered()).isEqualTo(DEFAULT_TOTAL_TAX_TRANSFERED);
        assertThat(testCfdi.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testCfdi.getDiscount_reason()).isEqualTo(DEFAULT_DISCOUNT_REASON);
        assertThat(testCfdi.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testCfdi.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testCfdi.getAddenda()).isEqualTo(DEFAULT_ADDENDA);
        assertThat(testCfdi.getNumber_certificate()).isEqualTo(DEFAULT_NUMBER_CERTIFICATE);
        assertThat(testCfdi.getCertificate()).isEqualTo(DEFAULT_CERTIFICATE);
        assertThat(testCfdi.getWay_payment()).isEqualTo(DEFAULT_WAY_PAYMENT);
        assertThat(testCfdi.getPath_cfdi()).isEqualTo(DEFAULT_PATH_CFDI);
        assertThat(testCfdi.getFilepdf()).isEqualTo(DEFAULT_FILEPDF);
        assertThat(testCfdi.getFilepdfContentType()).isEqualTo(DEFAULT_FILEPDF_CONTENT_TYPE);
        assertThat(testCfdi.getFilexml()).isEqualTo(DEFAULT_FILEXML);
        assertThat(testCfdi.getFilexmlContentType()).isEqualTo(DEFAULT_FILEXML_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdiRepository.findAll().size();
        // set the field null
        cfdi.setVersion(null);

        // Create the Cfdi, which fails.

        restCfdiMockMvc.perform(post("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi)))
                .andExpect(status().isBadRequest());

        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdiRepository.findAll().size();
        // set the field null
        cfdi.setDate_expedition(null);

        // Create the Cfdi, which fails.

        restCfdiMockMvc.perform(post("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi)))
                .andExpect(status().isBadRequest());

        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlace_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdiRepository.findAll().size();
        // set the field null
        cfdi.setPlace_expedition(null);

        // Create the Cfdi, which fails.

        restCfdiMockMvc.perform(post("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi)))
                .andExpect(status().isBadRequest());

        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdiRepository.findAll().size();
        // set the field null
        cfdi.setSubtotal(null);

        // Create the Cfdi, which fails.

        restCfdiMockMvc.perform(post("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi)))
                .andExpect(status().isBadRequest());

        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdiRepository.findAll().size();
        // set the field null
        cfdi.setTotal(null);

        // Create the Cfdi, which fails.

        restCfdiMockMvc.perform(post("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi)))
                .andExpect(status().isBadRequest());

        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumber_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdiRepository.findAll().size();
        // set the field null
        cfdi.setNumber_certificate(null);

        // Create the Cfdi, which fails.

        restCfdiMockMvc.perform(post("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi)))
                .andExpect(status().isBadRequest());

        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdiRepository.findAll().size();
        // set the field null
        cfdi.setCertificate(null);

        // Create the Cfdi, which fails.

        restCfdiMockMvc.perform(post("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi)))
                .andExpect(status().isBadRequest());

        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCfdis() throws Exception {
        // Initialize the database
        cfdiRepository.saveAndFlush(cfdi);

        // Get all the cfdis
        restCfdiMockMvc.perform(get("/api/cfdis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cfdi.getId().intValue())))
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
                .andExpect(jsonPath("$.[*].number_certificate").value(hasItem(DEFAULT_NUMBER_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].certificate").value(hasItem(DEFAULT_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].way_payment").value(hasItem(DEFAULT_WAY_PAYMENT.toString())))
                .andExpect(jsonPath("$.[*].path_cfdi").value(hasItem(DEFAULT_PATH_CFDI.toString())))
                .andExpect(jsonPath("$.[*].filepdfContentType").value(hasItem(DEFAULT_FILEPDF_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].filepdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILEPDF))))
                .andExpect(jsonPath("$.[*].filexmlContentType").value(hasItem(DEFAULT_FILEXML_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].filexml").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILEXML))));
    }

    @Test
    @Transactional
    public void getCfdi() throws Exception {
        // Initialize the database
        cfdiRepository.saveAndFlush(cfdi);

        // Get the cfdi
        restCfdiMockMvc.perform(get("/api/cfdis/{id}", cfdi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cfdi.getId().intValue()))
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
            .andExpect(jsonPath("$.number_certificate").value(DEFAULT_NUMBER_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.certificate").value(DEFAULT_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.way_payment").value(DEFAULT_WAY_PAYMENT.toString()))
            .andExpect(jsonPath("$.path_cfdi").value(DEFAULT_PATH_CFDI.toString()))
            .andExpect(jsonPath("$.filepdfContentType").value(DEFAULT_FILEPDF_CONTENT_TYPE))
            .andExpect(jsonPath("$.filepdf").value(Base64Utils.encodeToString(DEFAULT_FILEPDF)))
            .andExpect(jsonPath("$.filexmlContentType").value(DEFAULT_FILEXML_CONTENT_TYPE))
            .andExpect(jsonPath("$.filexml").value(Base64Utils.encodeToString(DEFAULT_FILEXML)));
    }

    @Test
    @Transactional
    public void getNonExistingCfdi() throws Exception {
        // Get the cfdi
        restCfdiMockMvc.perform(get("/api/cfdis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCfdi() throws Exception {
        // Initialize the database
        cfdiService.save(cfdiDTO);

        int databaseSizeBeforeUpdate = cfdiRepository.findAll().size();

        // Update the cfdi
        Cfdi updatedCfdi = new Cfdi();
        updatedCfdi.setId(cfdi.getId());
        updatedCfdi.setVersion(UPDATED_VERSION);
        updatedCfdi.setSerial(UPDATED_SERIAL);
        updatedCfdi.setFolio(UPDATED_FOLIO);
        updatedCfdi.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedCfdi.setPayment_conditions(UPDATED_PAYMENT_CONDITIONS);
        updatedCfdi.setChange_type(UPDATED_CHANGE_TYPE);
        updatedCfdi.setPlace_expedition(UPDATED_PLACE_EXPEDITION);
        updatedCfdi.setAccount_number(UPDATED_ACCOUNT_NUMBER);
        updatedCfdi.setFolio_fiscal_orig(UPDATED_FOLIO_FISCAL_ORIG);
        updatedCfdi.setSerial_folio_fiscal_orig(UPDATED_SERIAL_FOLIO_FISCAL_ORIG);
        updatedCfdi.setDate_folio_fiscal_orig(UPDATED_DATE_FOLIO_FISCAL_ORIG);
        updatedCfdi.setMont_folio_fiscal_orig(UPDATED_MONT_FOLIO_FISCAL_ORIG);
        updatedCfdi.setTotal_tax_retention(UPDATED_TOTAL_TAX_RETENTION);
        updatedCfdi.setTotal_tax_transfered(UPDATED_TOTAL_TAX_TRANSFERED);
        updatedCfdi.setDiscount(UPDATED_DISCOUNT);
        updatedCfdi.setDiscount_reason(UPDATED_DISCOUNT_REASON);
        updatedCfdi.setSubtotal(UPDATED_SUBTOTAL);
        updatedCfdi.setTotal(UPDATED_TOTAL);
        updatedCfdi.setAddenda(UPDATED_ADDENDA);
        updatedCfdi.setNumber_certificate(UPDATED_NUMBER_CERTIFICATE);
        updatedCfdi.setCertificate(UPDATED_CERTIFICATE);
        updatedCfdi.setWay_payment(UPDATED_WAY_PAYMENT);
        updatedCfdi.setPath_cfdi(UPDATED_PATH_CFDI);
        updatedCfdi.setFilepdf(UPDATED_FILEPDF);
        updatedCfdi.setFilepdfContentType(UPDATED_FILEPDF_CONTENT_TYPE);
        updatedCfdi.setFilexml(UPDATED_FILEXML);
        updatedCfdi.setFilexmlContentType(UPDATED_FILEXML_CONTENT_TYPE);

        restCfdiMockMvc.perform(put("/api/cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCfdi)))
                .andExpect(status().isOk());

        // Validate the Cfdi in the database
        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeUpdate);
        Cfdi testCfdi = cfdis.get(cfdis.size() - 1);
        assertThat(testCfdi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCfdi.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testCfdi.getFolio()).isEqualTo(UPDATED_FOLIO);
        assertThat(testCfdi.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testCfdi.getPayment_conditions()).isEqualTo(UPDATED_PAYMENT_CONDITIONS);
        assertThat(testCfdi.getChange_type()).isEqualTo(UPDATED_CHANGE_TYPE);
        assertThat(testCfdi.getPlace_expedition()).isEqualTo(UPDATED_PLACE_EXPEDITION);
        assertThat(testCfdi.getAccount_number()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCfdi.getFolio_fiscal_orig()).isEqualTo(UPDATED_FOLIO_FISCAL_ORIG);
        assertThat(testCfdi.getSerial_folio_fiscal_orig()).isEqualTo(UPDATED_SERIAL_FOLIO_FISCAL_ORIG);
        assertThat(testCfdi.getDate_folio_fiscal_orig()).isEqualTo(UPDATED_DATE_FOLIO_FISCAL_ORIG);
        assertThat(testCfdi.getMont_folio_fiscal_orig()).isEqualTo(UPDATED_MONT_FOLIO_FISCAL_ORIG);
        assertThat(testCfdi.getTotal_tax_retention()).isEqualTo(UPDATED_TOTAL_TAX_RETENTION);
        assertThat(testCfdi.getTotal_tax_transfered()).isEqualTo(UPDATED_TOTAL_TAX_TRANSFERED);
        assertThat(testCfdi.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testCfdi.getDiscount_reason()).isEqualTo(UPDATED_DISCOUNT_REASON);
        assertThat(testCfdi.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testCfdi.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testCfdi.getAddenda()).isEqualTo(UPDATED_ADDENDA);
        assertThat(testCfdi.getNumber_certificate()).isEqualTo(UPDATED_NUMBER_CERTIFICATE);
        assertThat(testCfdi.getCertificate()).isEqualTo(UPDATED_CERTIFICATE);
        assertThat(testCfdi.getWay_payment()).isEqualTo(UPDATED_WAY_PAYMENT);
        assertThat(testCfdi.getPath_cfdi()).isEqualTo(UPDATED_PATH_CFDI);
        assertThat(testCfdi.getFilepdf()).isEqualTo(UPDATED_FILEPDF);
        assertThat(testCfdi.getFilepdfContentType()).isEqualTo(UPDATED_FILEPDF_CONTENT_TYPE);
        assertThat(testCfdi.getFilexml()).isEqualTo(UPDATED_FILEXML);
        assertThat(testCfdi.getFilexmlContentType()).isEqualTo(UPDATED_FILEXML_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteCfdi() throws Exception {
        // Initialize the database
        cfdiService.save(cfdiDTO);

        int databaseSizeBeforeDelete = cfdiRepository.findAll().size();

        // Get the cfdi
        restCfdiMockMvc.perform(delete("/api/cfdis/{id}", cfdi.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cfdi> cfdis = cfdiRepository.findAll();
        assertThat(cfdis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
