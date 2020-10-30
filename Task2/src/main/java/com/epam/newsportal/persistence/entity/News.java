package com.epam.newsportal.persistence.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "news_table")
public class News extends AbstractEntity{

    @Id
    @GeneratedValue
    @Column(name = "news_id")
    private Long id;

    @Length(max = 100)
    @Column(name = "news_title")
    private String title;

    @Length(max = 500)
    @Column(name = "news_brief")
    private String brief;

    @Length(max = 5000)
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