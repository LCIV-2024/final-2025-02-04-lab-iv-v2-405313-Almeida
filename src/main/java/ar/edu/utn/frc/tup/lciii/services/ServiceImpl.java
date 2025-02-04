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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceImpl {

    @Autowired
    private  DeviceRepository deviceRepository;
    @Autowired
    private  TelemetryRepository telemetryRepository;


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

    public List<Device> getDeviceWithType(DeviceType type){
        List<Device> lstDevice;
        lstDevice = Collections.singletonList(deviceRepository.findByType(String.valueOf(type)));

        return lstDevice;
    }

    public List<Telemetry> getTelemetriesFilter(String filter) {
        List<Telemetry> lstTelemetry;
        lstTelemetry = Collections.singletonList(telemetryRepository.findByDevice_HostName(filter));

        return lstTelemetry;
    }
}
