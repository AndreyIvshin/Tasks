package com.epam.newsportal.service;

import com.epam.newsportal.persistence.entity.News;
import com.epam.newsportal.persistence.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NewsService extends AbstractService<News, NewsRepository> {

    public List<News> findAllOrdered() {
        return repository.findAllOrdered();
    }
}
