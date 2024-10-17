package com.dayum.dayum_diet.domain.post.dto;

import jakarta.annotation.Nullable;

public record CreatePostRequestDto(
	String title,

	@Nullable
	String description
) {
}
