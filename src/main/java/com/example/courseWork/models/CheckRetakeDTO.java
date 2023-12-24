package com.example.courseWork.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRetakeDTO {
    private List<Integer> userIds;
    private List<String> isRetake;
    private Integer eventId;
}
