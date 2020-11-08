package com.epam.newsportal.mapper;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.model.transfer.UserTransfer;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserTransfer> {

    @Override
    public User map(UserTransfer transfer) {
        User entity = new User();
        entity.setId(transfer.getId());
        entity.setUsername(transfer.getUsername());
        entity.setPassword(transfer.getPassword());
        entity.setPasswordRepeat(transfer.getPasswordRepeat());
        entity.setRole(transfer.getRole());
        return entity;
    }

    @Override
    public UserTransfer map(User entity) {
        UserTransfer transfer = new UserTransfer();
        transfer.setId(entity.getId());
        transfer.setUsername(entity.getUsername());
        transfer.setPassword(entity.getPassword());
        transfer.setPasswordRepeat(entity.getPasswordRepeat());
        transfer.setRole(entity.getRole());
        return transfer;
    }
}
