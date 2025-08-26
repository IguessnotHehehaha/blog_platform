package com.learning.blog.services.impl;

import com.learning.blog.domain.CreateCommentRequest;
import com.learning.blog.domain.UpdateCommentRequest;
import com.learning.blog.domain.dtos.CreateCommentRequestDto;
import com.learning.blog.domain.dtos.UpdateCommentRequestDto;
import com.learning.blog.domain.entities.Comment;
import com.learning.blog.domain.entities.Post;
import com.learning.blog.domain.entities.User;
import com.learning.blog.repositories.CommentRepository;
import com.learning.blog.services.CommentService;
import com.learning.blog.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    @Override
    public Comment getCommentById(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

    @Override
    public List<Comment> getCommentsByPost(UUID postId) {
        Post post = postService.getPostById(postId);
        return commentRepository.findAllByPost(post);
    }

    @Override
    @Transactional
    public Comment createComment(User user, CreateCommentRequest createCommentRequest) {
        Post post =  postService.getPostById(createCommentRequest.getPostId());

        Comment comment = Comment.builder()
                .content(createCommentRequest.getContent())
                .author(user).post(post).build();

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(UpdateCommentRequest updateCommentRequest) {
        Comment existing = getCommentById(updateCommentRequest.getId());

        existing.setContent(updateCommentRequest.getContent());

        return commentRepository.save(existing);
    }


    @Override
    @Transactional
    public void deleteComment(UUID id) {
        Comment existing = getCommentById(id);

        commentRepository.delete(existing);
    }
}
