package com.rithwik.proj.dao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rithwik.proj.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@Repository
public class TeacherDAO {

    private final DataSource dataSource;

    @Autowired
    public TeacherDAO(DataSource dataSource) {
		this.dataSource = dataSource;
    }
    
    public Teacher findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM teacher WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setId(rs.getInt("teacher_id"));
                    teacher.setName(rs.getString("name"));
                    teacher.setEmail(rs.getString("email"));
                    teacher.setPassword(rs.getString("password"));
                    teacher.setSubject_code(rs.getString("subject_code"));
                    teacher.setSubject_name(rs.getString("subject_name"));
                    return teacher;
                }
            }
        }
        return null;
    }

    public List<Teacher> getTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teacher";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("teacher_id"));
                teacher.setName(rs.getString("name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setSubject_code(rs.getString("subject_code"));
                teacher.setSubject_name(rs.getString("subject_name"));
                teachers.add(teacher);
            }
        }
        return teachers;
    }

    public void addTeacher(Teacher teacher) throws SQLException {
        String query = "INSERT INTO teacher (name, email, password, subject_code, subject_name) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getEmail());
            pstmt.setString(3, teacher.getPassword());
            pstmt.setString(4, teacher.getSubject_code());
            pstmt.setString(5, teacher.getSubject_name());
            pstmt.executeUpdate();
        }
    }
}