package com.principal.pruebaspringbootweb.controller;


import com.principal.pruebaspringbootweb.dto.CommentDTO;
import com.principal.pruebaspringbootweb.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getall")
    public String getAllComments(Model model) {
        List<CommentDTO> comments = commentService.getAllComments();
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/comments/{postId}")
    public String getComments(@PathVariable Long postId, Model model) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/comments/detail/{id}")
    public String viewComment(@PathVariable Long id, Model model) {
        CommentDTO commentDTO = commentService.getCommentById(id);
        model.addAttribute("comment", commentDTO);
        return "comment";
    }


    @GetMapping("/new/{postId}")
    public String newCommentForm(@PathVariable Long postId, Model model) {
        model.addAttribute("postId", postId);
        return "newcomment";
    }

    @PostMapping("/new/{postId}")
    public String createComment(@PathVariable Long postId, @RequestParam String body) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBody(body);
        commentService.createComment(postId, commentDTO);
        return "redirect:/comment/comments/" + postId;
    }

    @PutMapping("/update/{id}")
    public String updateComment(@PathVariable Long id, @RequestParam String body) {
        commentService.updateComment(id, body);
        return "redirect:/comment/comments/detail/" + id;
    }

    @DeleteMapping("/delete/{postId}/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/comment/comments/" + postId;
    }

}
