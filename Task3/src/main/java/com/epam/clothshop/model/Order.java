package com.epam.clothshop.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    @Column(name = "order_id")
    Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_ship_date")
    Date shipDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_created_at")
    Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    Status status;

    @Column(name = "order_complete")
    Boolean complete;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_product_table",
            joinColumns = @JoinColumn(name = "order_fk"),
            inverseJoinColumns = @JoinColumn(name = "product_fk"))
    List<Product> items;

    public enum Status {
        PLACED, APPROVED, DELIVERED;
    }
}
