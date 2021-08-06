package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 들고있음.
// @Repository라는 어노테이션이 없어도 IOC가 됨. 왜냐하면 JpaRepository를 상속했으므로
public interface UserRepository extends JpaRepository<User,Integer> {

    // findBy규칙 => Username 문법
    // select * from user where username = 1?
    public User findByUsername(String username);// Jpa Query method
}
