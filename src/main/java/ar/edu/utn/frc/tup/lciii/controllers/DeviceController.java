package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceDto;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.services.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeviceController {

    @Autowired
    private  ServiceImpl service;

    @GetMapping("/api/device")
    public ResponseEntity<List<Device>> getDeviceType(@RequestParam DeviceType type){
        List<Device> lst = service.getDeviceWithType(type);
        return ResponseEntity.ok(lst);
    }


    @PostMapping("api/device")
    public ResponseEntity<Device> postDevice(@RequestBody DeviceDto device){
        Device response = service.createDevice(device);
        return ResponseEntity.ok(response);
    }



}