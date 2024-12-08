package com.rithwik.proj.controller;

import com.rithwik.proj.dao.TestDAO;
import com.rithwik.proj.model.Test;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {

    private final TestDAO testDAO;

    @Autowired
    public TestController(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @GetMapping("/student/tests")
    public String viewStudentTests(Model model, HttpSession session) {
        Integer student_id = (Integer) session.getAttribute("student_id");

        try {
            List<Test> tests = testDAO.getTestsByStudent(student_id);
            model.addAttribute("tests", tests);
        	System.out.println(tests);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to fetch tests.");
        }

        return "student-tests"; // Return the view name
    }
}
