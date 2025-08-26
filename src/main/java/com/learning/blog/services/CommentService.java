package com.learning.blog.services;

import com.learning.blog.domain.CreateCommentRequest;
import com.learning.blog.domain.UpdateCommentRequest;
import com.learning.blog.domain.entities.Comment;
import com.learning.blog.domain.entities.Post;
import com.learning.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    Comment getCommentById(UUID id);
    List<Comment> getCommentsByPost(UUID postId);
    Comment createComment(User user, CreateCommentRequest createCommentRequest);
    Comment  updateComment(UpdateCommentRequest updateCommentRequest);
    void deleteComment(UUID id);
}
