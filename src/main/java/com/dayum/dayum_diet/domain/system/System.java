package com.dayum.dayum_diet.domain.system;

import com.dayum.dayum_diet.common.auditing.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class System extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "system_id")
	private Long systemId;

	@Column(name = "app_version", nullable = false, length = 10)
	private String appVersion;
}
