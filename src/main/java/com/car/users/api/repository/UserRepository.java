package com.car.users.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.car.users.api.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	User findByLogin(String login);
	
	Long countByEmail(String email);
	
    Long countByEmailAndIdNot(String email, Integer id);
	
	Long countByLogin(String login);
	
	Long countByLoginAndIdNot(String loginParam, Integer id);
	
}
