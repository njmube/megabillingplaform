package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_foreign_trade;
import org.megapractical.billingplatform.repository.Freecom_foreign_tradeRepository;
import org.megapractical.billingplatform.service.Freecom_foreign_tradeService;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_foreign_tradeResource REST controller.
 *
 * @see Freecom_foreign_tradeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_foreign_tradeResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_EMITTER_CURP = "AAAAA";
    private static final String UPDATED_EMITTER_CURP = "BBBBB";
    private static final String DEFAULT_RECEIVER_CURP = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_CURP = "BBBBBBBBBB";
    private static final String DEFAULT_RECEIVER_NUMREGIDTRIB = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_RECEIVER_NUMREGIDTRIB = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_ORIGIN_CERTIFICATE = 1;
    private static final Integer UPDATED_ORIGIN_CERTIFICATE = 2;
    private static final String DEFAULT_NUMBER_ORIGIN_CERTIFICATE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMBER_ORIGIN_CERTIFICATE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NUMBER_RELIABLE_EXPORTER = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMBER_RELIABLE_EXPORTER = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_SUBDIVISION = 1;
    private static final Integer UPDATED_SUBDIVISION = 2;
    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_TYPECHANGEUSD = new BigDecimal(1);
    private static final BigDecimal UPDATED_TYPECHANGEUSD = new BigDecimal(2);

    @Inject
    private Freecom_foreign_tradeRepository freecom_foreign_tradeRepository;

    @Inject
    private Freecom_foreign_tradeService freecom_foreign_tradeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_foreign_tradeMockMvc;

    private Freecom_foreign_trade freecom_foreign_trade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_foreign_tradeResource freecom_foreign_tradeResource = new Freecom_foreign_tradeResource();
        ReflectionTestUtils.setField(freecom_foreign_tradeResource, "freecom_foreign_tradeService", freecom_foreign_tradeService);
        this.restFreecom_foreign_tradeMockMvc = MockMvcBuilders.standaloneSetup(freecom_foreign_tradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_foreign_trade = new Freecom_foreign_trade();
        freecom_foreign_trade.setVersion(DEFAULT_VERSION);
        freecom_foreign_trade.setEmitter_curp(DEFAULT_EMITTER_CURP);
        freecom_foreign_trade.setReceiver_curp(DEFAULT_RECEIVER_CURP);
        freecom_foreign_trade.setReceiver_numregidtrib(DEFAULT_RECEIVER_NUMREGIDTRIB);
        freecom_foreign_trade.setOrigin_certificate(DEFAULT_ORIGIN_CERTIFICATE);
        freecom_foreign_trade.setNumber_origin_certificate(DEFAULT_NUMBER_ORIGIN_CERTIFICATE);
        freecom_foreign_trade.setNumber_reliable_exporter(DEFAULT_NUMBER_RELIABLE_EXPORTER);
        freecom_foreign_trade.setSubdivision(DEFAULT_SUBDIVISION);
        freecom_foreign_trade.setObservations(DEFAULT_OBSERVATIONS);
        freecom_foreign_trade.setTypechangeusd(DEFAULT_TYPECHANGEUSD);
    }

    @Test
    @Transactional
    public void createFreecom_foreign_trade() throws Exception {
        int databaseSizeBeforeCreate = freecom_foreign_tradeRepository.findAll().size();

        // Create the Freecom_foreign_trade

        restFreecom_foreign_tradeMockMvc.perform(post("/api/freecom-foreign-trades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_foreign_trade)))
                .andExpect(status().isCreated());

        // Validate the Freecom_foreign_trade in the database
        List<Freecom_foreign_trade> freecom_foreign_trades = freecom_foreign_tradeRepository.findAll();
        assertThat(freecom_foreign_trades).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_foreign_trade testFreecom_foreign_trade = freecom_foreign_trades.get(freecom_foreign_trades.size() - 1);
        assertThat(testFreecom_foreign_trade.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_foreign_trade.getEmitter_curp()).isEqualTo(DEFAULT_EMITTER_CURP);
        assertThat(testFreecom_foreign_trade.getReceiver_curp()).isEqualTo(DEFAULT_RECEIVER_CURP);
        assertThat(testFreecom_foreign_trade.getReceiver_numregidtrib()).isEqualTo(DEFAULT_RECEIVER_NUMREGIDTRIB);
        assertThat(testFreecom_foreign_trade.getOrigin_certificate()).isEqualTo(DEFAULT_ORIGIN_CERTIFICATE);
        assertThat(testFreecom_foreign_trade.getNumber_origin_certificate()).isEqualTo(DEFAULT_NUMBER_ORIGIN_CERTIFICATE);
        assertThat(testFreecom_foreign_trade.getNumber_reliable_exporter()).isEqualTo(DEFAULT_NUMBER_RELIABLE_EXPORTER);
        assertThat(testFreecom_foreign_trade.getSubdivision()).isEqualTo(DEFAULT_SUBDIVISION);
        assertThat(testFreecom_foreign_trade.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);
        assertThat(testFreecom_foreign_trade.getTypechangeusd()).isEqualTo(DEFAULT_TYPECHANGEUSD);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_foreign_tradeRepository.findAll().size();
        // set the field null
        freecom_foreign_trade.setVersion(null);

        // Create the Freecom_foreign_trade, which fails.

        restFreecom_foreign_tradeMockMvc.perform(post("/api/freecom-foreign-trades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_foreign_trade)))
                .andExpect(status().isBadRequest());

        List<Freecom_foreign_trade> freecom_foreign_trades = freecom_foreign_tradeRepository.findAll();
        assertThat(freecom_foreign_trades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiver_numregidtribIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_foreign_tradeRepository.findAll().size();
        // set the field null
        freecom_foreign_trade.setReceiver_numregidtrib(null);

        // Create the Freecom_foreign_trade, which fails.

        restFreecom_foreign_tradeMockMvc.perform(post("/api/freecom-foreign-trades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_foreign_trade)))
                .andExpect(status().isBadRequest());

        List<Freecom_foreign_trade> freecom_foreign_trades = freecom_foreign_tradeRepository.findAll();
        assertThat(freecom_foreign_trades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_foreign_trades() throws Exception {
        // Initialize the database
        freecom_foreign_tradeRepository.saveAndFlush(freecom_foreign_trade);

        // Get all the freecom_foreign_trades
        restFreecom_foreign_tradeMockMvc.perform(get("/api/freecom-foreign-trades?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_foreign_trade.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].emitter_curp").value(hasItem(DEFAULT_EMITTER_CURP.toString())))
                .andExpect(jsonPath("$.[*].receiver_curp").value(hasItem(DEFAULT_RECEIVER_CURP.toString())))
                .andExpect(jsonPath("$.[*].receiver_numregidtrib").value(hasItem(DEFAULT_RECEIVER_NUMREGIDTRIB.toString())))
                .andExpect(jsonPath("$.[*].origin_certificate").value(hasItem(DEFAULT_ORIGIN_CERTIFICATE)))
                .andExpect(jsonPath("$.[*].number_origin_certificate").value(hasItem(DEFAULT_NUMBER_ORIGIN_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].number_reliable_exporter").value(hasItem(DEFAULT_NUMBER_RELIABLE_EXPORTER.toString())))
                .andExpect(jsonPath("$.[*].subdivision").value(hasItem(DEFAULT_SUBDIVISION)))
                .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS.toString())))
                .andExpect(jsonPath("$.[*].typechangeusd").value(hasItem(DEFAULT_TYPECHANGEUSD.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_foreign_trade() throws Exception {
        // Initialize the database
        freecom_foreign_tradeRepository.saveAndFlush(freecom_foreign_trade);

        // Get the freecom_foreign_trade
        restFreecom_foreign_tradeMockMvc.perform(get("/api/freecom-foreign-trades/{id}", freecom_foreign_trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_foreign_trade.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.emitter_curp").value(DEFAULT_EMITTER_CURP.toString()))
            .andExpect(jsonPath("$.receiver_curp").value(DEFAULT_RECEIVER_CURP.toString()))
            .andExpect(jsonPath("$.receiver_numregidtrib").value(DEFAULT_RECEIVER_NUMREGIDTRIB.toString()))
            .andExpect(jsonPath("$.origin_certificate").value(DEFAULT_ORIGIN_CERTIFICATE))
            .andExpect(jsonPath("$.number_origin_certificate").value(DEFAULT_NUMBER_ORIGIN_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.number_reliable_exporter").value(DEFAULT_NUMBER_RELIABLE_EXPORTER.toString()))
            .andExpect(jsonPath("$.subdivision").value(DEFAULT_SUBDIVISION))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS.toString()))
            .andExpect(jsonPath("$.typechangeusd").value(DEFAULT_TYPECHANGEUSD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_foreign_trade() throws Exception {
        // Get the freecom_foreign_trade
        restFreecom_foreign_tradeMockMvc.perform(get("/api/freecom-foreign-trades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_foreign_trade() throws Exception {
        // Initialize the database
        freecom_foreign_tradeService.save(freecom_foreign_trade);

        int databaseSizeBeforeUpdate = freecom_foreign_tradeRepository.findAll().size();

        // Update the freecom_foreign_trade
        Freecom_foreign_trade updatedFreecom_foreign_trade = new Freecom_foreign_trade();
        updatedFreecom_foreign_trade.setId(freecom_foreign_trade.getId());
        updatedFreecom_foreign_trade.setVersion(UPDATED_VERSION);
        updatedFreecom_foreign_trade.setEmitter_curp(UPDATED_EMITTER_CURP);
        updatedFreecom_foreign_trade.setReceiver_curp(UPDATED_RECEIVER_CURP);
        updatedFreecom_foreign_trade.setReceiver_numregidtrib(UPDATED_RECEIVER_NUMREGIDTRIB);
        updatedFreecom_foreign_trade.setOrigin_certificate(UPDATED_ORIGIN_CERTIFICATE);
        updatedFreecom_foreign_trade.setNumber_origin_certificate(UPDATED_NUMBER_ORIGIN_CERTIFICATE);
        updatedFreecom_foreign_trade.setNumber_reliable_exporter(UPDATED_NUMBER_RELIABLE_EXPORTER);
        updatedFreecom_foreign_trade.setSubdivision(UPDATED_SUBDIVISION);
        updatedFreecom_foreign_trade.setObservations(UPDATED_OBSERVATIONS);
        updatedFreecom_foreign_trade.setTypechangeusd(UPDATED_TYPECHANGEUSD);

        restFreecom_foreign_tradeMockMvc.perform(put("/api/freecom-foreign-trades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_foreign_trade)))
                .andExpect(status().isOk());

        // Validate the Freecom_foreign_trade in the database
        List<Freecom_foreign_trade> freecom_foreign_trades = freecom_foreign_tradeRepository.findAll();
        assertThat(freecom_foreign_trades).hasSize(databaseSizeBeforeUpdate);
        Freecom_foreign_trade testFreecom_foreign_trade = freecom_foreign_trades.get(freecom_foreign_trades.size() - 1);
        assertThat(testFreecom_foreign_trade.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_foreign_trade.getEmitter_curp()).isEqualTo(UPDATED_EMITTER_CURP);
        assertThat(testFreecom_foreign_trade.getReceiver_curp()).isEqualTo(UPDATED_RECEIVER_CURP);
        assertThat(testFreecom_foreign_trade.getReceiver_numregidtrib()).isEqualTo(UPDATED_RECEIVER_NUMREGIDTRIB);
        assertThat(testFreecom_foreign_trade.getOrigin_certificate()).isEqualTo(UPDATED_ORIGIN_CERTIFICATE);
        assertThat(testFreecom_foreign_trade.getNumber_origin_certificate()).isEqualTo(UPDATED_NUMBER_ORIGIN_CERTIFICATE);
        assertThat(testFreecom_foreign_trade.getNumber_reliable_exporter()).isEqualTo(UPDATED_NUMBER_RELIABLE_EXPORTER);
        assertThat(testFreecom_foreign_trade.getSubdivision()).isEqualTo(UPDATED_SUBDIVISION);
        assertThat(testFreecom_foreign_trade.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);
        assertThat(testFreecom_foreign_trade.getTypechangeusd()).isEqualTo(UPDATED_TYPECHANGEUSD);
    }

    @Test
    @Transactional
    public void deleteFreecom_foreign_trade() throws Exception {
        // Initialize the database
        freecom_foreign_tradeService.save(freecom_foreign_trade);

        int databaseSizeBeforeDelete = freecom_foreign_tradeRepository.findAll().size();

        // Get the freecom_foreign_trade
        restFreecom_foreign_tradeMockMvc.perform(delete("/api/freecom-foreign-trades/{id}", freecom_foreign_trade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_foreign_trade> freecom_foreign_trades = freecom_foreign_tradeRepository.findAll();
        assertThat(freecom_foreign_trades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
