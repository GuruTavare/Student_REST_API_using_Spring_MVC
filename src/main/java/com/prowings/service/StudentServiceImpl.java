package com.prowings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prowings.dao_or_repository.StudentDAO;
import com.prowings.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentDAO studentDAO;

	@Override
	public boolean saveStudent(Student student) {

		return studentDAO.saveStudent(student);
	}

	@Override
	public Student getStudentById(int id) {
		return studentDAO.getStudentById(id);
	}

	@Override
	public List<Student> getAllStudents() {
		return studentDAO.getAllStudents();
	}

	@Override
	public List<Student> findByCity(String city) {
		return studentDAO.findByCity(city);
	}

	@Override
	public List<Student> findAllSortedByField(String field) {
		return studentDAO.findAllSortedByField(field);
	}

	@Override
	public boolean updateStudent(Student student) {
		return studentDAO.updateStudent(student);
	}

	@Override
	public boolean deleteStudentById(int id) {
		return studentDAO.deleteStudentByID(id);
	}

}
