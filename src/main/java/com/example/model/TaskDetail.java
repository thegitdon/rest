package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task_detail")
public class TaskDetail {

	// Now look at the tutorial_details table that contains a Primary Key column
	// (id) and a Foreign Key column (tutorial_id). You can see that we really need
	// only one tutorial_details (child) row associated with a tutorial (parent)
	// row, and the child data is hardly used in other relationships. So we can omit
	// the child’s id column

	@Id
	private Long id;

	@Column
	private Date createdOn;

	@Column
	private String createdBy;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "task_id")
	private Task task;

	public TaskDetail() {

	}

	public TaskDetail(String createdBy) {
		this.createdOn = new Date();
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Task getTask() {
		return task;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
