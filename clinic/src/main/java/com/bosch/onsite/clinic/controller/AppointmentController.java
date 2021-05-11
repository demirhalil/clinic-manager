package com.bosch.onsite.clinic.controller;

import com.bosch.onsite.clinic.domain.Appointment;
import com.bosch.onsite.clinic.domain.Patient;
import com.bosch.onsite.clinic.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping()
    public ResponseEntity<Appointment> save(@RequestParam int doctorId,
                                            @RequestBody Patient patient,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam  LocalDateTime time) {
        Appointment appointment = appointmentService.save(doctorId, patient, time);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }
}
