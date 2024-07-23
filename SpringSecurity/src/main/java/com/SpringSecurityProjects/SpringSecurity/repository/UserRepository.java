package com.SpringSecurityProjects.SpringSecurity.repository;

import com.SpringSecurityProjects.SpringSecurity.entities.Role;
import com.SpringSecurityProjects.SpringSecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);

}
