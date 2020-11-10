package com.epam.clothshop.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data @NoArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "category_table")
public class Category {

    @Id
    @SequenceGenerator(name = "category_squ_gen", sequenceName = "category_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_squ_gen")
    @Column(name = "category_id")
    Long id;

    @Column(name = "category_name")
    String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<Product> products;

}
