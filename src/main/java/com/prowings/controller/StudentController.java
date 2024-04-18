package com.prowings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prowings.entity.Student;
import com.prowings.exception.StudentNotFoundException;
import com.prowings.model.MyCustomError;
import com.prowings.service.StudentService;

//@Controller + @ResponseBody = @RestController

@RestController
public class StudentController {
	@Autowired
	StudentService studentService;

	@PostMapping("/students")
	public ResponseEntity<String> saveStudent(@RequestBody Student std) {
		System.out.println("Incoming student object : "+std);
		boolean res = studentService.saveStudent(std);
		if (res)
			return new ResponseEntity<String>("Student saved successfully!!!",HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Error while saving the Student!!!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
//	@PostMapping("/students")
//	public String saveStudent(@RequestBody Student std) {
//		boolean res = studentService.saveStudent(std);
//		if (res)
//			return "Student saved successfully!!!";
	
//		else
//			return "Error while saving the Student!!!";
//	}

//	@GetMapping("/students/{id}")
//	public ResponseEntity<Student> getStudentById(@PathVariable int id) {
//		System.out.println("request received to fetch Student of id: " + id + "from DB!!");
//		
//		HttpHeaders headers= new HttpHeaders();
//		headers.add("my header", "myHeaderValue");
//		headers.add("aaaa", "bbbb");
//		
//		return new ResponseEntity<Student>( studentService.getStudentById(id),headers, HttpStatus.OK);
//	}
	@GetMapping("/students/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Student getStudentById(@PathVariable int id) {
		System.out.println("request received to fetch Student of id: " + id + "from DB!!");
		return studentService.getStudentById(id);
		
	//below code was written for the controller specific exception handling method [handleStudentNotFoundException()]
	/*	if(studentService.getStudentById(id) != null)
			return studentService.getStudentById(id);
		else
			throw new StudentNotFoundException();
	*/
	}

	@GetMapping("/students")
	public List<Student> getAllStudents() {
		System.out.println("request received to fetch Students from DB!!");
		return studentService.getAllStudents();
	}

	@GetMapping("/students/search")
	public List<Student> getAllStudentsSearchBy(@RequestParam("city") String city) {
		System.out.println("request received to fetch all Students from city : " + city);
		return studentService.findByCity(city);
	}

	@GetMapping("/students/sort")
	public List<Student> getAllStudentsSortBy(@RequestParam("field") String field) {
		System.out.println("request received to fetch all Students and sort by : " + field);
		return studentService.findAllSortedByField(field);
	}

	
	@PutMapping("/students")
	public String updateStudent(@RequestBody Student student)
	{
		System.out.println("request received to update the student");
		System.out.println("Incoming student object : "+student);
		boolean res = studentService.updateStudent(student);
		if (res)
			return "Student updated successfully!!!";
		else
			return "Error while updating the Student!!!";
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<String> deleteStudentById(@PathVariable int id)
	{
		System.out.println("request received to delete Student of id: "+id +"from DB!!");
		return studentService.deleteStudentById(id) ? new ResponseEntity<String>("Deleted Successfully", HttpStatus.NO_CONTENT) : new ResponseEntity<String>("Failed to delete", HttpStatus.BAD_REQUEST);
	}

	/**
	 * The problem with handler method (@ExceptionHandler)in controller is ,it is controller specific.
	 * It won't work if same exception occur in our application other places..So we should go for 
	 * Global Exception Handling(@ControllerAdvice) Approach where we create global exception handling class and in it
	 * we write handler methods(@ExceptionHandler) for exception handling of whole application. 
	 */
	/*@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<MyCustomError> handleStudentNotFoundException(){
		
		System.out.println("inside handleStudentNotFoundException method!!!");
		
		MyCustomError error= new MyCustomError();
		error.setMessage("Student not found with given ID");
		error.setStatusCode(400);
		return new ResponseEntity<MyCustomError>(error, HttpStatus.NOT_FOUND);
		
	}*/
}
