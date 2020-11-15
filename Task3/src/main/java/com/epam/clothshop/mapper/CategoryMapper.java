package com.epam.clothshop.mapper;

import com.epam.clothshop.model.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Mapper
public abstract class CategoryMapper {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CategoryToSave {
        String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CategoryLite {
        Long id;
        String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CategoryFull {
        Long id;
        String name;
        List<ProductMapper.ProductLite> products = new ArrayList<>();
    }

    public abstract CategoryToSave mapToSave(Category category);

    public abstract Category mapToSave(CategoryToSave categoryToSave);

    public abstract CategoryLite mapLite(Category category);

    public abstract Category mapLite(CategoryLite categoryLite);

    public abstract CategoryFull mapFull(Category category);

    public abstract Category mapFull(CategoryFull categoryFull);
}
