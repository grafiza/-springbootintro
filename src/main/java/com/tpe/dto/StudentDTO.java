package com.tpe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tpe.domain.Student;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO { // StudentRequest , StudentResponse

    private Long id;

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be white space")
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} long")
    private String firstName;

    private String lastName;

    private Integer grade;

    @Email(message = "Provide valid email")
    private String email; // xxx@yyy.com // sss.com

    private String phoneNumber; // xxx-xxx-xx-xx

    private LocalDateTime createDate = LocalDateTime.now();

    // Not: POJO yu DTO ya ceviren Const. **********************
    public StudentDTO(Student student){
        this.id= student.getId();
        this.firstName = student.getName();
        this.lastName = student.getLastName();
        this.grade = student.getGrade();
        this.email = student.getEmail();
        this.phoneNumber = student.getPhoneNumber();
        this.createDate = student.getCreateDate();

    }
}