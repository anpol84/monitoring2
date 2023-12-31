package com.example.courseWork.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetakeDTO {

    private User user;
    private List<Integer> courses;
    private List<Integer> attempts;
}
