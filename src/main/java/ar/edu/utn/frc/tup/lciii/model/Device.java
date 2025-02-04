package ar.edu.utn.frc.tup.lciii.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "DEVICE")
public class Device {

    @Id
    @Column(name = "HOSTNAME", unique = true)
    private String hostName;

    @OneToOne(mappedBy = "device")
    private Telemetry telemetry;

    @Column(name = "CREATED_DATE")
    private LocalDateTime created_date;

    @Column(name = "OS")
    private String os;

    @Column(name = "MACADDRESS", unique = true)
    private String macAddress;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private DeviceType type;

}
