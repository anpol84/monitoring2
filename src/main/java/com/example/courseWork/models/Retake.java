package com.example.courseWork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "retake")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Retake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 1, message = "Количество попыток должно быть как минимум 1")
    @Max(value = 5, message = "Количество попыток должно быть не больше 5")
    private Integer attempt;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @Override
    public String toString() {
        return "Retake{" +
                "id=" + id +
                ", attempt=" + attempt +
                ", course=" + course.getId() +
                ", user=" + user.getId() +
                '}';
    }
}
