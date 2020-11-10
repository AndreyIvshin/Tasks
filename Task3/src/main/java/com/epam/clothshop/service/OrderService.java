package com.epam.clothshop.service;

import com.epam.clothshop.model.Order;
import com.epam.clothshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends AbstractCrudRepositoryMirroringService<Order, Long, OrderRepository> {
}
