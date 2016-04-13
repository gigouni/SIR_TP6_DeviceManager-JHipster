package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SirTp6App;
import com.mycompany.myapp.domain.Heater;
import com.mycompany.myapp.repository.HeaterRepository;
import com.mycompany.myapp.service.HeaterService;

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
 * Test class for the HeaterResource REST controller.
 *
 * @see HeaterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SirTp6App.class)
@WebAppConfiguration
@IntegrationTest
public class HeaterResourceIntTest {

    private static final String DEFAULT_PLACE = "AAAAA";
    private static final String UPDATED_PLACE = "BBBBB";

    private static final Float DEFAULT_CONSO = 1F;
    private static final Float UPDATED_CONSO = 2F;

    @Inject
    private HeaterRepository heaterRepository;

    @Inject
    private HeaterService heaterService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeaterMockMvc;

    private Heater heater;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeaterResource heaterResource = new HeaterResource();
        ReflectionTestUtils.setField(heaterResource, "heaterService", heaterService);
        this.restHeaterMockMvc = MockMvcBuilders.standaloneSetup(heaterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heater = new Heater();
        heater.setPlace(DEFAULT_PLACE);
        heater.setConso(DEFAULT_CONSO);
    }

    @Test
    @Transactional
    public void createHeater() throws Exception {
        int databaseSizeBeforeCreate = heaterRepository.findAll().size();

        // Create the Heater

        restHeaterMockMvc.perform(post("/api/heaters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heater)))
                .andExpect(status().isCreated());

        // Validate the Heater in the database
        List<Heater> heaters = heaterRepository.findAll();
        assertThat(heaters).hasSize(databaseSizeBeforeCreate + 1);
        Heater testHeater = heaters.get(heaters.size() - 1);
        assertThat(testHeater.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testHeater.getConso()).isEqualTo(DEFAULT_CONSO);
    }

    @Test
    @Transactional
    public void checkPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = heaterRepository.findAll().size();
        // set the field null
        heater.setPlace(null);

        // Create the Heater, which fails.

        restHeaterMockMvc.perform(post("/api/heaters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heater)))
                .andExpect(status().isBadRequest());

        List<Heater> heaters = heaterRepository.findAll();
        assertThat(heaters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeaters() throws Exception {
        // Initialize the database
        heaterRepository.saveAndFlush(heater);

        // Get all the heaters
        restHeaterMockMvc.perform(get("/api/heaters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heater.getId().intValue())))
                .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
                .andExpect(jsonPath("$.[*].conso").value(hasItem(DEFAULT_CONSO.doubleValue())));
    }

    @Test
    @Transactional
    public void getHeater() throws Exception {
        // Initialize the database
        heaterRepository.saveAndFlush(heater);

        // Get the heater
        restHeaterMockMvc.perform(get("/api/heaters/{id}", heater.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heater.getId().intValue()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.conso").value(DEFAULT_CONSO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHeater() throws Exception {
        // Get the heater
        restHeaterMockMvc.perform(get("/api/heaters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeater() throws Exception {
        // Initialize the database
        heaterService.save(heater);

        int databaseSizeBeforeUpdate = heaterRepository.findAll().size();

        // Update the heater
        Heater updatedHeater = new Heater();
        updatedHeater.setId(heater.getId());
        updatedHeater.setPlace(UPDATED_PLACE);
        updatedHeater.setConso(UPDATED_CONSO);

        restHeaterMockMvc.perform(put("/api/heaters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedHeater)))
                .andExpect(status().isOk());

        // Validate the Heater in the database
        List<Heater> heaters = heaterRepository.findAll();
        assertThat(heaters).hasSize(databaseSizeBeforeUpdate);
        Heater testHeater = heaters.get(heaters.size() - 1);
        assertThat(testHeater.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testHeater.getConso()).isEqualTo(UPDATED_CONSO);
    }

    @Test
    @Transactional
    public void deleteHeater() throws Exception {
        // Initialize the database
        heaterService.save(heater);

        int databaseSizeBeforeDelete = heaterRepository.findAll().size();

        // Get the heater
        restHeaterMockMvc.perform(delete("/api/heaters/{id}", heater.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Heater> heaters = heaterRepository.findAll();
        assertThat(heaters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
