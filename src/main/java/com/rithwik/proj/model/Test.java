package com.rithwik.proj.model;

public class Test {
    private int testId;
    private String testName;
    private String subjectCode; // Foreign key from Subject
    private Integer student_id;     // Foreign key from Student
    private double marksObtained;
    private double maxMarks;

    // Getters and setters
    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getsubjectCode() {
        return subjectCode;
    }

    public void setsubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getstudent_id() {
        return student_id;
    }

    public void setstudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }
}
