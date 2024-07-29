package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    private String lastName;
    private String firstName;
    private String title;
    private String titleOfCourtesy;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String homePhone;
    private String extension;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "reportsTo")
    private Employee reportsTo;

    private String photoPath;
}