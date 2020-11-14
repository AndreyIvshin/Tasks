package com.epam.clothshop.api;

import com.epam.clothshop.mapper.OrderMapper;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public List<OrderMapper.OrderLite> getOrders() {
        return orderService.findAll().stream().map(orderMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderMapper.OrderFull> getOrders(@PathVariable Long id) {
        return orderService.findById(id).map(order -> ResponseEntity.ok(orderMapper.mapFull(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity postOrders(@RequestBody OrderMapper.OrderToSave categoryToSave) {
        orderService.save(orderMapper.mapToSave(categoryToSave));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity putOrders(@PathVariable Long id, @RequestBody OrderMapper.OrderToSave categoryToSave) {
        if (orderService.findById(id).isPresent()) {
            Order order = orderMapper.mapToSave(categoryToSave);
            order.setId(id);
            orderService.save(order);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteOrders(@PathVariable Long id) {
        if (orderService.findById(id).isPresent()) {
            orderService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
