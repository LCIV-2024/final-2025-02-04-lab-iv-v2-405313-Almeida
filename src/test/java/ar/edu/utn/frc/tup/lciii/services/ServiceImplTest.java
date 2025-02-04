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

    @BeforeEach
    void setUp() {
        deviceDto = new DeviceDto("hostname", DeviceType.DESKTOP, "Windows", "123");
        telemetryDto = new TelemetryDto("30", LocalDateTime.now(), 50.00, 60.2, "true", true, true, "nose");
    }



    @Test
    void testCreateTelemetry() {
        Telemetry telemetry = new Telemetry();
        when(telemetryRepository.save(Mockito.any(Telemetry.class))).thenReturn(telemetry);

        Telemetry createdTelemetry = service.createTelemetry(telemetryDto);

        // Verificaciones en el DTO
        assertEquals(50, createdTelemetry.getCpuUsage());
        assertEquals("true", createdTelemetry.getMicrophoneState());
        assertEquals(true, createdTelemetry.getAudioCaptureAllowed());
    }

    @Test
    void testGetTelemetries() {
        Telemetry telemetry = new Telemetry();
        when(telemetryRepository.findAll()).thenReturn(Collections.singletonList(telemetry));

        List<Telemetry> telemetries = service.getTelemetries();

        assertEquals(1, telemetries.size());
        assertEquals(null, telemetries.get(0).getCpuUsage());
    }

    @Test
    void testGetDeviceWithType() {
        Device device = new Device();
        when(deviceRepository.findByType("DESKTOP")).thenReturn(device);

        List<DeviceDto> devices = service.getDeviceWithType(DeviceType.DESKTOP);

        assertEquals(1, devices.size());
        assertEquals(null, devices.get(0).getHostName());
    }


}
