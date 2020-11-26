package com.epam.newsportal.api;

import com.epam.newsportal.model.User;
import com.epam.newsportal.service.UserService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Transactional
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    public Response getUsers() {
        return Response.ok(userService.findAll()).build();
    }

    @GET @Path("{id}")
    public Response getUser(@PathParam("id") Long id) {
        System.out.println(1234);
        return userService.find(id)
                .map(o -> Response.ok().build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response postUser(User user) {
        return Response.created(UriBuilder.fromResource(this.getClass()).path("{id}")
                .build(userService.save(user).getId())).build();
    }

    @PUT @Path("{id}")
    public Response putUser(@PathParam("id") Long id, User user) {
        if (userService.find(id).isPresent()) {
            if (user.getId().equals(id)) {
                return Response.ok(userService.save(user)).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        if (userService.find(id).isPresent()) {
            userService.delete(id);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
