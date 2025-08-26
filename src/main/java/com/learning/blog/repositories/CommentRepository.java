package com.learning.blog.repositories;

import com.learning.blog.domain.entities.Comment;
import com.learning.blog.domain.entities.Post;
import com.learning.blog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByAuthor(User author);
}
