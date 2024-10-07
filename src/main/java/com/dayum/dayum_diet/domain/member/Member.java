package com.dayum.dayum_diet.domain.member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dayum.dayum_diet.common.auditing.BaseEntity;
import com.dayum.dayum_diet.domain.loginPlatform.LoginPlatform;
import com.dayum.dayum_diet.domain.member.enums.Gender;
import com.dayum.dayum_diet.domain.member.enums.Role;
import com.dayum.dayum_diet.domain.post.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "member_id")
	private UUID memberId;

	@Column(length = 255, nullable = false)
	private String name;

	@Column(length = 255, unique = true)
	private String nickname;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String ageRange;

	@Column(length = 100)
	private String introduction;

	private Integer height;

	@Column(precision = 4, scale = 1)
	private BigDecimal currentWeight;

	@Column(precision = 4, scale = 1)
	private BigDecimal goalWeight;

	@Column(length = 8192)
	private String profileImageUrl;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "member")
	private List<Post> posts;

	@OneToMany(mappedBy = "member")
	private List<LoginPlatform> platforms;

	@Builder
	public Member(UUID memberId, String name, String nickname, Gender gender, String ageRange, String introduction,
		Integer height, BigDecimal currentWeight, BigDecimal goalWeight, String profileImageUrl, Role role,
		List<Post> posts, List<LoginPlatform> platforms) {
		this.memberId = memberId;
		this.name = name;
		this.nickname = nickname;
		this.gender = gender;
		this.ageRange = ageRange;
		this.introduction = introduction;
		this.height = height;
		this.currentWeight = currentWeight;
		this.goalWeight = goalWeight;
		this.profileImageUrl = profileImageUrl;
		this.role = role;
		this.posts = posts;
		this.platforms = platforms;
	}
}

