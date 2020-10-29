package com.epam.newsportal.service;

import com.epam.newsportal.persistence.entity.User;
import com.epam.newsportal.persistence.enumeration.Role;
import com.epam.newsportal.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class UserService extends AbstractService<User , UserRepository> {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession httpSession;

    public boolean signIn(String username, String password) {
        User user = repository.findUserByName(username);
        if (user == null) {
            return false;
        } else {
            if (passwordEncoder.matches(password, user.getPassword())) {
                login(user);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean signUp(String username, String password, String passwordRepeat, Role role) {
        if (repository.findUserByName(username) != null) {
            return false;
        } else {
            if (password.equals(passwordRepeat)) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                user.setRole(role);
                repository.create(user);
                login(user);
                return true;
            } else {
                return false;
            }
        }
    }

    public void login(User user) {
        httpSession.setAttribute("user", user);
    }

    public void logout() {
        httpSession.setAttribute("user", null);
    }
}
