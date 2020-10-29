package com.epam.newsportal.persistence.repository;

import com.epam.newsportal.persistence.entity.News;
import com.epam.newsportal.persistence.entity.News_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class NewsRepository extends AbstractRepository<News>{

    @Override
    protected Class<News> getEntityClass() {
        return News.class;
    }

    public List<News> findAllOrdered() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<News> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(News_.DATE)));
        TypedQuery<News> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
