package com.epam.clothshop.api;

import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.User;
import com.epam.clothshop.security.Authority;
import com.epam.clothshop.service.OrderService;
import com.epam.clothshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@PreAuthorize("hasAuthority('WRITE') or hasAuthority('READ')")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public List<UserMapper.UserLite> getUsers() {
        return userService.findAll().stream().map(userMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserMapper.UserFull> getUser(@PathVariable Long id) {
        if (((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(id)
                || SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(Authority.WRITE.toString()))) {
            return userService.findById(id).map(product -> ResponseEntity.ok(userMapper.mapFull(product)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE') or isAnonymous()")
    public ResponseEntity postUser(@RequestBody UserMapper.UserToSave userToSave) {
        userService.save(userMapper.mapToSave(userToSave));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity putUser(@PathVariable Long id, @RequestBody UserMapper.UserToSave userToSave) {
        if (((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(id)
                || SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(Authority.WRITE.toString()))) {
            Optional<User> optional = userService.findById(id);
            if (optional.isPresent()) {

                //TODO different transfers

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        if (((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(id)
                || SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(Authority.WRITE.toString()))) {
            if (userService.findById(id).isPresent()) {
                userService.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

    }

    @PutMapping("{id}/orders/{iid}")
    public ResponseEntity putUserOrder(@PathVariable Long id, @PathVariable Long iid) {
        if (((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(id)
                || SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(Authority.WRITE.toString()))) {
            Optional<User> userOptional = userService.findById(id);
            if (userOptional.isPresent()) {
                Optional<Order> orderOptional = orderService.findById(iid);
                if (orderOptional.isPresent()) {
                    userOptional.get().getOrders().add(orderOptional.get());
                    userService.save(userOptional.get());
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

    }

    @DeleteMapping("{id}/orders/{iid}")
    public ResponseEntity deleteUserOrder(@PathVariable Long id, @PathVariable Long iid) {
        if (((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(id)
                || SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(Authority.WRITE.toString()))) {
            Optional<User> userOptional = userService.findById(id);
            if (userOptional.isPresent()) {
                Optional<Order> orderOptional = orderService.findById(iid);
                if (orderOptional.isPresent()) {
                    userOptional.get().getOrders().remove(orderOptional.get());
                    userService.save(userOptional.get());
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

    }
}
