package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryDto;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repositories.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repositories.TelemetryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private TelemetryRepository telemetryRepository;

    @InjectMocks
    private ServiceImpl service;

    private DeviceDto deviceDto;
    private TelemetryDto telemetryDto;
    private Device device;
    private Telemetry telemetry;

    @BeforeEach
    void setUp() {
        deviceDto = new DeviceDto("hostname",  DeviceType.DESKTOP, "Windows", "123");
        telemetryDto = new TelemetryDto("30", LocalDateTime.now(), 50.00, 60.2, "true", true, true, "nose");

        device = new Device();
        telemetry = new Telemetry();
    }

    @Test
    void testCreateDevice() {
        when(deviceRepository.save(Mockito.any(Device.class))).thenReturn(device);

        Device createdDevice = service.createDevice(deviceDto);

        assertEquals("hostname", createdDevice.getHostName());
        assertEquals("mac123", createdDevice.getMacAddress());
        assertEquals(DeviceType.DESKTOP, createdDevice.getType());
        assertEquals("Windows", createdDevice.getOs());
    }

    @Test
    void testCreateTelemetry() {
        when(telemetryRepository.save(Mockito.any(Telemetry.class))).thenReturn(telemetry);

        Telemetry createdTelemetry = service.createTelemetry(telemetryDto);

        assertEquals(30, createdTelemetry.getCpuUsage());
        assertEquals("192.168.0.1", createdTelemetry.getIp());
        assertEquals(50, createdTelemetry.getHostDiskFree());
    }

    @Test
    void testGetTelemetries() {
        when(telemetryRepository.findAll()).thenReturn(Collections.singletonList(telemetry));

        List<Telemetry> telemetries = service.getTelemetries();

        assertEquals(1, telemetries.size());
        assertEquals(30, telemetries.get(0).getCpuUsage());
    }

    @Test
    void testGetDeviceWithType() {
        when(deviceRepository.findByType("COMPUTER")).thenReturn(device);

        List<Device> devices = service.getDeviceWithType(DeviceType.DESKTOP);

        assertEquals(1, devices.size());
        assertEquals("hostname", devices.get(0).getHostName());
    }

    @Test
    void testGetTelemetriesFilter() {
        when(telemetryRepository.findByDevice_HostName("hostname")).thenReturn((Telemetry) Collections.singletonList(telemetry));

        List<Telemetry> telemetries = service.getTelemetriesFilter("hostname");

        assertEquals(1, telemetries.size());
        assertEquals("192.168.0.1", telemetries.get(0).getIp());
    }
}
