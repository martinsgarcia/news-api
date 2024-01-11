package com.news.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "NEWS")
public class NewsEntity implements Serializable {

	private static final long serialVersionUID = -6341665969339782892L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NonNull
	@Column(name = "title", nullable = false)
	private String title;

	@NonNull
	@Column(name = "description", nullable = false)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "news_category_id", nullable = false)
	private NewsCategoryEntity category;

}
