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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentRequestDto {

    @NotBlank(message = "comment must not be blank")
    @Size(min = 10, max = 1000, message = "comment must be between {min} and {max} characters")
    private String content;

    @NotNull(message = "comment id is required")
    private UUID id;
}
