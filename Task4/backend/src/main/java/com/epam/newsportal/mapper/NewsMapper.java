package com.epam.newsportal.mapper;

import com.epam.newsportal.model.News;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

public class NewsMapper {

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class NewsToSave {
        private String title;
        private String brief;
        private String content;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class NewsLite {
        private Long id;
        private String title;
        private String brief;
        private Date date;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class NewsFull {
        private Long id;
        private String title;
        private String brief;
        private String content;
        private Date date;
    }

    public NewsFull mapFull(News news) {
        NewsFull newsFull = new NewsFull();
        newsFull.setId(news.getId());
        newsFull.setTitle(news.getTitle());
        newsFull.setBrief(news.getBrief());
        newsFull.setContent(news.getContent());
        newsFull.setDate(news.getDate());
        return newsFull;
    }

    public NewsLite mapLite(News news) {
        NewsLite newsLite = new NewsLite();
        newsLite.setId(news.getId());
        newsLite.setTitle(news.getTitle());
        newsLite.setBrief(news.getBrief());
        newsLite.setDate(news.getDate());
        return newsLite;
    }

    public News mapToSave(NewsToSave newsToSave) {
        News news = new News();
        news.setTitle(newsToSave.getTitle());
        news.setBrief(newsToSave.getBrief());
        news.setContent(newsToSave.getContent());
        return news;
    }
}
