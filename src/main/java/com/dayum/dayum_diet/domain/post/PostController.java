package com.dayum.dayum_diet.domain.post;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dayum.dayum_diet.domain.post.dto.CreatePostRequestDto;
import com.dayum.dayum_diet.domain.post.dto.PostResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<PostResponse> createPost(
		@RequestPart("data") CreatePostRequestDto createPostRequestDto,
		@RequestPart("video-file") MultipartFile multipartFile) throws IOException {
		return new ResponseEntity<>(postService.createPost(createPostRequestDto, multipartFile), HttpStatus.CREATED);
	}
}
