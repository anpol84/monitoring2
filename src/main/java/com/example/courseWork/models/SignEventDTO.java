package com.example.courseWork.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignEventDTO {
    private Integer userId;
    private Integer eventId;
}
