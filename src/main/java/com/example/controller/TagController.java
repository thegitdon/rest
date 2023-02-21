package com.example.controller;

import java.util.ArrayList;
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
import com.example.model.Tag;
import com.example.model.Task;
import com.example.repository.TagRepository;
import com.example.repository.TaskRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TagController {

	@Autowired
	private TaskRepository tutorialRepository;

	@Autowired
	private TagRepository tagRepository;

	@GetMapping("/tags")
	public ResponseEntity<List<Tag>> getAllTags() {
		List<Tag> tags = new ArrayList<Tag>();

		tagRepository.findAll().forEach(tags::add);

		if (tags.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(tags, HttpStatus.OK);
	}

	@GetMapping("/tasks/{taskId}/tags")
	public ResponseEntity<List<Tag>> getAllTagsByTutorialId(@PathVariable(value = "taskId") Long tutorialId) {
		if (!tutorialRepository.existsById(tutorialId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
		}

		List<Tag> tags = tagRepository.findTagsByTasksId(tutorialId);
		return new ResponseEntity<>(tags, HttpStatus.OK);
	}

	@GetMapping("/tags/{id}")
	public ResponseEntity<Tag> getTagsById(@PathVariable(value = "id") Long id) {
		Tag tag = tagRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));

		return new ResponseEntity<>(tag, HttpStatus.OK);
	}

	@GetMapping("/tags/{tagId}/tasks")
	public ResponseEntity<List<Task>> getAllTutorialsByTagId(@PathVariable(value = "tagId") Long tagId) {
		if (!tagRepository.existsById(tagId)) {
			throw new ResourceNotFoundException("Not found Tag  with id = " + tagId);
		}

		List<Task> tutorials = tutorialRepository.findTasksByTagsId(tagId);
		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

	@PostMapping("/tasks/{taskId}/tags")
	public ResponseEntity<Tag> addTag(@PathVariable(value = "taskId") Long tutorialId, @RequestBody Tag tagRequest) {
		Tag tag = tutorialRepository.findById(tutorialId).map(tutorial -> {
			long tagId = tagRequest.getId();

			// tag is existed
			if (tagId != 0L) {
				Tag _tag = tagRepository.findById(tagId)
						.orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
				tutorial.addTag(_tag);
				tutorialRepository.save(tutorial);
				return _tag;
			}

			// add and create new Tag
			tutorial.addTag(tagRequest);
			return tagRepository.save(tagRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

		return new ResponseEntity<>(tag, HttpStatus.CREATED);
	}

	@PutMapping("/tags/{id}")
	public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest) {
		Tag tag = tagRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TagId " + id + "not found"));

		tag.setName(tagRequest.getName());

		return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
	}

	@DeleteMapping("/tasks/{taskId}/tags/{tagId}")
	public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "taskId") Long tutorialId,
			@PathVariable(value = "tagId") Long tagId) {
		Task tutorial = tutorialRepository.findById(tutorialId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

		tutorial.removeTag(tagId);
		tutorialRepository.save(tutorial);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/tags/{id}")
	public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
		tagRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
