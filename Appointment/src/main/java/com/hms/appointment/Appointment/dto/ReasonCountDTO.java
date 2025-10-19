package com.hms.appointment.Appointment.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReasonCountDTO {
    private String reason;
    private Long count;
}
