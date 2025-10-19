package com.hms.appointment.Appointment.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MonthlyVisitDTO {
    private String month;
    private Long count;
}
