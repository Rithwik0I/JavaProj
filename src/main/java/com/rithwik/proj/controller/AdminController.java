package com.rithwik.proj.controller;

import com.rithwik.proj.dao.AdminDAO;
import com.rithwik.proj.dao.TeacherDAO;
import com.rithwik.proj.model.Admin;
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
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminDAO adminDAO;
    private final TeacherDAO teacherDAO;

    @Autowired
    public AdminController(AdminDAO adminDAO, TeacherDAO teacherDAO) {
        this.adminDAO = adminDAO;
		this.teacherDAO = teacherDAO;
    }
    
    
    
    @PostMapping("/create-subject")
    public String createSubject(@RequestParam String subjectName, @RequestParam String subjectCode) {
        // Logic to save the subject in the database
    	
        return "redirect:/admin-dashboard";
    }

    @PostMapping("/create-teacher")
    public String createTeacher(@RequestParam String teacherName, 
                                @RequestParam String teacherEmail, 
                                @RequestParam String teacherPassword) {
        // Logic to save the teacher in the database (ensure password is hashed)
        return "redirect:/admin-dashboard";
    }


    @GetMapping("/admins")
    public String showAdmins(Model model) {
        List<Admin> admins = new ArrayList<>();
        try {
            admins = adminDAO.getAdmins();
        } catch (SQLException e) {
            logger.error("Error fetching admins", e);
        }
        model.addAttribute("admins", admins);
        return "showadmins";
    }
    
    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model) throws SQLException {
        // Fetch teacher data from the service layer
        List<Teacher> teachers = teacherDAO.getTeachers();
        model.addAttribute("teachers", teachers);
        return "admin-dashboard";
    }




    @GetMapping("/register-admin")
    public String showRegisterForm() {
        return "registeradmin";
    }

    @PostMapping("/admins")
    public String registerAdmin(@RequestParam String name, 
                                  @RequestParam String email, 
                                  @RequestParam String password) {
        Admin admin = new Admin();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(password);

        try {
            adminDAO.addAdmin(admin);
        } catch (SQLException e) {
            logger.error("Error registering admin", e);
            return "error";  // Create an error.html template
        }
        return "redirect:/admins";
    }
}