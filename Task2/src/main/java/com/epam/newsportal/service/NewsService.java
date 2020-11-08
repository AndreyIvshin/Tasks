package com.epam.newsportal.service;

import com.epam.newsportal.mapper.NewsMapper;
import com.epam.newsportal.model.entity.News;
import com.epam.newsportal.model.transfer.NewsTransfer;
import com.epam.newsportal.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NewsService extends AbstractService<News, NewsTransfer, NewsRepository, NewsMapper> {

    public List<News> findAllOrdered() {
        return repository.findAllOrdered();
    }

    @Override
    public void create(NewsTransfer transfer) {
        transfer.setDate(new Date());
        super.create(transfer);
    }

    @Override
    public News update(NewsTransfer transfer) {
        transfer.setDate(repository.find(transfer.getId()).getDate());
        return super.update(transfer);
    }
}
