package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Task;
import com.example.repository.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public List<Task> findAllTasks() {
		return taskRepository.findAll();
	}

	public Task findTaskById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task by ID = " + id + " not Found!"));
	}

}
