package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Comment;
import com.example.repository.CommentRepository;
import com.example.repository.TaskRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private TaskRepository tRepo;

	@Autowired
	private CommentRepository cRepo;

	// Retrieve all Comments of specific Tutorial
	@GetMapping("/tasks/{taskId}/comments")
	public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable(value = "taskId") Long taskId) {
		if (!tRepo.existsById(taskId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id = " + taskId);
		}

		List<Comment> comments = cRepo.findByTaskId(taskId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	// retrieve a Comment by :id
	@GetMapping("/comments/{id}")
	public ResponseEntity<Comment> getCommentsByTutorialId(@PathVariable(value = "id") Long id) {
		Comment comment = cRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));

		return new ResponseEntity<>(comment, HttpStatus.OK);
	}

	@PostMapping("/tasks/{taskId}/comments")
	public ResponseEntity<Comment> createComment(@PathVariable(value = "taskId") Long taskId,
			@RequestBody Comment commentRequest) {

		Comment comment = tRepo.findById(taskId).map(task -> {
			commentRequest.setTask(task);
			return cRepo.save(commentRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + taskId));
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}

	@PutMapping("/comments/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
		Comment comment = cRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));

		comment.setContent(commentRequest.getContent());

		return new ResponseEntity<>(cRepo.save(comment), HttpStatus.OK);
	}

	// delete a Comment by
	@DeleteMapping("/comments/{id}")
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
		cRepo.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Delete all Comments of specific Tutorial
	@DeleteMapping("/tasks/{taskId}/comments")
	public ResponseEntity<List<Comment>> deleteAllCommentsOfTutorial(@PathVariable(value = "taskId") Long taskId) {
		if (!tRepo.existsById(taskId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id = " + taskId);
		}

		cRepo.deleteByTaskId(taskId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
