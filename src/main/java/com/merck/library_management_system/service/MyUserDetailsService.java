package com.merck.library_management_system.service;

import com.merck.library_management_system.entity.Admin;
import com.merck.library_management_system.entity.Student;
import com.merck.library_management_system.repository.AdminRepository;
import com.merck.library_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    StudentRepository studentRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find the user in the Admin repository
        Optional<Admin> adminOpt = adminRepository.findById(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return new User(admin.getUsername(), admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        // Try to find the user in the Student repository
        Optional<Student> studentOpt = studentRepository.findById(username);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            return new User(student.getUsername(), student.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT")));
        }

        // If neither Admin nor Student is found, throw an exception
        throw new UsernameNotFoundException("User not found: " + username);
    }


}
