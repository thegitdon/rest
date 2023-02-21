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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Task;
import com.example.repository.TaskDetailRepository;
import com.example.repository.TaskRepository;

//@CrossOrigin(origins = "http://localhost:8080")
@CrossOrigin(origins = "*")
@RestController // used to define a controller and to indicate that the return value of the
				// methods should be be bound to the web response body.
@RequestMapping("/api") // all Apis’ url in the controller will start with /api
public class TaskController {

	// Way to create Spring Rest Controller to process HTTP requests

	@Autowired // to inject Repository bean to local variable
	TaskRepository tRepo;

	@Autowired
	private TaskDetailRepository tDRepo;

	@GetMapping("/tasks")
	public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) String title) {

		if (title == null)
			tRepo.findAll();
		else
			tRepo.findByTitleContaining(title);

		if (tRepo.findAll().isEmpty() || tRepo.findByTitleContaining(title).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * public List<Task> getAllTasks(@RequestParam(required = false) String title) {
	 * 
	 * if (title == null) return tRepo.findAll(); else return
	 * tRepo.findByTitleContaining(title); // Find all Tutorials which title
	 * contains ‘boot’ // http://localhost:8080/api/tasks?title=boot }
	 */
	/*
	 * public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required
	 * = false) String title) { List<Tutorial> tutorials = new
	 * ArrayList<Tutorial>();
	 * 
	 * if (title == null) tutorialRepository.findAll().forEach(tutorials::add); else
	 * tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
	 * 
	 * if (tutorials.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 * 
	 * return new ResponseEntity<>(tutorials, HttpStatus.OK); }
	 */

	@GetMapping("/tasks/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable("id") long id) {
		/*
		 * Optional<Task> t = tRepo.findById(id);
		 * 
		 * if (t.isPresent()) { return new ResponseEntity<>(t.get(), HttpStatus.OK); }
		 * else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
		 */
		Task task = tRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Task with id = " + id));

		return new ResponseEntity<>(task, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/tutorials") public ResponseEntity<List<Tutorial>>
	 * getAllTutorials(@RequestParam(required = false) String title) {
	 * List<Tutorial> tutorials = new ArrayList<Tutorial>();
	 * 
	 * if (title == null) tutorialRepository.findAll().forEach(tutorials::add); else
	 * tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
	 * 
	 * if (tutorials.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 * 
	 * return new ResponseEntity<>(tutorials, HttpStatus.OK); }
	 */

	@GetMapping("/tasks/published")
	public ResponseEntity<List<Task>> findByPublished() {
		// try {
		List<Task> tasks = tRepo.findByPublished(true);

		if (tasks.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(tasks, HttpStatus.OK);
		/*
		 * } catch (Exception e) { return new
		 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); }
		 */
	}

	@PostMapping("/tasks")
	public ResponseEntity<Task> createTask(@RequestBody Task t) {
		/*
		 * try { Task _task = tRepo.save(new Task(t.getTitle(), t.getDescription(),
		 * false)); return new ResponseEntity<>(_task, HttpStatus.CREATED); } catch
		 * (Exception e) { return new ResponseEntity<>(null,
		 * HttpStatus.INTERNAL_SERVER_ERROR); }
		 */
		Task _task = tRepo.save(new Task(t.getTitle(), t.getDescription(), false));
		return new ResponseEntity<>(_task, HttpStatus.CREATED);

	}

	@PutMapping("/tasks/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task t) {
		/*
		 * Optional<Task> taskData = tRepo.findById(id); if (taskData.isPresent()) {
		 * Task _task = taskData.get(); _task.setTitle(t.getTitle());
		 * _task.setDescription(t.getDescription());
		 * _task.setPublished(t.isPublished());
		 * 
		 * return new ResponseEntity<>(tRepo.save(_task), HttpStatus.OK); } else {
		 * return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
		 */
		Task _task = tRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Task with id = " + id));

		_task.setTitle(t.getTitle());
		_task.setDescription(t.getDescription());
		_task.setPublished(t.isPublished());

		return new ResponseEntity<>(tRepo.save(_task), HttpStatus.OK);
	}

	/*
	 * @DeleteMapping("/tasks/{id}") public void deleteTask(@PathVariable("id") long
	 * id) {
	 * 
	 * tRepo.deleteById(id); }
	 */

	// Delete a Tutorial and its Details
	// delete a Tutorial (and its Comments) by :id
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {
		if (tDRepo.existsById(id)) {
			tDRepo.deleteById(id);
		}

		tRepo.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/*
	 * @DeleteMapping("/tutorials") public ResponseEntity<HttpStatus>
	 * deleteAllTutorials() { tutorialRepository.deleteAll();
	 * 
	 * return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 */

}
