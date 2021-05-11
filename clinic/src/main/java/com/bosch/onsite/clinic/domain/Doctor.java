package com.bosch.onsite.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "of")
public class Doctor {
    private Long id;
    private String name;
    private String lastName;
}
