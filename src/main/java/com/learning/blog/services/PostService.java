package com.learning.blog.services;

import com.learning.blog.domain.CreatePostRequest;
import com.learning.blog.domain.UpdatePostRequest;
import com.learning.blog.domain.dtos.CreatePostRequestDto;
import com.learning.blog.domain.entities.Post;
import com.learning.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPostById(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void  deletePost(UUID id);
}
