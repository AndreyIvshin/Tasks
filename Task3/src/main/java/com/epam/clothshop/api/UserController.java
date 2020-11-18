package com.epam.clothshop.api;

import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.User;
import com.epam.clothshop.security.Authority;
import com.epam.clothshop.security.Role;
import com.epam.clothshop.service.OrderService;
import com.epam.clothshop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.clothshop.security.Authority.WRITE;

@Transactional
@RestController
@RequestMapping("api/users")
@PreAuthorize("hasAuthority('WRITE') or hasAuthority('READ')")
@Api(tags = "user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserMapper userMapper;

    private boolean hasId(Long id) {
        return ((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(id);
    }

    private boolean hasAuthority(Authority authority) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(authority.toString()));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WRITE')")
    @Operation(summary = "Get all users")
    public List<UserMapper.UserLite> getUsers() {
        return userService.findAll().stream().map(userMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get user")
    public ResponseEntity<UserMapper.UserFull> getUser(@PathVariable Long id) {
        if (hasId(id) || hasAuthority(WRITE)) {
            return userService.findById(id).map(product -> ResponseEntity.ok(userMapper.mapFull(product)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE') or isAnonymous()")
    @Operation(summary = "Create user")
    public ResponseEntity<UserMapper.UserFull> postUser(@RequestBody UserMapper.UserToSave userToSave) {
        if (userToSave.getRole().equals(Role.ADMIN) && !hasAuthority(WRITE)) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
        User save = userService.save(userMapper.mapToSave(userToSave));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(save.getId()).toUri()).body(userMapper.mapFull(save));
    }

    @PutMapping("{id}")
    @Operation(summary = "Modify user")
    public ResponseEntity putUser(@PathVariable Long id, @RequestBody UserMapper.UserToSave userToSave, HttpServletResponse response) {
        if (hasId(id) || hasAuthority(WRITE)) {
            Optional<User> optional = userService.findById(id);
            if (optional.isPresent()) {
                User user = userMapper.mapToSave(userToSave);
                user.setId(id);
                if (hasId(id) && !user.getRole().equals(optional.get().getRole())) {
                    return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
                }
                userService.save(user);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remove user")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        if (hasId(id) || hasAuthority(WRITE)) {
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
    @Operation(summary = "Add order to user")
    public ResponseEntity putUserOrder(@PathVariable Long id, @PathVariable Long iid) {
        if (hasId(id) || hasAuthority(WRITE)) {
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
    @Operation(summary = "Delete order from user")
    public ResponseEntity deleteUserOrder(@PathVariable Long id, @PathVariable Long iid) {
        if (hasId(id) || hasAuthority(WRITE)) {
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
