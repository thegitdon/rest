package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	// custom finder methods
	// The implementation is plugged in by Spring Data JPA automatically.

	List<Task> findByPublished(boolean published);

	List<Task> findByTitleContaining(String title);

	// Now we can use JpaRepository’s methods :)
	// without implementing these methods.
	List<Task> findTasksByTagsId(Long tagId); // returns all Tutorials related to Tag
}
