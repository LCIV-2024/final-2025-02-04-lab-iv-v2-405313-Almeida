package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryDto;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repositories.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repositories.TelemetryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceImpl {

    @Autowired
    private  DeviceRepository deviceRepository;
    @Autowired
    private  TelemetryRepository telemetryRepository;
    @Autowired
    private final RestTemplate restTemplate;


    public Device createDevice(DeviceDto request) {

        Device response = new Device();
        response.setHostName(request.getHostName());
        response.setMacAddress(request.getMacAddress());
        response.setType(request.getType());
        response.setOs(request.getOs());
        response.setCreated_date(LocalDateTime.now());

        deviceRepository.save(response);

        return response;
    }

    public Telemetry createTelemetry(TelemetryDto request){
        Telemetry response = new Telemetry();
        response.setCpuUsage(request.getCpuUsage());
        response.setDevice(response.getDevice());
        response.setIp(request.getIp());
        response.setHostDiskFree(request.getHostDiskFree());
        response.setMicrophoneState(request.getMicrophoneState());
        response.setAudioCaptureAllowed(request.getAudioCaptureAllowed());
        response.setDataDate(LocalDateTime.now());
        response.setScreenCaptureAllowed(request.getScreenCaptureAllowed());

        telemetryRepository.save(response);

        return response;
    }

    public List<Telemetry> getTelemetries() {
        List<Telemetry> lstTelemetry;
        lstTelemetry = telemetryRepository.findAll();

        return  lstTelemetry;
    }

    public List<DeviceDto> getDeviceWithType(DeviceType type) {
        List<Device> lstDevice = (List<Device>) deviceRepository.findByType(String.valueOf(type));

        List<DeviceDto> deviceDtos = lstDevice.stream().map(device -> new DeviceDto(
                device.getHostName(),
                device.getType(),
                device.getOs(),
                device.getMacAddress()
        )).collect(Collectors.toList());

        return deviceDtos;
    }

    public List<Telemetry> getTelemetriesFilter(String filter) {
        List<Telemetry> lstTelemetry;
        lstTelemetry = Collections.singletonList(telemetryRepository.findByDevice_HostName(filter));

        return lstTelemetry;
    }
    public void saveConsumedDevices() {
        String url = "https://67a106a15bcfff4fabe171b0.mockapi.io/api/v1/device/device";

        try {

            Device[] devices = restTemplate.getForObject(url, Device[].class);

            if (devices != null && devices.length > 0) {
                List<Device> deviceList = Arrays.asList(devices);
                Collections.shuffle(deviceList);
                int halfSize = deviceList.size() / 2;
                List<Device> selectedDevices = deviceList.subList(0, halfSize);

                List<Device> deviceEntities = selectedDevices.stream()
                        .map(device -> {

                            Device entity = new Device();
                            entity.setHostName(device.getHostName());
                            entity.setType(device.getType());
                            entity.setOs(device.getOs());
                            entity.setMacAddress(device.getMacAddress());
                            entity.setCreated_date(LocalDateTime.now());
                            return entity;
                        })
                        .collect(Collectors.toList());


                deviceRepository.saveAll(deviceEntities);

            }
        } catch (HttpClientErrorException | NullPointerException e) {

            System.err.println("Error al obtener dispositivos: " + e.getMessage());
        }
    }
}
