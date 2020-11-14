package com.epam.clothshop.api;

import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.model.User;
import com.epam.clothshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<UserMapper.UserLite> getUsers() {
        return userService.findAll().stream().map(userMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserMapper.UserFull> getUser(@PathVariable Long id) {
        return userService.findById(id).map(product -> ResponseEntity.ok(userMapper.mapFull(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity postUser(@RequestBody UserMapper.UserToSave userToSave) {
        userService.save(userMapper.mapToSave(userToSave));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity putUser(@PathVariable Long id, @RequestBody UserMapper.UserToSave userToSave) {
        if (userService.findById(id).isPresent()) {
            User user = userMapper.mapToSave(userToSave);
            user.setId(id);
            userService.save(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
