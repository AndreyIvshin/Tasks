package com.epam.clothshop.api;

import com.epam.clothshop.mapper.CategoryMapper;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryMapper.CategoryLite> getCategories() {
        return categoryService.findAll().stream().map(categoryMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryMapper.CategoryFull> getCategory(@PathVariable Long id) {
        return categoryService.findById(id).map(category -> ResponseEntity.ok(categoryMapper.mapFull(category)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity postCategory(@RequestBody CategoryMapper.CategoryToSave categoryToSave) {
        categoryService.save(categoryMapper.mapToSave(categoryToSave));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
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
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
