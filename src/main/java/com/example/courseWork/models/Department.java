package com.example.courseWork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Название кафедры не может быть пустым")
    private String name;

    @ManyToOne
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    private Institute institute;
    @OneToMany(mappedBy = "department")
    private List<User> teachers;
}
