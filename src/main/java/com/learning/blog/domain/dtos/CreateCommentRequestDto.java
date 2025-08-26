package com.learning.blog.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequestDto {

    @NotBlank(message = "Comment should not be blank")
    @Size(min = 10, max = 1000, message = "Comment should be between {min} and {max} characters")
    private String content;

    @NotNull(message = "Post id is required")
    private UUID postId;
}
