package com.example.courseWork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "specialization")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Название специальности не может быть пустым")
    private String name;

    @NotEmpty(message = "Код не может быть пустым")
    @Size(min = 8, max = 8, message = "Код должен состоять из 8 символов")
    @Pattern(regexp = "\\d\\d\\.\\d\\d\\.\\d\\d", message = "Код должен состоять из 6 цифр, разделенных точками по 2 цифры")
    private String code;

    @ManyToOne
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    private Institute institute;

    @ManyToMany(mappedBy = "specializations")
    private List<Course> courses;

    @OneToMany(mappedBy = "specialization")
    private List<User> students;

    @Override
    public String toString() {
        return name;
    }
}
