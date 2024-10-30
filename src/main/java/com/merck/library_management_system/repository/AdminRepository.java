package com.merck.library_management_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.merck.library_management_system.entity.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

}
