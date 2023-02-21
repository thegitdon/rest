package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByTaskId(Long postId); // returns all Comments of a Tutorial specified by tutorialId.

	@Transactional
	void deleteByTaskId(long tutorialId); // deletes all Comments of a Tutorial specified by tutorialId.

}
