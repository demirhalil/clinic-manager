package com.bosch.onsite.clinic.controller;

import com.bosch.onsite.clinic.domain.Appointment;
import com.bosch.onsite.clinic.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = {DoctorController.class, DoctorService.class})
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DoctorService doctorService;

    @Test
    void getAppointments() throws Exception {
        List<Appointment> appointments = initAppointments(5)
                .stream()
                .filter(x -> x.getDoctorId() == 10)
                .collect(Collectors.toList());
        when(doctorService.getAppointments(anyInt())).thenReturn(appointments);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/{id}", 10))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].doctorId", is(10)))
                .andExpect(jsonPath("$[0].patientId", is(14)))
                .andReturn();
        verify(doctorService, times(1)).getAppointments(anyInt());
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