package com.epam.clothshop.api;

import com.epam.clothshop.mapper.OrderMapper;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.OrderService;
import com.epam.clothshop.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("api/orders")
@PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
@Api(tags = "order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    @Operation(summary = "Get all orders")
    public List<OrderMapper.OrderLite> getOrders() {
        return orderService.findAll().stream().map(orderMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get order")
    public ResponseEntity<OrderMapper.OrderFull> getOrders(@PathVariable Long id) {
        return orderService.findById(id).map(order -> ResponseEntity.ok(orderMapper.mapFull(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create order")
    public ResponseEntity postOrders(@RequestBody OrderMapper.OrderToSave categoryToSave) {
        orderService.save(orderMapper.mapToSave(categoryToSave));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Modify order")
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
    @Operation(summary = "Remove order")
    public ResponseEntity deleteOrders(@PathVariable Long id) {
        if (orderService.findById(id).isPresent()) {
            orderService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/products/{iid}")
    @Operation(summary = "Add product to order")
    public ResponseEntity putOrderProduct(@PathVariable Long id, @PathVariable Long iid) {
        Optional<Order> orderOptional = orderService.findById(id);
        if (orderOptional.isPresent()) {
            Optional<Product> productOptional = productService.findById(iid);
            if (productOptional.isPresent()) {
                orderOptional.get().getProducts().add(productOptional.get());
                orderService.save(orderOptional.get());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/products/{iid}")
    @Operation(summary = "Delete product from order")
    public ResponseEntity deleteOrderProduct(@PathVariable Long id, @PathVariable Long iid) {
        Optional<Order> orderOptional = orderService.findById(id);
        if (orderOptional.isPresent()) {
            Optional<Product> productOptional = productService.findById(iid);
            if (productOptional.isPresent()) {
                orderOptional.get().getProducts().remove(productOptional.get());
                orderService.save(orderOptional.get());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
