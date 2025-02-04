package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryDto;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.services.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TelemetryController {

    @Autowired
    private ServiceImpl service;
    @GetMapping("/api/telemetry")
    public ResponseEntity<List<Telemetry>> getAllTelemetry(@RequestParam(required = false)String hostname ) {
        if (hostname.isEmpty()) {
            List<Telemetry> lst = service.getTelemetries();
            return ResponseEntity.ok(lst);
        } else {
            List<Telemetry> lst = service.getTelemetriesFilter(hostname);
            return ResponseEntity.ok(lst);
        }
    }

    @PostMapping("/api/telemetry")
    public ResponseEntity<Telemetry> postTelemetry(@RequestBody TelemetryDto telemetry ) {
        Telemetry response = service.createTelemetry(telemetry);
        return ResponseEntity.ok(response);
    }

}