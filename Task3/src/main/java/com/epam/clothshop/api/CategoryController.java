package com.epam.clothshop.api;

import com.epam.clothshop.mapper.CategoryMapper;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.CategoryService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("api/categories")
@PreAuthorize("hasAuthority('WRITE')")
@Api(tags = "category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
    @Operation(summary = "Get all categories")
    public List<CategoryMapper.CategoryLite> getCategories() {
        return categoryService.findAll().stream().map(categoryMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
    @Operation(summary = "Get category")
    public ResponseEntity<CategoryMapper.CategoryFull> getCategory(@PathVariable Long id) {
        return categoryService.findById(id).map(category -> ResponseEntity.ok(categoryMapper.mapFull(category)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create category")
    public ResponseEntity postCategory(@RequestBody CategoryMapper.CategoryToSave categoryToSave) {
        Category save = categoryService.save(categoryMapper.mapToSave(categoryToSave));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(save.getId()).toUri()).body(categoryMapper.mapFull(save));
    }

    @PutMapping("{id}")
    @Operation(summary = "Modify category")
    public ResponseEntity putCategory(@PathVariable Long id, @RequestBody CategoryMapper.CategoryToSave categoryToSave) {
        if (categoryService.findById(id).isPresent()) {
            Category category = categoryMapper.mapToSave(categoryToSave);
            category.setId(id);
            categoryService.save(category);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remove category")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/products/{iid}")
    @Operation(summary = "Add product to category")
    public ResponseEntity putCategoryProduct(@PathVariable Long id, @PathVariable Long iid) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()) {
            Optional<Product> productOptional = productService.findById(iid);
            if (productOptional.isPresent()) {
                categoryOptional.get().getProducts().add(productOptional.get());
                categoryService.save(categoryOptional.get());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/products/{iid}")
    @Operation(summary = "Delete product from category")
    public ResponseEntity deleteCategoryProduct(@PathVariable Long id, @PathVariable Long iid) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()) {
            Optional<Product> productOptional = productService.findById(iid);
            if (productOptional.isPresent()) {
                categoryOptional.get().getProducts().remove(productOptional.get());
                categoryService.save(categoryOptional.get());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
