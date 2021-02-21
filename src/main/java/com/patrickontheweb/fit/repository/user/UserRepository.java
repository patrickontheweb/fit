package com.patrickontheweb.fit.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patrickontheweb.fit.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
