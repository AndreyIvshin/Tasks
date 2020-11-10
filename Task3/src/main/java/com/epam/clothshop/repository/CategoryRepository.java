package com.epam.clothshop.repository;

import com.epam.clothshop.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
