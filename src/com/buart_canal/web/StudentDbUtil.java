package com.buart_canal.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(eager = true)
@ApplicationScoped

public class StudentDbUtil {
	static DataSource dataSource;
	
	public StudentDbUtil() throws NamingException{
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException{
		String jndi="java:comp/env/jdbc/studentdb";
		Context context = new InitialContext();
		DataSource dataSource = (DataSource)context.lookup(jndi);
		return dataSource;
	}
	
	public static List<Student> getStudents() throws SQLException{
		List<Student> students = new ArrayList<Student>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			String sql = "select * from student";
			myRs = myStmt.executeQuery(sql);
			
			while(myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				Student tempStudent = new Student(id,firstName,lastName,email);
				students.add(tempStudent);
			}
			return students;
		}
		finally {
			close(myConn,myStmt,myRs);
		}
	}
	
	public static void addStudent(Student student) throws SQLException {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			String sql = "insert into student(first_name, last_name, email) values ('" + student.firstName + "', '" + student.lastName + "', '" + student.email + "');";
			myStmt.executeUpdate(sql);
		}
		finally {
			close(myConn,myStmt,myRs);
		}
	}
	
	public static Student fetchStudent(int id) throws SQLException {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		Student response=null;

		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			String sql = "select * from student where id='" + id + "';";
			myRs = myStmt.executeQuery(sql);
			myRs.next();
			String firstname=myRs.getString("first_name");
			String lastname=myRs.getString("last_name");
			String email=myRs.getString("email");
			response = new Student(id,firstname,lastname,email);
			return response;
		}
		
		finally {
			close(myConn,myStmt,myRs);
		}
	}
	
	public static void updateStudent(Student student) {
		Connection myConn=null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			String sql = "update student set first_name=?, last_name=?, email=? where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, student.getFirstName());
			myStmt.setString(2, student.getLastName());
			myStmt.setString(3, student.getEmail());
			myStmt.setInt(4,student.getId());
			myStmt.execute();
			} 
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			close(myConn,myStmt,null);
		}
	}
	
	public static void deleteStudent(int id) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			String sql = "delete from student where id=? ;";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
		}
		finally {
			close(myConn,myStmt,null);
		}
	}
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if(myStmt != null)
				myStmt.close();
			if(myRs != null)
				myRs.close();
			if(myConn != null)
				myConn.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
