package com.epam.clothshop.service;

import com.epam.clothshop.model.Product;
import com.epam.clothshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractCrudRepositoryAdaptingService<Product, Long, ProductRepository> {

}
