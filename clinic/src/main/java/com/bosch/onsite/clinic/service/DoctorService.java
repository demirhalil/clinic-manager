package com.bosch.onsite.clinic.service;

import com.bosch.onsite.clinic.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DoctorService {
    private final AppointmentService appointmentService;

    @Autowired
    public DoctorService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public List<Appointment> getAppointments(int id) {
        Map<Integer, List<Appointment>> appointments = appointmentService.getDoctorAppointments();
        if (!appointments.containsKey(id)) {
            throw new IllegalArgumentException("Doctor is not found");
        }
        return appointments.get(id);
    }
}
