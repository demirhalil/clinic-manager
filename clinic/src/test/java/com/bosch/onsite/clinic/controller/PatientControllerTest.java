package com.bosch.onsite.clinic.controller;

import com.bosch.onsite.clinic.domain.Appointment;
import com.bosch.onsite.clinic.service.DoctorService;
import com.bosch.onsite.clinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = {PatientController.class, PatientService.class})
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientService patientService;

    @Test
    void getAppointments() throws Exception {
        List<Appointment> appointments = initAppointments(5)
                .stream()
                .filter(x -> x.getPatientId() == 7)
                .collect(Collectors.toList());
        when(patientService.getAppointments(anyInt())).thenReturn(appointments);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patients/{id}", 7))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].patientId", is(7)))
                .andExpect(jsonPath("$[0].doctorId", is(5)))
                .andReturn();
        verify(patientService, times(1)).getAppointments(anyInt());
    }

    private List<Appointment> initAppointments(int numberOfAppointment) {
        List<Appointment> appointments = new ArrayList<>();
        for (int i = 1; i <= numberOfAppointment; i++) {
            Appointment appointment = Appointment.of(i, i * 5, i * 7, LocalDateTime.now());
            appointments.add(appointment);
        }
        return appointments;
    }
}