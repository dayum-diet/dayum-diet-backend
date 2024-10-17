package com.dayum.dayum_diet.domain.post;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dayum.dayum_diet.domain.post.dto.CreatePostRequestDto;
import com.dayum.dayum_diet.domain.post.dto.PostResponse;

@Component
public class PostMapper {

	public Post toEntity(CreatePostRequestDto createPostRequestDto, MultipartFile multipartFile) throws
		IOException {
		return Post.builder()
			.title(createPostRequestDto.title())
			.description(createPostRequestDto.description())
			.videoUrl(multipartFile.getOriginalFilename())
			.build();
	}

	public PostResponse toResponse(Post post) {
		return PostResponse.builder()
			.postId(post.getPostId())
			.approvalStatus(post.isApprovalStatus())
			.eatenCount(post.getEatenCount())
			.savedCount(post.getSavedCount())
			.title(post.getTitle())
			.description(post.getDescription())
			.videoUrl(post.getVideoUrl())
			.createdAt(post.getCreatedAt())
			.modifiedAt(post.getModifiedAt())
			.build();
	}
}
