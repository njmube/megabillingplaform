package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_foreign_trade;
import org.megapractical.billingplatform.repository.Com_foreign_tradeRepository;
import org.megapractical.billingplatform.service.Com_foreign_tradeService;

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
 * Test class for the Com_foreign_tradeResource REST controller.
 *
 * @see Com_foreign_tradeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_foreign_tradeResourceIntTest {

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

    private static final BigDecimal DEFAULT_TOTALUSD = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTALUSD = new BigDecimal(2);

    @Inject
    private Com_foreign_tradeRepository com_foreign_tradeRepository;

    @Inject
    private Com_foreign_tradeService com_foreign_tradeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_foreign_tradeMockMvc;

    private Com_foreign_trade com_foreign_trade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_foreign_tradeResource com_foreign_tradeResource = new Com_foreign_tradeResource();
        ReflectionTestUtils.setField(com_foreign_tradeResource, "com_foreign_tradeService", com_foreign_tradeService);
        this.restCom_foreign_tradeMockMvc = MockMvcBuilders.standaloneSetup(com_foreign_tradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_foreign_trade = new Com_foreign_trade();
        com_foreign_trade.setVersion(DEFAULT_VERSION);
        com_foreign_trade.setEmitter_curp(DEFAULT_EMITTER_CURP);
        com_foreign_trade.setReceiver_curp(DEFAULT_RECEIVER_CURP);
        com_foreign_trade.setReceiver_numregidtrib(DEFAULT_RECEIVER_NUMREGIDTRIB);
        com_foreign_trade.setOrigin_certificate(DEFAULT_ORIGIN_CERTIFICATE);
        com_foreign_trade.setNumber_origin_certificate(DEFAULT_NUMBER_ORIGIN_CERTIFICATE);
        com_foreign_trade.setNumber_reliable_exporter(DEFAULT_NUMBER_RELIABLE_EXPORTER);
        com_foreign_trade.setSubdivision(DEFAULT_SUBDIVISION);
        com_foreign_trade.setObservations(DEFAULT_OBSERVATIONS);
        com_foreign_trade.setTypechangeusd(DEFAULT_TYPECHANGEUSD);
        com_foreign_trade.setTotalusd(DEFAULT_TOTALUSD);
    }

    @Test
    @Transactional
    public void createCom_foreign_trade() throws Exception {
        int databaseSizeBeforeCreate = com_foreign_tradeRepository.findAll().size();

        // Create the Com_foreign_trade

        restCom_foreign_tradeMockMvc.perform(post("/api/com-foreign-trades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_trade)))
                .andExpect(status().isCreated());

        // Validate the Com_foreign_trade in the database
        List<Com_foreign_trade> com_foreign_trades = com_foreign_tradeRepository.findAll();
        assertThat(com_foreign_trades).hasSize(databaseSizeBeforeCreate + 1);
        Com_foreign_trade testCom_foreign_trade = com_foreign_trades.get(com_foreign_trades.size() - 1);
        assertThat(testCom_foreign_trade.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_foreign_trade.getEmitter_curp()).isEqualTo(DEFAULT_EMITTER_CURP);
        assertThat(testCom_foreign_trade.getReceiver_curp()).isEqualTo(DEFAULT_RECEIVER_CURP);
        assertThat(testCom_foreign_trade.getReceiver_numregidtrib()).isEqualTo(DEFAULT_RECEIVER_NUMREGIDTRIB);
        assertThat(testCom_foreign_trade.getOrigin_certificate()).isEqualTo(DEFAULT_ORIGIN_CERTIFICATE);
        assertThat(testCom_foreign_trade.getNumber_origin_certificate()).isEqualTo(DEFAULT_NUMBER_ORIGIN_CERTIFICATE);
        assertThat(testCom_foreign_trade.getNumber_reliable_exporter()).isEqualTo(DEFAULT_NUMBER_RELIABLE_EXPORTER);
        assertThat(testCom_foreign_trade.getSubdivision()).isEqualTo(DEFAULT_SUBDIVISION);
        assertThat(testCom_foreign_trade.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);
        assertThat(testCom_foreign_trade.getTypechangeusd()).isEqualTo(DEFAULT_TYPECHANGEUSD);
        assertThat(testCom_foreign_trade.getTotalusd()).isEqualTo(DEFAULT_TOTALUSD);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_foreign_tradeRepository.findAll().size();
        // set the field null
        com_foreign_trade.setVersion(null);

        // Create the Com_foreign_trade, which fails.

        restCom_foreign_tradeMockMvc.perform(post("/api/com-foreign-trades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_trade)))
                .andExpect(status().isBadRequest());

        List<Com_foreign_trade> com_foreign_trades = com_foreign_tradeRepository.findAll();
        assertThat(com_foreign_trades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiver_numregidtribIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_foreign_tradeRepository.findAll().size();
        // set the field null
        com_foreign_trade.setReceiver_numregidtrib(null);

        // Create the Com_foreign_trade, which fails.

        restCom_foreign_tradeMockMvc.perform(post("/api/com-foreign-trades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_trade)))
                .andExpect(status().isBadRequest());

        List<Com_foreign_trade> com_foreign_trades = com_foreign_tradeRepository.findAll();
        assertThat(com_foreign_trades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_foreign_trades() throws Exception {
        // Initialize the database
        com_foreign_tradeRepository.saveAndFlush(com_foreign_trade);

        // Get all the com_foreign_trades
        restCom_foreign_tradeMockMvc.perform(get("/api/com-foreign-trades?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_foreign_trade.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].emitter_curp").value(hasItem(DEFAULT_EMITTER_CURP.toString())))
                .andExpect(jsonPath("$.[*].receiver_curp").value(hasItem(DEFAULT_RECEIVER_CURP.toString())))
                .andExpect(jsonPath("$.[*].receiver_numregidtrib").value(hasItem(DEFAULT_RECEIVER_NUMREGIDTRIB.toString())))
                .andExpect(jsonPath("$.[*].origin_certificate").value(hasItem(DEFAULT_ORIGIN_CERTIFICATE)))
                .andExpect(jsonPath("$.[*].number_origin_certificate").value(hasItem(DEFAULT_NUMBER_ORIGIN_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].number_reliable_exporter").value(hasItem(DEFAULT_NUMBER_RELIABLE_EXPORTER.toString())))
                .andExpect(jsonPath("$.[*].subdivision").value(hasItem(DEFAULT_SUBDIVISION)))
                .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS.toString())))
                .andExpect(jsonPath("$.[*].typechangeusd").value(hasItem(DEFAULT_TYPECHANGEUSD.intValue())))
                .andExpect(jsonPath("$.[*].totalusd").value(hasItem(DEFAULT_TOTALUSD.intValue())));
    }

    @Test
    @Transactional
    public void getCom_foreign_trade() throws Exception {
        // Initialize the database
        com_foreign_tradeRepository.saveAndFlush(com_foreign_trade);

        // Get the com_foreign_trade
        restCom_foreign_tradeMockMvc.perform(get("/api/com-foreign-trades/{id}", com_foreign_trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_foreign_trade.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.emitter_curp").value(DEFAULT_EMITTER_CURP.toString()))
            .andExpect(jsonPath("$.receiver_curp").value(DEFAULT_RECEIVER_CURP.toString()))
            .andExpect(jsonPath("$.receiver_numregidtrib").value(DEFAULT_RECEIVER_NUMREGIDTRIB.toString()))
            .andExpect(jsonPath("$.origin_certificate").value(DEFAULT_ORIGIN_CERTIFICATE))
            .andExpect(jsonPath("$.number_origin_certificate").value(DEFAULT_NUMBER_ORIGIN_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.number_reliable_exporter").value(DEFAULT_NUMBER_RELIABLE_EXPORTER.toString()))
            .andExpect(jsonPath("$.subdivision").value(DEFAULT_SUBDIVISION))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS.toString()))
            .andExpect(jsonPath("$.typechangeusd").value(DEFAULT_TYPECHANGEUSD.intValue()))
            .andExpect(jsonPath("$.totalusd").value(DEFAULT_TOTALUSD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_foreign_trade() throws Exception {
        // Get the com_foreign_trade
        restCom_foreign_tradeMockMvc.perform(get("/api/com-foreign-trades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_foreign_trade() throws Exception {
        // Initialize the database
        com_foreign_tradeService.save(com_foreign_trade);

        int databaseSizeBeforeUpdate = com_foreign_tradeRepository.findAll().size();

        // Update the com_foreign_trade
        Com_foreign_trade updatedCom_foreign_trade = new Com_foreign_trade();
        updatedCom_foreign_trade.setId(com_foreign_trade.getId());
        updatedCom_foreign_trade.setVersion(UPDATED_VERSION);
        updatedCom_foreign_trade.setEmitter_curp(UPDATED_EMITTER_CURP);
        updatedCom_foreign_trade.setReceiver_curp(UPDATED_RECEIVER_CURP);
        updatedCom_foreign_trade.setReceiver_numregidtrib(UPDATED_RECEIVER_NUMREGIDTRIB);
        updatedCom_foreign_trade.setOrigin_certificate(UPDATED_ORIGIN_CERTIFICATE);
        updatedCom_foreign_trade.setNumber_origin_certificate(UPDATED_NUMBER_ORIGIN_CERTIFICATE);
        updatedCom_foreign_trade.setNumber_reliable_exporter(UPDATED_NUMBER_RELIABLE_EXPORTER);
        updatedCom_foreign_trade.setSubdivision(UPDATED_SUBDIVISION);
        updatedCom_foreign_trade.setObservations(UPDATED_OBSERVATIONS);
        updatedCom_foreign_trade.setTypechangeusd(UPDATED_TYPECHANGEUSD);
        updatedCom_foreign_trade.setTotalusd(UPDATED_TOTALUSD);

        restCom_foreign_tradeMockMvc.perform(put("/api/com-foreign-trades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_foreign_trade)))
                .andExpect(status().isOk());

        // Validate the Com_foreign_trade in the database
        List<Com_foreign_trade> com_foreign_trades = com_foreign_tradeRepository.findAll();
        assertThat(com_foreign_trades).hasSize(databaseSizeBeforeUpdate);
        Com_foreign_trade testCom_foreign_trade = com_foreign_trades.get(com_foreign_trades.size() - 1);
        assertThat(testCom_foreign_trade.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_foreign_trade.getEmitter_curp()).isEqualTo(UPDATED_EMITTER_CURP);
        assertThat(testCom_foreign_trade.getReceiver_curp()).isEqualTo(UPDATED_RECEIVER_CURP);
        assertThat(testCom_foreign_trade.getReceiver_numregidtrib()).isEqualTo(UPDATED_RECEIVER_NUMREGIDTRIB);
        assertThat(testCom_foreign_trade.getOrigin_certificate()).isEqualTo(UPDATED_ORIGIN_CERTIFICATE);
        assertThat(testCom_foreign_trade.getNumber_origin_certificate()).isEqualTo(UPDATED_NUMBER_ORIGIN_CERTIFICATE);
        assertThat(testCom_foreign_trade.getNumber_reliable_exporter()).isEqualTo(UPDATED_NUMBER_RELIABLE_EXPORTER);
        assertThat(testCom_foreign_trade.getSubdivision()).isEqualTo(UPDATED_SUBDIVISION);
        assertThat(testCom_foreign_trade.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);
        assertThat(testCom_foreign_trade.getTypechangeusd()).isEqualTo(UPDATED_TYPECHANGEUSD);
        assertThat(testCom_foreign_trade.getTotalusd()).isEqualTo(UPDATED_TOTALUSD);
    }

    @Test
    @Transactional
    public void deleteCom_foreign_trade() throws Exception {
        // Initialize the database
        com_foreign_tradeService.save(com_foreign_trade);

        int databaseSizeBeforeDelete = com_foreign_tradeRepository.findAll().size();

        // Get the com_foreign_trade
        restCom_foreign_tradeMockMvc.perform(delete("/api/com-foreign-trades/{id}", com_foreign_trade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_foreign_trade> com_foreign_trades = com_foreign_tradeRepository.findAll();
        assertThat(com_foreign_trades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
