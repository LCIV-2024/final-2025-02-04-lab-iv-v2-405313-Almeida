package ar.edu.utn.frc.tup.lciii.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryDto {

  private String ip;
  private LocalDateTime dataDate;
  private Double CpuUsage;
  private Double hostDiskFree;
  private String microphoneState;
  private Boolean screenCaptureAllowed;
  private Boolean audioCaptureAllowed;
  private String HostName;



}
