package com.prowings.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prowings.entity.Student;
import com.prowings.entity.Users;

import net.bytebuddy.asm.Advice.Return;

@RestController
@RequestMapping("/v1")
public class UserInterceptorController {
	@Autowired
	StudentController studentController;
	@GetMapping("/users")
	public String hello()
	{
		 List<Student> lst=studentController.getAllStudents();
		 System.out.println(lst);
		return "welcome java developers@@@@@@@@@@@@@@";
	}
	
	@PostMapping("/users")
	public ResponseEntity<List<Users>> saveUsers(@RequestBody Users user){
		
		List<Users> lst=new ArrayList<>();
		lst.add(user);
		lst.add(new Users(3, "raman", "Security"));
		lst.add(new Users(4, "Raghav", "Tester"));
		lst.add(new Users(5, "sita", "Hr"));
		
		
		return new ResponseEntity<List<Users>>(lst, HttpStatus.OK);
		
	}

}
