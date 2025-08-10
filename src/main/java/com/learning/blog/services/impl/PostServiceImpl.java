package com.learning.blog.services.impl;

import com.learning.blog.domain.CreatePostRequest;
import com.learning.blog.domain.PostStatus;
import com.learning.blog.domain.UpdatePostRequest;
import com.learning.blog.domain.entities.Category;
import com.learning.blog.domain.entities.Post;
import com.learning.blog.domain.entities.Tag;
import com.learning.blog.domain.entities.User;
import com.learning.blog.repositories.PostRepository;
import com.learning.blog.services.CategoryService;
import com.learning.blog.services.PostService;
import com.learning.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final CategoryService categoryService;

    private static final int WPM = 100;

    @Override
    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post doesn't exists with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if(categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag =  tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }
        if(categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }
        if(tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculatePostReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {

        Post existingPost = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post doesn't exists with id: " + id));
        existingPost.setTitle(updatePostRequest.getTitle());
        String postContent = updatePostRequest.getContent();
        existingPost.setContent(postContent);

        existingPost.setStatus(updatePostRequest.getStatus());
        /*if (updatePostRequest.getStatus() != null) {
            existingPost.setStatus(updatePostRequest.getStatus());
        }*/


        existingPost.setReadingTime(calculatePostReadingTime(postContent));

        UUID updatePostReqeustCategoryId = updatePostRequest.getCategoryId();
        if(!existingPost.getCategory().getId().equals(updatePostReqeustCategoryId)) {
            Category newCategory = categoryService.getCategoryById(updatePostReqeustCategoryId);
            existingPost.setCategory(newCategory);
        }

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    private Integer calculatePostReadingTime(String content){
        if(content == null || content.isEmpty()) {
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double)wordCount / WPM);
    }
}
