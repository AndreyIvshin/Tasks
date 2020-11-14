package com.epam.clothshop.mapper;

import com.epam.clothshop.model.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Mapper
public abstract class OrderMapper {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderToSave {
        Date shipDate;
        Date createdAt;
        Order.Status status;
        Boolean complete;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderLite {
        Long id;
        Date shipDate;
        Date createdAt;
        Order.Status status;
        Boolean complete;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderFull {
        Long id;
        Date shipDate;
        Date createdAt;
        Order.Status status;
        Boolean complete;
        List<ProductMapper.ProductLite> items;
    }

    public abstract OrderToSave mapToSave(Order order);

    public abstract Order mapToSave(OrderToSave categoryToSave);

    public abstract OrderLite mapLite(Order order);

    public abstract Order mapLite(OrderLite categoryLite);

    public abstract OrderFull mapFull(Order order);

    public abstract Order mapFull(OrderFull categoryFull);
}
