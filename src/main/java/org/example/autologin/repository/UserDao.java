package org.example.autologin.repository;

import org.example.autologin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserDao extends JpaRepository<User, Integer> {
    User findByUname(String uname);

    User findByUnameAndPassword(String uname, String password);
    // 根据用户名查找用户，如果存在则返回 true，否则返回 false
    boolean existsByUname(String uname);
}