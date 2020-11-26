package com.epam.newsportal.model;

import com.epam.newsportal.enumeration.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_table")
public class User extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "user_sequence_generator", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_generator")
    @Column(name = "user_id")
    Long id;

    @Size(min = 2, max = 40)
    @Column(name = "user_username")
    String username;

    @Column(name = "user_password")
    String password;

    @Transient
    String passwordRepeat;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role")
    Role role;

}
