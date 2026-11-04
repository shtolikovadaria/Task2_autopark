package com.example.autopark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Укажите марку авто")
    @Size(max = 100, message = "Не длиннее 100 символов")
    @Column(name = "marka_avto")
    private String markaAvto;

    @NotNull(message = "Укажите год выпуска")
    @Min(value = 1900, message = "Год не раньше 1900")
    @Max(value = 2100, message = "Год не позже 2100")
    @Column(name = "year_of_issue")
    private Integer yearOfIssue;

    @NotNull(message = "Укажите дату постановки на учёт")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @NotBlank(message = "Укажите ФИО владельца")
    @Size(max = 150, message = "Не длиннее 150 символов")
    @Column(name = "owner_name")
    private String ownerName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarkaAvto() { return markaAvto; }
    public void setMarkaAvto(String markaAvto) { this.markaAvto = markaAvto; }

    public Integer getYearOfIssue() { return yearOfIssue; }
    public void setYearOfIssue(Integer yearOfIssue) { this.yearOfIssue = yearOfIssue; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}