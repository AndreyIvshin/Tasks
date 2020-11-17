package com.epam.clothshop.api;

import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("api/products")
@PreAuthorize("hasAuthority('WRITE')")
@Api(tags = "product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
    @Operation(summary = "Get all products")
    public List<ProductMapper.ProductLite> getProducts() {
        return productService.findAll().stream().map(productMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get product")
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
    public ResponseEntity<ProductMapper.ProductFull> getProduct(@PathVariable Long id) {
        return productService.findById(id).map(product -> ResponseEntity.ok(productMapper.mapFull(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create product")
    public ResponseEntity postProduct(@RequestBody ProductMapper.ProductToSave productToSave) {
        Product save = productService.save(productMapper.mapToSave(productToSave));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(save.getId()).toUri()).body(productMapper.mapFull(save));
    }

    @PutMapping("{id}")
    @Operation(summary = "Modify product")
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
    @Operation(summary = "Remove product")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
