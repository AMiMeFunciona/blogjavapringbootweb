package com.principal.pruebaspringbootweb.controller;


import com.principal.pruebaspringbootweb.dto.PostDTO;
import com.principal.pruebaspringbootweb.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/getall")
    public String getAllPost(Model model) {
        List<PostDTO> posts = postService.getAllPostSortedByDate();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/posts/detail/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostDTO post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/searchposts")
    public String listPosts(Model model, @RequestParam(value = "title", required = false) String title) {
        List<PostDTO> posts;
        if (title != null && !title.isEmpty()) {
            posts = postService.getPostsByTitle(title);
        } else {
            posts = postService.getAllPostSortedByDate();
        }
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/new")
    public String newPostForm() {
        return "newpost";
    }

    @PostMapping("/new")
    public String createPost(@RequestParam String title, @RequestParam String body) {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(title);
        postDTO.setBody(body);
        postService.createPost(postDTO);
        return "redirect:/blog/getall";
    }

    @PutMapping("/update/{id}")
    public String updatePost(@PathVariable Long id, @RequestParam String title, @RequestParam String body) {
        postService.updatePost(id, title, body);
        return "redirect:/blog/posts/detail/" + id;
    }

    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/blog/getall";
    }

}
