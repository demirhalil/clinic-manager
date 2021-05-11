package com.bosch.onsite.clinic.controller;

import com.bosch.onsite.clinic.domain.Appointment;
import com.bosch.onsite.clinic.service.AppointmentService;
import com.bosch.onsite.clinic.service.DoctorService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Appointment>> getAppointments(@PathVariable int id) {
        return new ResponseEntity<>(doctorService.getAppointments(id), HttpStatus.OK);
    }
}
