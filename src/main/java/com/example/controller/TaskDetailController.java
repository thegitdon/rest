package com.example.controller;

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

import com.example.model.Task;
import com.example.model.TaskDetail;
import com.example.repository.TaskDetailRepository;
import com.example.repository.TaskRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TaskDetailController {

	@Autowired
	private TaskDetailRepository tDRepo;

	@Autowired
	private TaskRepository tRepo;

	// Retrieve Details of specific Tutorial
	// @GetMapping({ "/details/{id}", "/tasks/{id}/details" })
	@GetMapping("/tasks/{id}/details")
	public ResponseEntity<TaskDetail> getDetailsById(@PathVariable(value = "id") Long id) {
		TaskDetail details = tDRepo.findById(id).orElseThrow();

		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@PostMapping("/tasks/{taskId}/details")
	public ResponseEntity<TaskDetail> createDetails(@PathVariable(value = "taskId") Long taskId,
			@RequestBody TaskDetail detailsRequest) {
		Task task = tRepo.findById(taskId).orElseThrow(); // ResourceNotFoundException

		detailsRequest.setCreatedOn(new java.util.Date());
		detailsRequest.setTask(task);
		TaskDetail details = tDRepo.save(detailsRequest);

		return new ResponseEntity<>(details, HttpStatus.CREATED);
	}

	// NO
	@DeleteMapping("/details/{id}")
	public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id) {
		tDRepo.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Delete Details of specific Tutorial
	@DeleteMapping("/tasks/{taskId}/details")
	public ResponseEntity<TaskDetail> deleteDetailsOfTutorial(@PathVariable(value = "taskId") Long taskId) {
		if (!tRepo.existsById(taskId)) {
			System.out.println(":(");
		}

		tDRepo.deleteByTaskId(taskId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Update Details of specific Tutorial
	// "/tasks/{taskId}/details"
	@PutMapping("/details/{id}")
	public ResponseEntity<TaskDetail> updateDetails(@PathVariable("id") long id,
			@RequestBody TaskDetail detailsRequest) {
		TaskDetail details = tDRepo.findById(id).orElseThrow();

		details.setCreatedBy(detailsRequest.getCreatedBy());

		return new ResponseEntity<>(tDRepo.save(details), HttpStatus.OK);
	}

}
