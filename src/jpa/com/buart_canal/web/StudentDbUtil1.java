package jpa.com.buart_canal.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.sql.DataSource;

@ManagedBean(eager = true)
@ApplicationScoped
@Table(name = "student")

public class StudentDbUtil1 {
	private static final String PERSIS_NAME = "JSFJPA";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSIS_NAME);
	
	static EntityManager em = factory.createEntityManager();
	
	static DataSource dataSource;
	
	public StudentDbUtil1() throws NamingException{	}
	
	public static Student1 fetchStudent(int id) throws SQLException {
		Student1 stu = em.find(Student1.class, id);
		return stu;
	}
	
	public static void addStudent(Student1 student) throws SQLException {
		em.getTransaction().begin();
		em.persist(student); 
		em.getTransaction().commit();
	}
	
	public static void deleteStudent(int id) throws SQLException {
		Student1 oldStudent = em.find(Student1.class,id); 
		em.getTransaction().begin(); 
		em.remove(oldStudent); 
		em.getTransaction().commit();
	}
	
	public static void updateStudent(Student1 student) {
		Student1 stu = em.find(Student1.class, student.id);
		em.getTransaction().begin();
		stu.setFirstName(student.firstName);
		stu.setLastName(student.lastName);
		stu.setEmail(student.email);
		em.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Student1> getStudents() throws SQLException{
		List<Student1> students = new ArrayList<Student1>();// = new ArrayList<Student1>();
		Query query = em.createQuery( "select x from Student1 as x");
		students = query.getResultList();
		return students;
	}
}
