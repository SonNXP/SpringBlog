package com.raysmond.blog.models;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * An abstract base model class for entities
 *
 * @author Raysmond
 */

/**
 * * BaseModel includes common attributes of Post, Setting, Tag, User entities *
 * anotation @MappedSuperclass that make the entities can inherite from
 * BaseModel
 * 
 * Define compareTo() to use Comparable serializable purposes: convert/serialize
 * (writeObject(Obj)) Java Obj into a byte array that can store in file, DB,
 * memory and other components can deserialize (readObject(Obj)) the array byte
 * into Java Obj and read the Obj normally
 */
@MappedSuperclass
public abstract class BaseModel implements Comparable<BaseModel>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    /**
     * JPA callback methods to listen save/update/remove this entity by
     * EntityManager only. JPA uses EntityManager for handling the persistence of
     * data.
     */
    // @PrePersist: Thực thi trước khi entity được persist (được lưu vào database)
    // bởi method persist()

    // @PostPersist: Thực thi sau khi entity được persist

    // @PostLoad: Thực thi sau khi một entity được load vào persistence context hiện
    // tại hoặc một entity được refreshed.

    // @PreUpdate: Thực thi trước khi entity được update.

    // @PostUpdate: Thực thi sau khi entity được update.

    // @PreRemove: Thực thi trước khi entity bị xóa khỏi database bởi method
    // remove()

    // @PostRemove: Thực thi sau khi entity bị xóa.
    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    @Override
    public int compareTo(BaseModel o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }

        return this.getId().equals(((BaseModel) other).getId());
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long _id) {
        id = _id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}