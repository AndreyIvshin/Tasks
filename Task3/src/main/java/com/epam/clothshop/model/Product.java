package com.epam.clothshop.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data @NoArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "product_table")
public class Product {

    @Id
    @SequenceGenerator(name = "product_squ_gen", sequenceName = "product_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_squ_gen")
    @Column(name = "product_id")
    Long id;

    @Column(name = "product_name")
    String name;

    @Column(name = "product_price")
    Integer price;

    @Column(name = "product_quantity")
    Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_fk")
    Category category;
}
