package com.prowings.dao_or_repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.prowings.entity.Student;
import com.prowings.exception.InvalidStudentException;
import com.prowings.exception.StudentNotFoundException;

@Repository
public class StudentDAOImpl implements StudentDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean saveStudent(Student student) {
//		if(10/2==5)
//			throw new InvalidStudentException();

		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(student);
			transaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		}

	}

	@Override
	public Student getStudentById(int id) {
		Session session = null;
		Transaction transaction = null;
		Student s = new Student();
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			s = session.get(Student.class, id);

		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();

			
			
		}
		if(s != null)
			return s;
		else
			throw new StudentNotFoundException();
	}

	@Override
	public List<Student> getAllStudents() {
		Session session = null;
		Transaction txn = null;
		List<Student> res = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			String hql = "FROM Student";
			Query query = session.createQuery(hql);
			res = query.getResultList();
			txn.commit();
		} catch (Exception e) {
			System.out.println("Error while fetching the student list!!");
			e.printStackTrace();
			if (txn != null)
				txn.rollback();
		}
		return res;

	}

	@Override
	public List<Student> findByCity(String address) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
			Root<Student> root = criteriaQuery.from(Student.class);
			criteriaQuery.select(root).where(builder.equal(root.get("address"), address));
			return session.createQuery(criteriaQuery).getResultList();
		}
	}

	@Override
	public List<Student> findAllSortedByField(String field) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
			Root<Student> root = criteriaQuery.from(Student.class);
			criteriaQuery.select(root).orderBy(builder.asc(root.get(field))); // Assuming 'field' is the name of the
																				// field by which you want to sort
			return session.createQuery(criteriaQuery).getResultList();
		}
	}

	@Override
	public boolean updateStudent(Student student) {
		System.out.println("inside StudentRepository :: updateStudent()");
		Student fetchedStudent = new Student();
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			fetchedStudent = session.get(Student.class, student.getId());
			if (null != fetchedStudent) {
				session.merge(student);
				return true;
			} else {
				System.out.println("Student with specified ID : " + student.getId() + " is not present in DB!!");
				throw new RuntimeException("Student with specified ID is not present in DB!!");
			}

		} catch (Exception e) {
			System.out.println("error while updating the student!!");
			e.printStackTrace();
			return false;
		} finally {
			txn.commit();
			session.close();
		}
	}

	@Override
	public boolean deleteStudentByID(int id) {
		Student fetchedStudent = new Student();
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			fetchedStudent = session.get(Student.class, id);
			if(null != fetchedStudent)
			{
				session.remove(fetchedStudent);
				return true;
			}
			else
			{
				System.out.println("Student with specified ID : "+id+ " is not present in DB!!");
				throw new StudentNotFoundException();
			}

		}catch (StudentNotFoundException e1) {
			System.out.println("error while deleting the student!!");
			e1.printStackTrace();
			return false;
		}
		finally {
			txn.commit();
			session.close();
		}
	}

}
