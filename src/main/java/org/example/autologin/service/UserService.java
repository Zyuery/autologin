package org.example.autologin.service;

import org.example.autologin.domain.User;

public interface UserService {
    User loginService(String uname, String password);
    User registService(User user);
}
