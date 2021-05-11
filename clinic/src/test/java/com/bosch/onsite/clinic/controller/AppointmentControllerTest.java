package com.bosch.onsite.clinic.controller;

import com.bosch.onsite.clinic.domain.Appointment;
import com.bosch.onsite.clinic.domain.Patient;
import com.bosch.onsite.clinic.service.AppointmentService;
import com.bosch.onsite.clinic.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = {AppointmentController.class, AppointmentService.class})
class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppointmentService appointmentService;

    @Test
    void save() throws Exception {
        Appointment appointment = Appointment.of(1, 5, 7, LocalDateTime.now());
        when(appointmentService.save(anyInt(),any(Patient.class),any(LocalDateTime.class))).thenReturn(appointment);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/appointments")
                .param("doctorId", String.valueOf(5))
                .param("time", String.valueOf(LocalDateTime.now()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"id\": 3,\n" +
                        "    \"name\": \"Halil\",\n" +
                        "    \"lastName\": \"Demir\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.doctorId", is(5)))
                .andExpect(jsonPath("$.patientId", is(7)))
                .andDo(print())
                .andReturn();
        verify(appointmentService, times(1)).save(anyInt(), any(Patient.class), any(LocalDateTime.class));
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