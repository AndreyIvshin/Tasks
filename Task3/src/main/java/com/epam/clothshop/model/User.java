package com.epam.clothshop.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @Column(name = "user_id")
    Long id;

    @Column(name = "user_username")
    String username;

    @Column(name = "user_first_name")
    String firstName;

    @Column(name = "user_last_name")
    String lastName;

    @Column(name = "user_email")
    String email;

    @Column(name = "user_password")
    String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_order_table",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "order_fk"))
    List<Order> orders;

}
