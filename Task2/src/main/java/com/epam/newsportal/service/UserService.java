package com.epam.newsportal.service;

import com.epam.newsportal.mapper.UserMapper;
import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.model.enumeration.Role;
import com.epam.newsportal.model.transfer.UserTransfer;
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
public class UserService extends AbstractService<User, UserTransfer, UserRepository, UserMapper> implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findUserByName(s);
    }

    @Override
    public void create(UserTransfer transfer) {
        if (transfer.getPassword().equals(transfer.getPasswordRepeat())) {
            transfer.setPassword(passwordEncoder.encode(transfer.getPassword()));
            transfer.setRole(Role.USER);
            super.create(transfer);
        }
    }
}
