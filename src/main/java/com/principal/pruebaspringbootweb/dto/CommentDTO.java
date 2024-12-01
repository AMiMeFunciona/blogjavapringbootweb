package com.principal.pruebaspringbootweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private String body;
    private LocalDateTime publicationDate;
    private Long postId;
    private String userEmail;

}
