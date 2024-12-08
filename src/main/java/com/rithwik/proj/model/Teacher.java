package com.rithwik.proj.model;

public class Teacher {
    private int id;
    private String name;
    private String email;
    private String password;
    private String subject_name;
    private String subject_code;
    
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public String getSubject_code() {return subject_code;}
	public void setSubject_code(String subject_code) {this.subject_code = subject_code;}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
