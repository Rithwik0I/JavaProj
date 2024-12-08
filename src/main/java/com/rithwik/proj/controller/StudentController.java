package com.rithwik.proj.controller;

import com.rithwik.proj.dao.StudentDAO;
import com.rithwik.proj.dao.TestDAO;
import com.rithwik.proj.model.Student;
import com.rithwik.proj.model.Test;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentDAO studentDAO;
    private final TestDAO testDAO;

    @Autowired
    public StudentController(StudentDAO studentDAO, TestDAO testDAO) {
        this.studentDAO = studentDAO;
        this.testDAO = testDAO;
    }

    @GetMapping("/students")
    public String showStudents(Model model) {
        List<Student> students = new ArrayList<>();
        try {
            students = studentDAO.getStudents();
        } catch (SQLException e) {
            logger.error("Error fetching students", e);
        }
        model.addAttribute("students", students);
        return "showstudents";
    }



    @GetMapping("/register-student")
    public String showRegisterForm() {
        return "registerstudent";
    }
    
    @GetMapping("/homepage")
    public String homePage() {
        return "homepage";
    }
    
    
    
    @GetMapping("/error")
    public String error() {
    	return "error";
    }

    @PostMapping("/students")
    public String registerStudent(@RequestParam String name, 
                                  @RequestParam String email, 
                                  @RequestParam String password) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password);

        try {
            studentDAO.addStudent(student);
        } catch (SQLException e) {
            logger.error("Error registering student", e);
            return "error";  // Create an error.html template
        }
        return "redirect:/students";
    }

    @GetMapping("/student-dashboard")
    public String showDashboard(Model model, HttpSession session) {
        // Mock data for subjects
        List<Map<String, String>> subjects = List.of(
            Map.of("name", "Mathematics", "teacher", "Mr. John Doe"),
            Map.of("name", "Science", "teacher", "Ms. Jane Smith"),
            Map.of("name", "History", "teacher", "Dr. Emily White")
        );

        // Mock data for tests
        Integer student_id = (Integer) session.getAttribute("student_id");

        try {
            List<Test> tests = testDAO.getTestsByStudent(student_id);
            model.addAttribute("tests", tests);
        	System.out.println(tests);
        	
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to fetch tests.");
        }

        // Add data to the model
        model.addAttribute("subjects", subjects);
        

        return "student-dashboard"; // Points to student-dashboard.html
    }
}
