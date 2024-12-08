package com.rithwik.proj.controller;

import com.rithwik.proj.dao.TeacherDAO;
import com.rithwik.proj.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherDAO teacherDAO;

    @Autowired
    public TeacherController(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @GetMapping("/teachers")
    public String showTeachers(Model model) {
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = teacherDAO.getTeachers();
        } catch (SQLException e) {
            logger.error("Error fetching teachers", e);
        }
        model.addAttribute("teachers", teachers);
        return "showteachers";
    }
    
    @GetMapping("/teacher-dashboard")
    public String showDashboard(Model model) {
        // Mock data for subjects
        List<Map<String, String>> subjects = List.of(
            Map.of("name", "Mathematics"),
            Map.of("name", "Physics"),
            Map.of("name", "Chemistry")
        );

        // Mock data for tests
        List<Map<String, Object>> tests = List.of(
            Map.of("id", 1, "name", "Algebra Test", "subject", "Mathematics", "date", "2024-12-01"),
            Map.of("id", 2, "name", "Mechanics Quiz", "subject", "Physics", "date", "2024-12-02")
        );

        // Mock data for grades
        List<Map<String, Object>> grades = List.of(
            Map.of("id", 1, "studentName", "John Doe", "subject", "Mathematics", "testName", "Algebra Test", "marksObtained", 85, "totalMarks", 100, "grade", "A"),
            Map.of("id", 2, "studentName", "Jane Smith", "subject", "Physics", "testName", "Mechanics Quiz", "marksObtained", 78, "totalMarks", 100, "grade", "B")
        );

        model.addAttribute("subjects", subjects);
        model.addAttribute("tests", tests);
        model.addAttribute("grades", grades);

        return "teacher-dashboard"; // Points to teacher-dashboard.html
    }



//    @GetMapping("/register-teacher")
//    public String showRegisterForm() {
//        return "registerteacher";
//    }

    @PostMapping("/teachers")
    public String registerTeacher(@RequestParam String name, 
                                  @RequestParam String email, 
                                  @RequestParam String password,
                                  @RequestParam String subject_code,
                                  @RequestParam String subject_name) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setSubject_code(subject_code);
        teacher.setSubject_name(subject_name);

        try {
            teacherDAO.addTeacher(teacher);
        } catch (SQLException e) {
            logger.error("Error registering teacher", e);
            return "error";  // Create an error.html template
        }
        return "redirect:/admin-dashboard";
    }
}