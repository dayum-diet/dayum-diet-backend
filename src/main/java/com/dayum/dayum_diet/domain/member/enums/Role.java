package com.dayum.dayum_diet.domain.member.enums;

public enum Role {
	ROLE_GUEST("ROLE_GUEST"),
	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN");

	private String name;

	Role(String name) {
		this.name = name;
	}
}
