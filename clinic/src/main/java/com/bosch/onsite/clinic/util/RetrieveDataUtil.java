package com.bosch.onsite.clinic.util;

import com.bosch.onsite.clinic.domain.Doctor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RetrieveDataUtil {
    public static List<Doctor> getDoctors() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = ResourceUtils.getFile("classpath:mockDoctorData.json");
        List<Doctor> doctors = objectMapper.readValue(file, new TypeReference<List<Doctor>>(){});
        return doctors;
    }
}
