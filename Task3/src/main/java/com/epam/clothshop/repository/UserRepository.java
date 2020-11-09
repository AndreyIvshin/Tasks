package com.epam.clothshop.repository;

import com.epam.clothshop.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
