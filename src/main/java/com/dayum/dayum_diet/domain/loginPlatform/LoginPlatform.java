package com.dayum.dayum_diet.domain.loginPlatform;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginPlatform extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "login_platform_id")
	private Long loginPlatformId;

	@Column(length = 255, nullable = false)
	private String email;

	@Column(length = 6, nullable = false)
	private String platform;

	@ManyToOne
	@JoinColumn(name = "member_id",referencedColumnName = "member_id")
	private Member member;

	@Builder
	public LoginPlatform(Long loginPlatformId, String email, String platform, Member member) {
		this.loginPlatformId = loginPlatformId;
		this.email = email;
		this.platform = platform;
		this.member = member;
	}

}
