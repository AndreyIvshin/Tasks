package com.epam.clothshop.mapper;

import com.epam.clothshop.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Mapper
public abstract class UserMapper {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserToSave {
        String username;
        String firstName;
        String lastName;
        String email;
        String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserLite {
        Long id;
        String username;
        String firstName;
        String lastName;
        String email;
        String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserFull {
        Long id;
        String username;
        String firstName;
        String lastName;
        String email;
        String password;
        List<OrderMapper.OrderLite> orders;
    }

    public abstract UserToSave mapToSave(User user);

    public abstract User mapToSave(UserToSave userToSave);

    public abstract UserLite mapLite(User user);

    public abstract User mapLite(UserLite userLite);

    public abstract UserFull mapFull(User user);

    public abstract User mapFull(UserFull userFull);
}
