package com.news.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NEWS_CATEGORY")
public class NewsCategoryEntity implements Serializable {

	private static final long serialVersionUID = 1590470404568034688L;

	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@NonNull
	@Column(name = "title", nullable = false)
	private String title;

	@NonNull
	@Column(name = "description", nullable = false)
	private String description;

}
