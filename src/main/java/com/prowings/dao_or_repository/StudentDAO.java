package com.prowings.dao_or_repository;

import java.util.List;

import com.prowings.entity.Student;

public interface StudentDAO {
public boolean saveStudent(Student student);
	
	public Student getStudentById(int id);

	public List<Student> getAllStudents();

	public List<Student> findByCity(String city);

	public List<Student> findAllSortedByField(String field);

	public boolean updateStudent(Student student);

	public boolean deleteStudentByID(int id);

}
