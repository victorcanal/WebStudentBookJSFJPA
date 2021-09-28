package jpa.com.buart_canal.web;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.Table;

@ManagedBean(eager=true)
@SessionScoped
@Table(name = "student")

public class StudentManager1 {
	List<Student1> students;

	public StudentManager1() {
		super();
		this.students = null;
	}

	public List<Student1> getStudents() {
		return students;
	}

	public void setStudents(List<Student1> students) {
		this.students = students;
	}
	
	public void loadStudents() throws SQLException {
		students = StudentDbUtil1.getStudents();
	}
	
	public String addStudent(Student1 stu) throws SQLException {
		StudentDbUtil1.addStudent(stu);
		return "List-students1";
	}
	
	public String loadStudent(int ids) throws SQLException {
		Student1 theStudent = StudentDbUtil1.fetchStudent(ids);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		requestMap.put("student1", theStudent);
		return "Edit-student1";
	}
	
	public String updateStudent(Student1 stu) {
		StudentDbUtil1.updateStudent(stu);
		return "List-students1";
	}
	
	public String deleteStudent(int id) throws SQLException {
		StudentDbUtil1.deleteStudent(id);
		return "List-students1";
	}
}
