package com.epam.clothshop.service;

import com.epam.clothshop.model.Category;
import com.epam.clothshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractCrudRepositoryAdaptingService<Category, Long, CategoryRepository> {
}
