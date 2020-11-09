package com.epam.newsportal.service;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.model.enumeration.Role;
import com.epam.newsportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends AbstractService<User, UserRepository> implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findUserByName(s);
    }

    @Override
    public void create(User entity) {
        if (entity.getPassword().equals(entity.getPasswordRepeat())) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            entity.setRole(Role.USER);
            super.create(entity);
        }
    }
}
