package com.bosch.onsite.clinic.service;

import com.bosch.onsite.clinic.domain.Appointment;
import com.bosch.onsite.clinic.domain.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.print.Doc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;
    @Mock
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAppointments_whenThereIsAppointments_returnThem() {
        int doctorId = 1;
        Map<Integer, List<Appointment>> appointments = initMockData(5, doctorId);
        when(appointmentService.getDoctorAppointments()).thenReturn(appointments);
        List<Appointment> appointmentsOfDoctor = doctorService.getAppointments(doctorId);
        assertEquals(5,appointmentsOfDoctor.size());
    }

    @Test
    void getAppointments_whenThereIsNoSpecifiedDoctor_thenThrowException() {
        int doctorId = 1;
        Map<Integer, List<Appointment>> appointments = initMockData(5, doctorId);
        when(appointmentService.getDoctorAppointments()).thenReturn(appointments);
        assertThrows(IllegalArgumentException.class, () -> doctorService.getAppointments(2));
    }

    private Map<Integer, List<Appointment>> initMockData(int numberOfAppointments, int doctorId) {
        Map<Integer, List<Appointment>> map = new HashMap<>();
        List<Appointment> appointments = new ArrayList<>();
        for (int i = 1; i <= numberOfAppointments; i++) {
            Patient patient = Patient.of(i, "name-" + i, "lastname-" + i);
            Appointment appointment = Appointment.of(i, doctorId, patient.getId(), LocalDateTime.now());
            appointments.add(appointment);
        }

        map.put(doctorId, new ArrayList<>());
        map.get((doctorId)).addAll(appointments);
        return map;
    }
}