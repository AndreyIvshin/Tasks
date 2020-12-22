package com.epam.newsportal.api;

import com.epam.newsportal.enumeration.Role;
import com.epam.newsportal.mapper.UserMapper;
import com.epam.newsportal.model.User;
import com.epam.newsportal.security.JWTGenerator;
import com.epam.newsportal.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Transactional
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SecurityResource {

    @Inject
    private Validator validator;
    @Inject
    private UserService userService;
    @Inject
    private JWTGenerator jwtGenerator;
    @Inject
    private UserMapper userMapper;

    @POST
    @Path("auth")
    public Response auth(@Valid UserMapper.UserAuth userAuth) {
        if (!validator.validate(userAuth).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return userService.findUserByUsername(userAuth.getUsername())
                .map(user -> BCrypt.checkpw(userAuth.getPassword(), user.getPassword())
                        ? Response.ok()
                        .header("authorization", jwtGenerator.generate(user.getRole()))
                        .header("role", user.getRole().toString())
                        .build()
                        : Response.status(Response.Status.FORBIDDEN).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Path("reg")
    public Response reg(@Valid UserMapper.UserReg userReg) {
        if (!validator.validate(userReg).isEmpty() || userService.findUserByUsername(userReg.getUsername()).isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = userMapper.mapReg(userReg);
        user.setRole(Role.USER);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userService.create(user);
        return Response.ok()
                .header("authorization", jwtGenerator.generate(user.getRole()))
                .header("role", user.getRole().toString())
                .build();
    }

}
