package com.epam.newsportal.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

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

    public News() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) &&
                Objects.equals(title, news.title) &&
                Objects.equals(date, news.date) &&
                Objects.equals(brief, news.brief) &&
                Objects.equals(content, news.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, brief, content);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", brief='" + brief + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
