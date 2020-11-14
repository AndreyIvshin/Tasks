package com.epam.clothshop.api;

import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.model.Product;
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
    public List<ProductMapper.ProductLite> getProducts() {
        return productService.findAll().stream().map(productMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductMapper.ProductFull> getProduct(@PathVariable Long id) {
        return productService.findById(id).map(product -> ResponseEntity.ok(productMapper.mapFull(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody ProductMapper.ProductToSave productToSave) {
        productService.save(productMapper.mapToSave(productToSave));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity putProduct(@PathVariable Long id, @RequestBody ProductMapper.ProductToSave productToSave) {
        if (productService.findById(id).isPresent()) {
            Product product = productMapper.mapToSave(productToSave);
            product.setId(id);
            productService.save(product);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
