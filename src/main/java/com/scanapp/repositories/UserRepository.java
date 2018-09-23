package com.scanapp.repositories;


import com.scanapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends CrudRepository<User , Long> {

    User findByEmail(String email);
    User deleteByEmail(String email);

}