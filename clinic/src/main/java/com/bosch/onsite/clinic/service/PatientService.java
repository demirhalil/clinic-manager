package com.bosch.onsite.clinic.service;

import com.bosch.onsite.clinic.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PatientService {

    private final AppointmentService appointmentService;

    @Autowired
    public PatientService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public List<Appointment> getAppointments(int id) {
        Map<Integer, List<Appointment>> appointments = appointmentService.getPatientAppointments();
        if (!appointments.containsKey(id)) {
            throw new IllegalArgumentException("Patient is not found");
        }
        return appointments.get(id);
    }
}
