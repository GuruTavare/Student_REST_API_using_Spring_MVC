package com.prowings.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.prowings.model.MyCustomError;

@ControllerAdvice
public class StudentExceptionHandler {
	
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<MyCustomError> handleStudentNotFoundException(){
		
		System.out.println("inside handleStudentNotFoundException method!!!");
		
		MyCustomError error= new MyCustomError();
		error.setMessage("Student not found with given ID");
		error.setStatusCode(404);
		return new ResponseEntity<MyCustomError>(error, HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(InvalidStudentException.class)
	public ResponseEntity<MyCustomError> handleInvalidStudentException(){
		System.out.println("inside handleInvalidStudentException method!!!");
		
		MyCustomError customError=new MyCustomError();
		customError.setMessage("name should be greater than 3 chars");
		customError.setStatusCode(400);
		return new ResponseEntity<MyCustomError>(customError, HttpStatus.BAD_REQUEST);
		
	}

}
