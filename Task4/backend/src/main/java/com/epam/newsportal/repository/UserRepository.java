package com.epam.newsportal.repository;

import com.epam.newsportal.model.User;
import com.epam.newsportal.model.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class UserRepository extends AbstractRepository<User, Long> {

    public Optional<User> findUserByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.USERNAME), username));
        return entityManager.createQuery(criteriaQuery).getResultList().stream().findAny();
    }
}
