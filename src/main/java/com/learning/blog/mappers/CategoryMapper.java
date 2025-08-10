package com.learning.blog.mappers;

import com.learning.blog.domain.PostStatus;
import com.learning.blog.domain.dtos.CategoryDTO;
import com.learning.blog.domain.dtos.CreateCategoryRequest;
import com.learning.blog.domain.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDTO toDTO(Category category);

    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Named("calculatePostCount")
    default long calculatePostCount(java.util.List<com.learning.blog.domain.entities.Post> posts) {
        if(null == posts || posts.isEmpty()) {
            return 0;
        }
        return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }
}
