package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public void createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ConflictException("Email is already exist");
        }
        studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        // findbyId optional gönderir. Optional nullpointerexception almamak için kullanılır
        return studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student not found with id: " + id));
    }

    public void deleteStudent(Long id) {
        Student student = findStudent(id);
        studentRepository.delete(student);
    }

    //Not: Update Student ************************************
    public void updateStudent(Long id, StudentDTO studentDTO) {
        //!!! id'li ogrenci var mi ??
        Student student = findStudent(id);
        //!!! email unique mi ??
        boolean emailExist = studentRepository.existsByEmail(studentDTO.getEmail());
        if (emailExist && !studentDTO.getEmail().equals(student.getEmail())) {
            throw new ConflictException("Email is already exist");
        }
        /*
               1) kendi email mrc, yenisindede mrc gir --> ( UPDATE OLUR )
               2) kendi email mrc, ahmet girdi ( DB de zaten var) --> ( CONFLICT )
               3) kendi email mrc, mhmet girdi ( DB de YOk ) --> ( UPDATE OLUR )
         */

        // !!! DTO --> POJO
        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());

        studentRepository.save(student);
    }

    public Page<Student> getAllWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public List<Student> findStudentByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    //Not: GetStudentByGrade( with JPQL ( Java Persistance Query Language) ) ******
    public List<Student> getStudentsEqualsGrade(Integer grade) {

        return studentRepository.findAllEqualsGrade(grade);
    }
}