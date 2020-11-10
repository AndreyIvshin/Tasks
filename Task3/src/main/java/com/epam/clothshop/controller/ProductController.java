package com.epam.clothshop.controller;

import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok((List<Product>) productService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity postProduct(@RequestBody Product product) {
        productService.save(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity putProduct(@PathVariable Long id, @RequestBody Product product) {
        Product old = productService.findById(id).get();
        product.setId(old.getId());
        productService.save(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity getProductPhoto(@PathVariable Long id) {
        //TODO get photo
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/photo")
    public ResponseEntity putProductPhoto(@PathVariable Long id) {
        //TODO put photo
        return ResponseEntity.ok().build();
    }

}
