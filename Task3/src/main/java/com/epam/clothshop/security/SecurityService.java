package com.epam.clothshop.security;

import com.epam.clothshop.model.User;
import com.epam.clothshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        SecurityModel securityModel = new SecurityModel();
        securityModel.setId(user.getId());
        securityModel.setUsername(user.getUsername());
        securityModel.setPassword(user.getPassword());
        securityModel.setAuthorities(user.getRole().getAuthorities());
        return securityModel;
    }

    public void registerUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setEmail("email");
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
