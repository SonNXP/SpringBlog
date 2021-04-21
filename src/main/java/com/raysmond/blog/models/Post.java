package com.raysmond.blog.models;

import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
// generate the default getter/setter automatically.
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Raysmond
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
// Caching is a mechanism to enhance the performance of a system. It is a buffer
// memorythat lies between the application and the database. Cache memory stores
// recently used data items in order to reduce the number of database hits as
// much as possible.
// https://stackoverflow.com/questions/1837651/hibernate-cache-strategy
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "postCache")
public class Post extends BaseModel {
    private static final SimpleDateFormat SLUG_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    // In Java, one-to-one relationship (normally called association) is where an
    // object has a reference (instance variable) of the other object.
    // In database world, one-to-one relationship is where a table A has a special
    // column, known as foreign-key column, referencing to the primary key column of
    // another table B. Table A is known as child-table, whereas, the table B is
    // known as parent-table.
    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String title;

    @Type(type = "text")
    private String content;

    @Type(type = "text")
    private String renderedContent;

    @Type(type = "text")
    private String summary;

    @Type(type = "text")
    private String renderedSummary;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.PUBLISHED;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostFormat postFormat = PostFormat.MARKDOWN;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType = PostType.POST;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "posts_tags", joinColumns = {
            @JoinColumn(name = "post_id", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", nullable = false, updatable = false) })
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "tagCache")
    private Set<Tag> tags = new HashSet<>();

    private String permalink;

    private Integer views = 0;

    public Integer getViews() {
        return views == null ? 0 : views;
    }

    public String getRenderedContent() {
        if (this.postFormat == PostFormat.MARKDOWN) {
            return renderedContent;
        }

        return getContent();
    }

    public void setPermalink(String permalink) {
        String token = permalink.toLowerCase().replace("\n", " ").replaceAll("[^a-z\\d\\s]", " ");
        this.permalink = StringUtils.arrayToDelimitedString(StringUtils.tokenizeToStringArray(token, " "), "-");
    }
}
