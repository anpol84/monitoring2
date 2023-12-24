package com.example.courseWork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Имя не должно быть пустым")
    private String name;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    private String surname;

    @NotEmpty(message = "Отчество не должно быть пустым")
    private String lastname;

    @NotEmpty(message = "Логин не должен быть пустым")
    private String login;

    @Email(message = "Почта не соответстует шаблону почты")
    @NotEmpty(message = "Почта не должна быть пустой")
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    private String role;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    private Specialization specialization;

    @OneToOne(mappedBy = "user")
    private UntreatedStudent untreatedStudent;

    @OneToMany(mappedBy = "user")
    private List<Retake> retakes;

    @OneToMany(mappedBy = "teacher")
    private List<Event> events;

    @ManyToMany(mappedBy = "students")
    private List<Event> studentEvents;

    @OneToMany(mappedBy = "user")
    private List<PastRetake> pastRetakes;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
