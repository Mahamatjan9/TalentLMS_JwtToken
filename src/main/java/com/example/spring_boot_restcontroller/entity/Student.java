package com.example.spring_boot_restcontroller.entity;

import com.example.spring_boot_restcontroller.entity.emuns.StudyFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_gen")
    @SequenceGenerator(name = "student_gen", sequenceName = "student_gen", allocationSize = 1)
    private Long id;
    @Column(length = 100000,name = "first_name")
    private String firstName;
    @Column(length = 100000,name = "last_name")
    private String lastName;
    @Column(length = 100000,name = "phone_name")
    private String phoneNumber;
    @Column(length = 100000)
    private String email;
    @Column(name = "study_format")
    private StudyFormat studyFormat;

    @ManyToOne(cascade = {MERGE,DETACH,PERSIST,REFRESH},fetch = FetchType.EAGER)
    private Group group;

}
