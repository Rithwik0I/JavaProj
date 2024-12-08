package com.rithwik.proj.dao;

import com.rithwik.proj.model.Test;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@Repository
public class TestDAO {
	private final DataSource dataSource;

    public TestDAO(DataSource dataSource) {
    	this.dataSource = dataSource;
    }

    public void addTest(Test test) throws Exception {
        String query = "INSERT INTO Test (test_name, subject_code, student_id, marks_obtained, max_marks) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	Integer safeStudentId = test.getstudent_id(); // Use a default value, e.g., 0
        	
            statement.setString(1, test.getTestName());
            statement.setString(2, test.getsubjectCode());
            if (safeStudentId != null) {
                statement.setObject(3, safeStudentId, java.sql.Types.INTEGER);
            } else {
                statement.setObject(3, null, java.sql.Types.INTEGER);
            }            statement.setDouble(4, test.getMarksObtained());
            statement.setDouble(5, test.getMaxMarks());
            statement.executeUpdate();
        }
    }

    public List<Test> getTestsByStudent(Integer studentId) throws Exception {
        List<Test> tests = new ArrayList<>();
        String query = "SELECT * FROM test WHERE student_id = ?";
        try (Connection connection = dataSource.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	System.out.println(studentId);
        	if (studentId != null) {
        	    statement.setObject(1, studentId, java.sql.Types.INTEGER);
        	} else {
        	    statement.setObject(1, null, java.sql.Types.INTEGER);
        	}
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	System.out.println(resultSet);
            	Test test = new Test();
                Integer safeStudentId = test.getstudent_id(); // Use a default value, e.g., 0
                String safeSubjectCode = resultSet.getString("subject_code");
            	System.out.println(safeSubjectCode);
            	

                test.setTestId(resultSet.getInt("test_id"));
                test.setTestName(resultSet.getString("test_name"));
                test.setsubjectCode((safeSubjectCode != null) ? safeSubjectCode : "N/A");
                test.setstudent_id((safeStudentId != null) ? safeStudentId : 0);
                test.setMarksObtained(resultSet.getDouble("marks_obtained"));
                test.setMaxMarks(resultSet.getDouble("max_marks"));
                tests.add(test);
            }
            System.out.println("Tests List: " + tests);
        	for (Test test1 : tests) {
        	    System.out.println("Subject Code: " + test1.getsubjectCode());
        	}
        }
        return tests;
    }
}
