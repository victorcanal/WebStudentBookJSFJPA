package com.buart_canal.web;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//import javax.sql.DataSource;

@ManagedBean(eager=true)
@SessionScoped

public class StudentManager {
	List<Student> students;
	//private static DataSource dataSource;
	//private static StudentDbUtil sbd;

	public StudentManager() {
		super();
		this.students = null;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public void loadStudents() throws SQLException {
		students = StudentDbUtil.getStudents();
	}
	
	public String addStudent(Student stu) throws SQLException {
		StudentDbUtil.addStudent(stu);
		return "List-students.xhtml";
	}
	
	public String loadStudent(int ids) throws SQLException {
		Student theStudent = StudentDbUtil.fetchStudent(ids);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		requestMap.put("student", theStudent);
		//Student tempstudent = new Student();
		//requestMap.put("tempstudent",tempstudent);
		return "Edit-student.xhtml";
	}
	
	public String updateStudent(Student stu) {
		StudentDbUtil.updateStudent(stu);
		return "List-students";
	}
	
	public String deleteStudent(int id) throws SQLException {
		StudentDbUtil.deleteStudent(id);
		return "List-students";
	}
}
