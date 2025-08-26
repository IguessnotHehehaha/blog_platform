package com.learning.blog.mappers;

import com.learning.blog.domain.CreateCommentRequest;
import com.learning.blog.domain.UpdateCommentRequest;
import com.learning.blog.domain.dtos.*;
import com.learning.blog.domain.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "author", target = "author")
    @Mapping(source = "post.id", target = "postId")
    CommentDto toDto(Comment comment);

    CreateCommentRequest toCreateCommentRequest(CreateCommentRequestDto dto);

    UpdateCommentRequest toUpdateCommentRequest(UpdateCommentRequestDto dto);
}
