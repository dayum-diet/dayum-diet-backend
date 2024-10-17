package com.dayum.dayum_diet.domain.post.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
	Long postId;
	boolean approvalStatus;
	long eatenCount;
	long savedCount;
	String title;
	String description;
	String videoUrl;
	LocalDateTime createdAt;
	LocalDateTime modifiedAt;

	@Builder
	public PostResponse(Long postId, boolean approvalStatus, long eatenCount, long savedCount, String title,
		String description, String videoUrl, LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.postId = postId;
		this.approvalStatus = approvalStatus;
		this.eatenCount = eatenCount;
		this.savedCount = savedCount;
		this.title = title;
		this.description = description;
		this.videoUrl = videoUrl;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}
}
