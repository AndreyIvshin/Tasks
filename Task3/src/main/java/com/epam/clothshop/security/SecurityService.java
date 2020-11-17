package com.epam.clothshop.security;

import com.epam.clothshop.model.User;
import com.epam.clothshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
            return null;
        }
        SecurityModel securityModel = new SecurityModel();
        securityModel.setId(user.getId());
        securityModel.setUsername(user.getUsername());
        securityModel.setPassword(user.getPassword());
        securityModel.setAuthorities(user.getRole().getAuthorities().stream().map(a -> new SimpleGrantedAuthority(a.toString())).collect(Collectors.toList()));
        return securityModel;
    }
}
