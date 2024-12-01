package com.principal.pruebaspringbootweb.service;


import com.principal.pruebaspringbootweb.dto.CommentDTO;
import com.principal.pruebaspringbootweb.model.Comment;
import com.principal.pruebaspringbootweb.model.Post;
import com.principal.pruebaspringbootweb.model.User;
import com.principal.pruebaspringbootweb.repository.CommentRepository;
import com.principal.pruebaspringbootweb.repository.PostRepository;
import com.principal.pruebaspringbootweb.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> new ModelMapper().map(comment, CommentDTO.class))
                .collect(Collectors.toList());

    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(comment -> new ModelMapper().map(comment, CommentDTO.class))
                .collect(Collectors.toList());

    }

    public CommentDTO getCommentById(Long id) {
        return commentRepository.findById(id)
                .stream()
                .map(comment -> new ModelMapper().map(comment, CommentDTO.class))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Transactional
    public int updateComment(Long id, String body) {
        return commentRepository.updateComment(id, body);
    }

    @Transactional
    public void createComment(Long postId, CommentDTO commentDTO) {

        Comment comment = new Comment();
        comment.setBody(commentDTO.getBody());
        comment.setPublicationDate(LocalDateTime.now());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);

        commentRepository.save(comment);

    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
