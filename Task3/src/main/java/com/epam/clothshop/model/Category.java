package com.epam.clothshop.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "category_table")
public class Category {

    @Id
    @SequenceGenerator(name = "category_seq_gen", sequenceName = "category_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
    @Column(name = "category_id")
    Long id;

    @Column(name = "category_name")
    String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_products_table",
            joinColumns = @JoinColumn(name = "category_fk"),
            inverseJoinColumns = @JoinColumn(name = "product_fk"))
    List<Product> products = new ArrayList<>();

}
