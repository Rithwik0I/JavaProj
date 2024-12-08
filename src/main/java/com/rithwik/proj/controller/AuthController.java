package com.rithwik.proj.controller;

import com.rithwik.proj.dao.StudentDAO;
import com.rithwik.proj.dao.TeacherDAO;
import com.rithwik.proj.dao.AdminDAO;
import com.rithwik.proj.model.Student;
import com.rithwik.proj.model.Teacher;
import com.rithwik.proj.model.Admin;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private StudentDAO studentDAO;
    
    @Autowired
    private TeacherDAO teacherDAO;
    
    @Autowired
    private AdminDAO adminDAO;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/check-user-role")
    @ResponseBody
    public ResponseEntity<?> checkUserRole(HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        String userRole = (String) session.getAttribute("userRole");

        if (loggedInUser == null || userRole == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Map<String, String> response = new HashMap<>();
        response.put("role", userRole);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        @RequestParam String role,
                        HttpSession session, 
                        Model model) {
        try {
            boolean loginSuccess = false;
            switch(role) {
                case "student":
                    Student student = studentDAO.findByEmail(email);
                    if (student != null && student.getPassword().equals(password)) {
                        session.setAttribute("loggedInUser", student);
                        session.setAttribute("userRole", "student");
                        session.setAttribute("student_id", student.getId());
                        loginSuccess = true;
                    }
                    break;
                case "teacher":
                    Teacher teacher = teacherDAO.findByEmail(email);
                    if (teacher != null && teacher.getPassword().equals(password)) {
                        session.setAttribute("loggedInUser", teacher);
                        session.setAttribute("userRole", "teacher");
                        session.setAttribute("teacher_id", teacher.getId());
                        loginSuccess = true;
                    }
                    break;
                case "admin":
                    Admin admin = adminDAO.findByEmail(email);
                    if (admin != null && admin.getPassword().equals(password)) {
                        session.setAttribute("loggedInUser", admin);
                        session.setAttribute("userRole", "admin");
                        session.setAttribute("admin_id", admin.getId());
                        loginSuccess = true;
                    }
                    break;
            }

            if (loginSuccess) {
                return "redirect:/" + role + "-dashboard";
            } else {
                model.addAttribute("error", "Invalid credentials");
                return "login";
            }
        } catch (SQLException e) {
            model.addAttribute("error", "Login error");
            return "redirect:/"+role+ "-login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}