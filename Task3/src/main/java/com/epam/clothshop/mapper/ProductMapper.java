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
    public static class ProductLite {
        Long id;
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

    public abstract ProductToSave mapToSave(Product product);

    public abstract Product mapToSave(ProductToSave productToSave);

    public abstract ProductLite mapLite(Product product);

    public abstract Product mapLite(ProductLite productLite);

    public abstract ProductFull mapFull(Product product);

    public abstract Product mapFull(ProductFull productFull);
}
