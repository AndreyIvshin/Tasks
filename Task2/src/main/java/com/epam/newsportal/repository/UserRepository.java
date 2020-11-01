package com.epam.newsportal.repository;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.model.entity.User_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class UserRepository extends AbstractRepository<User>{

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public User findUserByName(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.USERNAME), username));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList().stream().findAny().orElse(null);
    }

    public List<User> findAllOrdered() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<User> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(User_.USERNAME)));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
