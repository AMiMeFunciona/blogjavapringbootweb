package com.principal.pruebaspringbootweb.repository;


import com.principal.pruebaspringbootweb.dto.PostDTO;
import com.principal.pruebaspringbootweb.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    @Query("SELECT p FROM Post p WHERE p.id = :id")
    Optional<Post> findPostById(@Param("id") Long id);

    List<Post> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT p.* FROM tb_posts p WHERE p.post_title LIKE %:title%", nativeQuery = true)
    List<Post> findByTitleNativeQuery(@Param("title") String title);

//    @Query("SELECT p FROM Post p " +
//            "JOIN FETCH p.user u " +
//            "LEFT JOIN FETCH p.comments c " +
//            "WHERE p.title LIKE %:title%")
//    List<Post> findByTitleFetching(@Param("title") String title);

    @Query("SELECT new com.principal.pruebaspringbootweb.dto.PostDTO(" +
            "p.id, p.title, p.body, p.publicationDate, u.email, SIZE(p.comments)) " +
            "FROM Post p " +
            "JOIN p.user u " +
            "LEFT JOIN p.comments c " +
            "WHERE p.title LIKE %:title%")
    List<PostDTO> findByTitleFetching(@Param("title") String title);


    @Query("SELECT p FROM Post p ORDER BY p.publicationDate DESC")
    List<Post> findAllPostsOrderedByPublicationDateDesc();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tb_posts (post_title, post_body, post_date, post_user) VALUES (:title, :body, :publicationDate, :userId)", nativeQuery = true)
    void insertPost(@Param("title") String title,
                    @Param("body") String body,
                    @Param("publicationDate") LocalDateTime publicationDate,
                    @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tb_posts WHERE post_id = :id", nativeQuery = true)
    void deletePostById(@Param("id") Long id);
}
