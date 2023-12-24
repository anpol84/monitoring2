package com.example.courseWork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Название курса не должно быть пустым")
    private String name;

    private Integer number;

    private Integer semester;

    @NotEmpty(message = "Тип аттестации не может быть пустым")
    private String type;

    @ManyToMany
    @JoinTable(
            name = "course_institute",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "institute_id")
    )
    @NotEmpty(message = "У курса должен быть институт")
    private List<Institute> institutes;

    @ManyToMany
    @NotEmpty(message = "У курса должна быть специальность")
    @JoinTable(
            name = "course_specialization",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "course")
    private List<Retake> retakes;

    @OneToMany(mappedBy = "course")
    private List<Event> events;
}
