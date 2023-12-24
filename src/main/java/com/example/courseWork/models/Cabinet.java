package com.example.courseWork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cabinet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 1, message = "Номер кабинета должен быть положительным")
    @Max(value = 1000, message = "Номер кабинета не должен быть более 1000")
    private Integer number;

    @OneToMany(mappedBy = "cabinet")
    private List<Event> events;

    @Override
    public String toString() {
        return "Cabinet{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}
