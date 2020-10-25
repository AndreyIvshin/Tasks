package com.epam.newsportal.domain.repository;

import com.epam.newsportal.domain.entity.News;
import org.springframework.stereotype.Repository;

@Repository
public class NewsRepository extends AbstractRepository<News>{

    @Override
    protected Class<News> getEntityClass() {
        return News.class;
    }
}
