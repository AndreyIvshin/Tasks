package com.epam.newsportal.service;

import com.epam.newsportal.model.entity.News;
import com.epam.newsportal.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NewsService extends AbstractService<News, NewsRepository> {

    public List<News> findAllOrdered() {
        return repository.findAllOrdered();
    }

    @Override
    public void create(News entity) {
        entity.setDate(new Date());
        super.create(entity);
    }

    @Override
    public News update(News entity) {
        entity.setDate(repository.find(entity.getId()).getDate());
        return super.update(entity);
    }
}
