package com.bosch.onsite.clinic.service;

import com.bosch.onsite.clinic.domain.Appointment;
import com.bosch.onsite.clinic.domain.Doctor;
import com.bosch.onsite.clinic.domain.Patient;
import com.bosch.onsite.clinic.util.RetrieveDataUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AppointmentService {
    @Getter
    @Setter
    private Map<Integer, List<Appointment>> doctorAppointments;
    @Getter
    @Setter
    private Map<Integer, List<Appointment>> patientAppointments;
    private final List<Doctor> doctors;

    public AppointmentService() throws IOException {
        this.doctors = RetrieveDataUtil.getDoctors();
        patientAppointments = new HashMap<>();
        doctorAppointments = new HashMap<>();
    }

    public Appointment save(int doctorId, Patient patient, LocalDateTime time) {
        LocalDate dateOfTime = time.toLocalDate();
        if (!dateOfTime.isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date should be the same date of today");
        }
        checkIfDoctorIsExist(doctorId);
        checkForConflict(doctorId, patient, time);

        int appointmentId = generateAppointmentId();
        Appointment appointment = Appointment.of(appointmentId, doctorId, patient.getId(), time);
        addAppointmentTo(doctorId, appointment, doctorAppointments);
        addAppointmentTo(patient.getId(),appointment,patientAppointments);
        return appointment;
    }

    private void addAppointmentTo(int doctorId, Appointment appointment, Map<Integer, List<Appointment>> appointmentsMap) {
        if (!appointmentsMap.containsKey(doctorId)) {
            appointmentsMap.put(doctorId, new ArrayList<>());
        }
        appointmentsMap.get(doctorId).add(appointment);
    }

    private void checkForConflict(int doctorId, Patient patient, LocalDateTime time) {
        boolean isThereConflictForDoctor = checkConflict(doctorAppointments.get(doctorId), time);
        boolean isThereConflictForPatient = checkConflict(patientAppointments.get(patient.getId()), time);

        if (isThereConflictForDoctor) {
            throw new IllegalArgumentException("This doctor is not available for the specified time. Please choose another time frame");
        }

        if (isThereConflictForPatient) {
            throw new IllegalArgumentException("You have another appointment for the specified time. Please choose another time frame");
        }
    }

    private void checkIfDoctorIsExist(int doctorId) {
        doctors.stream()
                .filter(x -> x.getId() == doctorId)
                .findAny()
                .orElseThrow(() -> new NullPointerException("Doctor is not found for the specified id: " + doctorId));
    }

    private boolean checkConflict(List<Appointment> appointments, LocalDateTime time) {
        if (Objects.isNull(appointments) || appointments.size() == 0) {
            return false;
        }
        for (Appointment appointment : appointments) {
            if (appointment.getTime().isEqual(time)) {
                return true;
            }
        }
        return false;
    }

    private int generateAppointmentId() {
        Random random = new Random();
        return random.nextInt(1 + 1000) + 1;
    }
}
