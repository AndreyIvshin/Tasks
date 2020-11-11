package com.epam.clothshop.mapper;

import com.epam.clothshop.model.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Mapper
public abstract class ProductMapper {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductToSave {
        String name;
        Integer price;
        Integer quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductFull {
        Long id;
        String name;
        Integer price;
        Integer quantity;
    }

    @Map(ProductToSave.class)
    public abstract ProductToSave mapToSave(Product product);

    @Map(ProductToSave.class)
    public abstract Product mapToSave(ProductToSave productToSave);

    @Map(ProductFull.class)
    public abstract ProductFull mapFull(Product product);

    @Map(ProductFull.class)
    public abstract Product mapFull(ProductFull productFull);
}
