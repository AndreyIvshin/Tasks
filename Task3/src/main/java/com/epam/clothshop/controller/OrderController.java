package com.epam.clothshop.controller;

import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok((List<Order>) orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity postOrderCancel(@PathVariable Long id) {
        //TODO cancel
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity postOrderPurchase(@PathVariable Long id) {
        //TODO purchase
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<Product>> getOrderItems(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id).get().getItems());
    }

    @PostMapping("/{id}/items")
    public ResponseEntity postOrderItems(@PathVariable Long id, @RequestBody Product product) {
        Order order = orderService.findById(id).get();
        order.getItems().add(product);
        orderService.save(order);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{oid}/items/{iid}")
    public ResponseEntity<Product> getOrderItem(@PathVariable Long oid, @PathVariable Long iid) {
        return ResponseEntity.ok(orderService.findById(oid).get().getItems().stream().filter(p -> p.getId().equals(iid)).findAny().get());
    }

    @DeleteMapping("/{oid}/items/{iid}")
    public ResponseEntity deleteOrderItem(@PathVariable Long oid, @PathVariable Long iid) {
        Order order = orderService.findById(oid).get();
        Product product = order.getItems().stream().filter(p -> p.getId().equals(iid)).findAny().get();
        order.getItems().remove(product);
        orderService.save(order);
        return ResponseEntity.ok().build();
    }

}
