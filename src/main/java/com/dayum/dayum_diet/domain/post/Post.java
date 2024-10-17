package com.dayum.dayum_diet.domain.post;

import java.util.UUID;

import com.dayum.dayum_diet.common.auditing.BaseEntity;
import com.dayum.dayum_diet.domain.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "post_id")
	private Long postId;

	@Column(name = "approval_status", nullable = false)
	private boolean approvalStatus;

	@Column(name = "eaten_count", nullable = false)
	private long eatenCount;

	@Column(name = "saved_count", nullable = false)
	private long savedCount;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(length = 2000)
	private String description;

	@Column(name = "video_url", nullable = false, length = 8192)
	private String videoUrl;

	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	private Member member;

	@Builder
	public Post(Long postId, boolean approvalStatus, long eatenCount, long savedCount, String title,
		String description, String videoUrl) {
		this.postId = postId;
		this.approvalStatus = approvalStatus;
		this.eatenCount = eatenCount;
		this.savedCount = savedCount;
		this.title = title;
		this.description = description;
		this.videoUrl = videoUrl;
	}
}
