package com.example.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.TaskDetail;

@Repository
public interface TaskDetailRepository extends JpaRepository<TaskDetail, Long> {

	@Transactional
	void deleteById(long id); // deletes Details specified by id

	@Transactional
	void deleteByTaskId(long taskId); // deletes Details of a Tutorial specified by tutorialId
}
