package com.merck.library_management_system.services;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.merck.library_management_system.entity.Admin;
import com.merck.library_management_system.entity.Student;
import com.merck.library_management_system.repository.AdminRepository;
import com.merck.library_management_system.repository.StudentRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	AdminRepository ap;
	
	@Autowired
	StudentRepository sp;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    // Try to find the user in the Admin repository
	    Optional<Admin> adminOpt = ap.findById(username);
	    if (adminOpt.isPresent()) {
	        Admin admin = adminOpt.get();
	        return new User(admin.getUsername(), admin.getPassword(),
	                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
	    }

	    // Try to find the user in the Student repository
	    Optional<Student> studentOpt = sp.findById(username);
	    if (studentOpt.isPresent()) {
	        Student student = studentOpt.get();
	        return new User(student.getUsername(), student.getPassword(),
	                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT")));
	    }

	    // If neither Admin nor Student is found, throw an exception
	    throw new UsernameNotFoundException("User not found: " + username);
	}
	

}
