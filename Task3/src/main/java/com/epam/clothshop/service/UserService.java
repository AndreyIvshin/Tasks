package com.epam.clothshop.service;

import com.epam.clothshop.model.User;
import com.epam.clothshop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudRepositoryAdaptingService<User, Long, UserRepository> {
}
