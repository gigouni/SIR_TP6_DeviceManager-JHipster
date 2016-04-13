package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SirTp6App;
import com.mycompany.myapp.domain.ElectronicDevice;
import com.mycompany.myapp.repository.ElectronicDeviceRepository;
import com.mycompany.myapp.service.ElectronicDeviceService;

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
 * Test class for the ElectronicDeviceResource REST controller.
 *
 * @see ElectronicDeviceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SirTp6App.class)
@WebAppConfiguration
@IntegrationTest
public class ElectronicDeviceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Float DEFAULT_CONSO = 1F;
    private static final Float UPDATED_CONSO = 2F;

    @Inject
    private ElectronicDeviceRepository electronicDeviceRepository;

    @Inject
    private ElectronicDeviceService electronicDeviceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restElectronicDeviceMockMvc;

    private ElectronicDevice electronicDevice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ElectronicDeviceResource electronicDeviceResource = new ElectronicDeviceResource();
        ReflectionTestUtils.setField(electronicDeviceResource, "electronicDeviceService", electronicDeviceService);
        this.restElectronicDeviceMockMvc = MockMvcBuilders.standaloneSetup(electronicDeviceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        electronicDevice = new ElectronicDevice();
        electronicDevice.setName(DEFAULT_NAME);
        electronicDevice.setConso(DEFAULT_CONSO);
    }

    @Test
    @Transactional
    public void createElectronicDevice() throws Exception {
        int databaseSizeBeforeCreate = electronicDeviceRepository.findAll().size();

        // Create the ElectronicDevice

        restElectronicDeviceMockMvc.perform(post("/api/electronic-devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(electronicDevice)))
                .andExpect(status().isCreated());

        // Validate the ElectronicDevice in the database
        List<ElectronicDevice> electronicDevices = electronicDeviceRepository.findAll();
        assertThat(electronicDevices).hasSize(databaseSizeBeforeCreate + 1);
        ElectronicDevice testElectronicDevice = electronicDevices.get(electronicDevices.size() - 1);
        assertThat(testElectronicDevice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testElectronicDevice.getConso()).isEqualTo(DEFAULT_CONSO);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicDeviceRepository.findAll().size();
        // set the field null
        electronicDevice.setName(null);

        // Create the ElectronicDevice, which fails.

        restElectronicDeviceMockMvc.perform(post("/api/electronic-devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(electronicDevice)))
                .andExpect(status().isBadRequest());

        List<ElectronicDevice> electronicDevices = electronicDeviceRepository.findAll();
        assertThat(electronicDevices).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllElectronicDevices() throws Exception {
        // Initialize the database
        electronicDeviceRepository.saveAndFlush(electronicDevice);

        // Get all the electronicDevices
        restElectronicDeviceMockMvc.perform(get("/api/electronic-devices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(electronicDevice.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].conso").value(hasItem(DEFAULT_CONSO.doubleValue())));
    }

    @Test
    @Transactional
    public void getElectronicDevice() throws Exception {
        // Initialize the database
        electronicDeviceRepository.saveAndFlush(electronicDevice);

        // Get the electronicDevice
        restElectronicDeviceMockMvc.perform(get("/api/electronic-devices/{id}", electronicDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(electronicDevice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.conso").value(DEFAULT_CONSO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingElectronicDevice() throws Exception {
        // Get the electronicDevice
        restElectronicDeviceMockMvc.perform(get("/api/electronic-devices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElectronicDevice() throws Exception {
        // Initialize the database
        electronicDeviceService.save(electronicDevice);

        int databaseSizeBeforeUpdate = electronicDeviceRepository.findAll().size();

        // Update the electronicDevice
        ElectronicDevice updatedElectronicDevice = new ElectronicDevice();
        updatedElectronicDevice.setId(electronicDevice.getId());
        updatedElectronicDevice.setName(UPDATED_NAME);
        updatedElectronicDevice.setConso(UPDATED_CONSO);

        restElectronicDeviceMockMvc.perform(put("/api/electronic-devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedElectronicDevice)))
                .andExpect(status().isOk());

        // Validate the ElectronicDevice in the database
        List<ElectronicDevice> electronicDevices = electronicDeviceRepository.findAll();
        assertThat(electronicDevices).hasSize(databaseSizeBeforeUpdate);
        ElectronicDevice testElectronicDevice = electronicDevices.get(electronicDevices.size() - 1);
        assertThat(testElectronicDevice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testElectronicDevice.getConso()).isEqualTo(UPDATED_CONSO);
    }

    @Test
    @Transactional
    public void deleteElectronicDevice() throws Exception {
        // Initialize the database
        electronicDeviceService.save(electronicDevice);

        int databaseSizeBeforeDelete = electronicDeviceRepository.findAll().size();

        // Get the electronicDevice
        restElectronicDeviceMockMvc.perform(delete("/api/electronic-devices/{id}", electronicDevice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ElectronicDevice> electronicDevices = electronicDeviceRepository.findAll();
        assertThat(electronicDevices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
