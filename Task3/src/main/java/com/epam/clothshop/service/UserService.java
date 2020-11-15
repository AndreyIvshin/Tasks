package com.epam.clothshop.service;

import com.epam.clothshop.model.User;
import com.epam.clothshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractCrudRepositoryAdaptingService<User, Long, UserRepository> {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findById(Long var1) {
        Optional<User> optional = super.findById(var1);
        if (optional.isPresent()) {
            optional.get().setPassword("hidden");
        }
        return optional;
    }

    @Override
    public List<User> findAll() {
        List<User> all = super.findAll();
        all.forEach(u -> u.setPassword("hidden"));
        return all;
    }

    @Override
    public <S extends User> S save(S var1) {
        var1.setPassword(passwordEncoder.encode(var1.getPassword()));
        S save = super.save(var1);
        return save;
    }
}
