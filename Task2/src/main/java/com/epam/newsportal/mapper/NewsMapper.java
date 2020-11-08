package com.epam.newsportal.mapper;

import com.epam.newsportal.model.entity.News;
import com.epam.newsportal.model.transfer.NewsTransfer;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper extends AbstractMapper<News, NewsTransfer> {

    @Override
    public News map(NewsTransfer transfer) {
        News entity = new News();
        entity.setId(transfer.getId());
        entity.setTitle(transfer.getTitle());
        entity.setBrief(transfer.getBrief());
        entity.setContent(transfer.getContent());
        entity.setDate(transfer.getDate());
        return entity;
    }

    @Override
    public NewsTransfer map(News entity) {
        NewsTransfer transfer = new NewsTransfer();
        transfer.setId(entity.getId());
        transfer.setTitle(entity.getTitle());
        transfer.setBrief(entity.getBrief());
        transfer.setContent(entity.getContent());
        transfer.setDate(entity.getDate());
        return transfer;
    }
}
