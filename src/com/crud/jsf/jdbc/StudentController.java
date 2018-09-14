package com.crud.jsf.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class StudentController {

	private List<Student> students;
	private StudentService studentService;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	public List<Student> getStudents() {
		return studentService.getStudents();
	}
	
	public StudentController() throws Exception {
		students = new ArrayList<>();
		
		studentService = StudentService.getInstance();
	}
	

	public void loadStudents() {

		try {
			
			// get all students from database
			studentService.loadStudents();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading students", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addStudent(Student theStudent) {


		try {
			
			// add student to the database
			studentService.addStudent(theStudent);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding students", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			return null;

		}
		
		return "list-students?faces-redirect=true";
	}

	public String loadStudent(int studentId) {
		
		
		try {
			// get student from database
			studentService.loadStudent(studentId);
			
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading student id:" + studentId, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			return null;
		}
				
		return "update-student-form.xhtml";	

	}	
	
	public String updateStudent(Student theStudent) {

		
		try {
			
			// update student in the database
			studentService.updateStudent(theStudent);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating student: " + theStudent, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			return null;
		}
		
		return "list-students?faces-redirect=true";	
	}
	
	public String deleteStudent(int studentId) {
		
		try {

			// delete the student from the database
			studentService.deleteStudent(studentId);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting student id: " + studentId, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			return null;
		}
		
		return "list-students";	
	}	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
