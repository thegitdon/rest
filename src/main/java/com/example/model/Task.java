package com.example.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer" }) //
@Entity
@Table(name = "task")
public class Task {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private boolean published;

	private String file_path;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "task_tags", joinColumns = { @JoinColumn(name = "task_id") }, inverseJoinColumns = {
			@JoinColumn(name = "tag_id") })
	private Set<Tag> tags = new HashSet<>();

	public Task() {

	}

	public Task(String title, String description, boolean published) {
		this.title = title;
		this.description = description;
		this.published = published;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
		tag.getTutorials().add(this);
	}

	public void removeTag(long tagId) {
		Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
		if (tag != null) {
			this.tags.remove(tag);
			tag.getTutorials().remove(this);
		}
	}

}
