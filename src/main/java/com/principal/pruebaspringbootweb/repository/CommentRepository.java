package com.principal.pruebaspringbootweb.repository;


import com.principal.pruebaspringbootweb.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> findByPostId(Long postId);

    @Modifying
    @Query("UPDATE Comment c SET c.body = :body WHERE c.id = :id")
    int updateComment(@Param("id") Long id, @Param("body") String body);


}
