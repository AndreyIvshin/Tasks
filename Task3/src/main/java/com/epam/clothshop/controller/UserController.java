package com.epam.clothshop.controller;

import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.User;
import com.epam.clothshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok((List<User>) userService.findAll());
    }

    @PostMapping
    public ResponseEntity postUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("login")
    public ResponseEntity getUserLogin(@RequestParam String username, @RequestParam String password) {
        //TODO login
        return ResponseEntity.ok().build();
    }

    @GetMapping("logout")
    public ResponseEntity getUserLogout(@RequestParam String username, @RequestParam String password) {
        //TODO login
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id).get());
    }

    @PutMapping("{id}")
    public ResponseEntity<User> putUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.save(userService.findById(id).get()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/orders")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id).get().getOrders());
    }

    @PostMapping("{id}/orders")
    public ResponseEntity postUserOrder(@PathVariable Long id, @RequestBody Order order) {
        User user = userService.findById(id).get();
        user.getOrders().add(order);
        userService.save(user);
        return ResponseEntity.ok().build();
    }
}
