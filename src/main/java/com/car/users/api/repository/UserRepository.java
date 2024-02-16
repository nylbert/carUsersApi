package com.car.users.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.car.users.api.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
