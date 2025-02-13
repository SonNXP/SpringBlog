package com.raysmond.blog.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A generic setting model
 *
 * @author Raysmond
 */
@Entity
@Table(name = "settings")
@Getter
@Setter
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "settingCache")
public class Setting extends BaseModel {
    @Column(name = "_key", unique = true, nullable = false)
    private String key;

    // represent as BLOB (binary data) in the DataBase.
    // https://stackoverflow.com/questions/29511133/what-is-the-significance-of-javax-persistence-lob-annotation-in-jpa
    @Lob
    @Column(name = "_value")
    private Serializable value;

}
