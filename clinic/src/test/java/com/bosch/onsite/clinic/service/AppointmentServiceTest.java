package com.bosch.onsite.clinic.service;

import com.bosch.onsite.clinic.domain.Appointment;
import com.bosch.onsite.clinic.domain.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    public static final int ID = 1;
    public static final String NAME = "Kevin";
    public static final String LAST_NAME = "Hard";
    private AppointmentService appointmentService;
    private Patient patient;
    public static final LocalDateTime TIME = LocalDateTime.now();

    @BeforeEach
    void setUp() throws IOException {
        appointmentService = new AppointmentService();
        patient = initPatient(ID, NAME, LAST_NAME);
    }

    @AfterEach
    void tearDown() {
        appointmentService = null;
    }

    @Test
    void save_whenThereIsNoConflictForBothDoctorAndPatient() {
        long doctorId = 1;
        Patient patient = initPatient(1, "Kevin", "Hard");
        LocalDateTime time = LocalDateTime.now();
        Appointment actual = appointmentService.save(doctorId, patient, time);
        assertEquals(doctorId, actual.getDoctorId());
        assertEquals(patient.getId(), actual.getPatientId());
        assertTrue(appointmentService.getDoctorAppointments().containsKey(doctorId));
        assertTrue(appointmentService.getPatientAppointments().containsKey(patient.getId()));
        assertEquals(1, appointmentService.getDoctorAppointments().get(doctorId).size());
        assertEquals(1, appointmentService.getDoctorAppointments().get(doctorId).size());
    }

    @Test()
    void save_whenTheTimeIsNotToday_thenThrowException() {
        long doctorId = 1;
        LocalDateTime time =  TIME.plusDays(2);
        Patient patient = initPatient(1, "Kevin", "Hard");
        assertThrows(IllegalArgumentException.class, () -> appointmentService.save(doctorId, patient, time));
    }

    @Test
    void save_whenThereIsNoSpecifiedDoctor_thenThrowException() {
        long doctorId = 12;
        Patient patient = initPatient(1, "Kevin", "Hard");
        LocalDateTime time = LocalDateTime.now().plusDays(2);
        assertThrows(IllegalArgumentException.class, () -> appointmentService.save(doctorId, patient, time));

    }

    @Test
    void save_whenThereIsAConflictForDoctor_thenThrowException() {
        long doctorId = 1;
        appointmentService.setDoctorAppointments(initMockDataForAppointments(doctorId));
        assertThrows(IllegalArgumentException.class, () -> appointmentService.save(doctorId, patient, TIME));

    }

    @Test
    void save_whenThereIsAConflictForPatient_thenThrowException() {
        long doctorId = 1;
        appointmentService.setPatientAppointments(initMockDataForAppointments(doctorId));
        assertThrows(IllegalArgumentException.class, () -> appointmentService.save(2L, patient, TIME));

    }

    private Map<Long, List<Appointment>> initMockDataForAppointments(long id) {
        Map<Long, List<Appointment>> appointments = new HashMap<>();
        appointments.put(id, new ArrayList<>());
        appointments.get(id).add(Appointment.of(1L, id, patient.getId(), TIME));
        return appointments;
    }


    private Patient initPatient(long id, String name, String lastName) {
        return Patient.of(id, name, lastName);
    }
}