package com.epam.newsportal.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "news_table")
public class News extends AbstractEntity{

    @Id
    @SequenceGenerator(name = "news_sequence_generator", sequenceName = "news_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_sequence_generator")
    @Column(name = "news_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "news_title")
    private String title;

    @NotNull
    @Size(min = 2, max = 500)
    @Column(name = "news_brief")
    private String brief;

    @NotNull
    @Size(min = 2, max = 5000)
    @Column(name = "news_content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "news_date")
    private Date date;

}
