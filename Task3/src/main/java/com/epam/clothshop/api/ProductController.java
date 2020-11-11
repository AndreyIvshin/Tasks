package com.epam.clothshop.api;

import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductMapper.ProductFull> getProducts() {
        return productService.findAll().stream().map(productMapper::mapFull).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductMapper.ProductToSave> getProduct(@PathVariable Long id) {
        return productService.findById(id).map(product -> ResponseEntity.ok(productMapper.mapToSave(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody ProductMapper.ProductToSave productLite) {
        productService.save(productMapper.mapToSave(productLite));
        return ResponseEntity.ok().build();
    }
}
