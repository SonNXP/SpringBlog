package com.raysmond.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Raysmond
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "userCache")
public class User extends BaseModel {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @Column(unique = true)
    private String email;

    // use Spring Boot or Jackson Serializer: To hide sensitive data
    // https://medium.com/@bhanuchaddha/using-jsonignore-or-jsonproperty-to-hide-sensitive-data-in-json-response-ad12b1aacbf3
    // But belew @JsonIgnore blocks both Setter and Getter -> cannot set password
    // Since version 2.6: @JsonProperty(access = Access.WRITE_ONLY), can set but not
    // show (get)
    @JsonIgnore
    private String password;

    private String role = ROLE_USER;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    // Need to Store and Accessing Data(fast Iterator and random access) -> use
    // ArrayList
    private Collection<Post> posts = new ArrayList<>();

    public User() {

    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
