package com.learning.blog.controller;

import com.learning.blog.domain.CreateCommentRequest;
import com.learning.blog.domain.UpdateCommentRequest;
import com.learning.blog.domain.dtos.CommentDto;
import com.learning.blog.domain.dtos.CreateCommentRequestDto;
import com.learning.blog.domain.dtos.UpdateCommentRequestDto;
import com.learning.blog.domain.entities.Comment;
import com.learning.blog.domain.entities.User;
import com.learning.blog.mappers.CommentMapper;
import com.learning.blog.services.CommentService;
import com.learning.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @GetMapping(path = "/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable UUID postId) {
        List<Comment> comments = commentService.getCommentsByPost(postId);
        List<CommentDto> commentDtos = comments.stream().map(commentMapper::toDto).toList();
        return ResponseEntity.ok(commentDtos);
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody @Valid CreateCommentRequestDto createCommentRequestDto,
                                                    @RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        CreateCommentRequest createCommentRequest = commentMapper.toCreateCommentRequest(createCommentRequestDto);
        Comment createdComment = commentService.createComment(loggedInUser, createCommentRequest);
        CommentDto createdCommentDto = commentMapper.toDto(createdComment);
        return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable UUID id, @RequestBody @Valid UpdateCommentRequestDto updateCommentRequestDto) {

        log.info("Update comment by id {}", id);

        UpdateCommentRequest updateCommentRequest = commentMapper.toUpdateCommentRequest(updateCommentRequestDto);
        updateCommentRequest.setId(id);

        Comment updatedComment = commentService.updateComment(updateCommentRequest);
        CommentDto updatedCommentDto = commentMapper.toDto(updatedComment);
        return ResponseEntity.ok(updatedCommentDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable UUID id) {
        Comment comment = commentService.getCommentById(id);
        CommentDto commentDto = commentMapper.toDto(comment);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommentDto> deleteCommentById(@PathVariable UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}