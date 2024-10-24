package com.dayum.dayum_diet.domain.searchHistory;

import com.dayum.dayum_diet.common.auditing.BaseEntity;
import com.dayum.dayum_diet.domain.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SearchHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "search_id")
	private Long searchId;

	@Column(name = "search_keyword", nullable = false, length = 255)
	private String searchKeyword;

	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	private Member member;
}
