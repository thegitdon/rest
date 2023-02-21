package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String content;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) // optional element is set to false for non-null relationship.
	@JoinColumn(name = "task_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Task task;

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Task getTask() {
		return task;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
