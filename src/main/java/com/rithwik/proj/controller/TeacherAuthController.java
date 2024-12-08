//package com.rithwik.proj.controller;
//
//import com.rithwik.proj.dao.TeacherDAO;
//import com.rithwik.proj.model.Teacher;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.SQLException;
//
//@Controller
//public class TeacherAuthController {
//
//    @Autowired
//    private TeacherDAO teacherDAO;
//
//    @GetMapping("/teacher-login")
//    public String loginPage() {
//        return "teacher-login";
//    }
//
//    @PostMapping("/teacher-login")
//    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
//        try {
//            Teacher teacher = teacherDAO.findByEmail(email);
//            if (teacher != null && teacher.getPassword().equals(password)) {
//                session.setAttribute("loggedInUser", teacher);
//                return "redirect:/teacher-dashboard";
//            } else {
//                model.addAttribute("error", "Invalid credentials");
//                return "teacher-login";
//            }
//        } catch (SQLException e) {
//            model.addAttribute("error", "Login error");
//            return "teacher-login";
//        }
//    }
//
//    @GetMapping("/teacher-logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/teacher-login";
//    }
//}