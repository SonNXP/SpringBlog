package com.raysmond.blog.repositories;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Raysmond
 */
// @Repository annotates classes at the persistence layer, which will act as a
// database repository, Create Read Update Delete - CRUD operations
@Repository
public interface PostRepository extends JpaRepository<Post, Long> { // JpaRepository.class (gradle) is different from
                                                                    // pom.xml ?
    Post findByPermalinkAndPostStatus(String permalink, PostStatus postStatus);

    // A page is a sublist of a list of objects. It allows gain information about
    // the position of it in the containing entire list.
    Page<Post> findAllByPostType(PostType postType, Pageable pageRequest);

    Page<Post> findAllByPostTypeAndPostStatus(PostType postType, PostStatus postStatus, Pageable pageRequest);

    // Post entity/table INNER JOIN Tags entity/table, t is variable of Tags
    // Colon ":" in SQL: a bind variable, allow a single SQL statement (whether a
    // query or DML) to be re-used many times, which helps security (by disallowing
    // SQL injection attacks) and performance (by reducing the amount of parsing
    // required).
    @Query("SELECT p FROM Post p INNER JOIN p.tags t WHERE t.name = :tag")
    Page<Post> findByTag(@Param("tag") String tag, Pageable pageable);

    @Query("SELECT t.name, count(p) as tag_count from Post p " + "INNER JOIN p.tags t "
            + "WHERE p.postStatus = :status " + "GROUP BY t.id " + "ORDER BY tag_count DESC")
    List<Object[]> countPostsByTags(@Param("status") PostStatus status);
}
// This is a tag for a named query parameter, and is not part of the query's
// actual syntax. The tag is replaced with some value specified in the code that
// makes the query before it is actually run.