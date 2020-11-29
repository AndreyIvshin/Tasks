package com.epam.newsportal.filter;

import com.epam.newsportal.enumeration.Role;
import com.epam.newsportal.security.JWTGenerator;
import io.jsonwebtoken.JwtException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class JWTFilter implements ContainerRequestFilter {

    private String POST = "POST";
    private String GET = "GET";

    @Inject
    private JWTGenerator jwtGenerator;

    @Override
    public void filter(ContainerRequestContext req) throws IOException {
        if ((getPath(req).equals("/auth") && methodEquals(req, POST)) || (getPath(req).equals("/reg") && methodEquals(req, POST))) {
            return;
        } else {
            String authorization = req.getHeaders().getFirst("authorization");
            if (authorization == null) {
                abort(req);
                return;
            }
            Role role = null;
            try {
                role = jwtGenerator.parse(authorization);
            } catch (JwtException e) {
                abort(req);
                return;
            }
            if (role.equals(Role.USER)) {
                if (!(getPath(req).startsWith("/news") && methodEquals(req, GET))) {
                    abort(req);
                    return;
                }
            }
        }
    }

    private void abort(ContainerRequestContext req) {
        req.abortWith(Response.status(Response.Status.FORBIDDEN).build());
    }

    private boolean methodEquals(ContainerRequestContext containerRequestContext, String method) {
        return containerRequestContext.getMethod().equals(method);
    }

    private String getPath(ContainerRequestContext containerRequestContext) {
        return containerRequestContext.getUriInfo().getPath();
    }
}
