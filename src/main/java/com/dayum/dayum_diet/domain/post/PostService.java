package com.dayum.dayum_diet.domain.post;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dayum.dayum_diet.domain.post.dto.CreatePostRequestDto;
import com.dayum.dayum_diet.domain.post.dto.PostResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final PostMapper postMapper;

	@Transactional
	public PostResponse createPost(CreatePostRequestDto createPostRequestDto, MultipartFile multipartFile) throws IOException {
		Post post = postRepository.save(postMapper.toEntity(createPostRequestDto, multipartFile));
		return postMapper.toResponse(post);
	}
}
