package com.principal.pruebaspringbootweb.service;


import com.principal.pruebaspringbootweb.dto.PostDTO;
import com.principal.pruebaspringbootweb.model.Post;
import com.principal.pruebaspringbootweb.model.User;
import com.principal.pruebaspringbootweb.repository.PostRepository;
import com.principal.pruebaspringbootweb.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public List<PostDTO> getAllPostSortedByDate() {
//        return postRepository.findAll(Sort.by("publicationDate").descending())
//                .stream()
//                .map(post -> new ModelMapper().map(post, PostDTO.class))
//                .toList();

        return postRepository
                .findAllPostsOrderedByPublicationDateDesc()
                .stream()
                .map(post -> {
                    PostDTO postDTO = new PostDTO();
                    postDTO.setId(post.getId());
                    postDTO.setTitle(post.getTitle());
                    postDTO.setBody(post.getBody());
                    postDTO.setPublicationDate(post.getPublicationDate());
                    postDTO.setUserEmail(post.getUser() != null ? post.getUser().getEmail() : null);
                    postDTO.setCommentCount(post.getComments() != null ? post.getComments().size() : 0);
                    return postDTO;
                }).collect(Collectors.toList());

    }

    public PostDTO getPostById(Long id) {
         return postRepository.findPostById(id)
                 .stream()
                 .map(post -> new ModelMapper().map(post, PostDTO.class))
                 .findAny()
                 .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<PostDTO> getPostsByTitle(String title) {
//        return postRepository.findByTitleContainingIgnoreCase(title)
//                .stream()
//                .map(post -> new ModelMapper().map(post, PostDTO.class))
//                .toList();

//        return postRepository.findByTitleNativeQuery(title)
//                .stream()
//                .map(post -> new ModelMapper().map(post, PostDTO.class))
//                .toList();

          return postRepository.findByTitleFetching(title)
                  .stream()
                  .map(post -> new ModelMapper().map(post, PostDTO.class))
                  .toList();

    }


    public static String getAuthenticatedEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        } else {
            throw new IllegalStateException("El principal no es de un tipo esperado: " + principal.getClass());
        }
    }

    public void createPost(PostDTO postDTO) {

        Post post = new Post();
        post.setTitle(post.getTitle());
        post.setBody(post.getBody());
        post.setPublicationDate(LocalDateTime.now());

        String email = getAuthenticatedEmail();

//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = ((UserDetails) principal).getUsername();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

//        post.setUser(user);

//        postRepository.save(post);

        postRepository.insertPost(postDTO.getTitle(),  postDTO.getBody(), LocalDateTime.now(), user.getId());

    }

    @Transactional
    public void updatePost(Long id, String title, String body) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(title);
        post.setBody(body);
        postRepository.save(post);

    }

    public void deletePost(Long id) {

        postRepository.deletePostById(id);
    }
}
