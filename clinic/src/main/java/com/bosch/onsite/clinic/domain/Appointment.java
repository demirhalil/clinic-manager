package com.bosch.onsite.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
public class Appointment {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime time;
}
