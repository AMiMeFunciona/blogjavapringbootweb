package com.principal.pruebaspringbootweb.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tb_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_body")
    private String body;

    @Column(name = "comment_date")
    private LocalDateTime publicationDate;

    @ManyToOne
    @JoinColumn(name = "comment_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_post")
    private Post post;


}
