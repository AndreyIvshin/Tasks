package com.epam.newsportal.api;

import com.epam.newsportal.mapper.NewsMapper;
import com.epam.newsportal.model.News;
import com.epam.newsportal.service.NewsService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Path("news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsResource {

    @Inject
    private NewsService newsService;
    @Inject
    private NewsMapper newsMapper;
    @Inject
    private Validator validator;

    @GET
    public Response getAllNews() {
        return Response.ok(newsService.findAll().stream().map(newsMapper::mapLite).collect(Collectors.toList())).build();
    }

    @GET
    @Path("{id}")
    public Response getNews(@PathParam("id") Long id) {
        return newsService.find(id)
                .map(news -> Response.ok(newsMapper.mapFull(news)).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response postNews(NewsMapper.NewsToSave newsToSave) {
        News news = newsMapper.mapToSave(newsToSave);
        news.setDate(Date.from(Instant.now()));
        if (!validator.validate(news).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.created(UriBuilder.fromResource(NewsResource.class).path("{id}")
                .build(newsService.create(news).getId())).build();
    }

    @PUT
    @Path("{id}")
    public Response putNews(@PathParam("id") Long id, NewsMapper.NewsToSave newsToSave) {
        return newsService.find(id)
                .map(oldNews -> {
                    News newNews = newsMapper.mapToSave(newsToSave);
                    newNews.setId(id);
                    newNews.setDate(oldNews.getDate());
                    if (!validator.validate(newNews).isEmpty()) {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                    newsService.update(newNews);
                    return Response.ok().build();
                })
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    public Response deleteNews(@PathParam("id") Long id) {
        return newsService.find(id)
                .map(news -> {
                    newsService.delete(id);
                    return Response.ok().build();
                })
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
