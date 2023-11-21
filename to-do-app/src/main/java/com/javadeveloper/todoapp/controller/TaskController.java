package com.javadeveloper.todoapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javadeveloper.todoapp.dto.Task;
import com.javadeveloper.todoapp.service.TaskService;


@RestController
public class TaskController {

	@Autowired
	TaskService service;

	@GetMapping("/tasks")
	public List<Task> retrieveAllTasks() {
		return service.findAll();
	}

	@GetMapping("/tasks/{id}")
	public EntityModel<Task> retrieveTask(@PathVariable Long id) {
		Task task = service.findOne(id);

		if(task==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found with id :"+ id);

		EntityModel<Task> entityModel = EntityModel.of(task);

		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllTasks());
		entityModel.add(link.withRel("all-tasks"));

		return entityModel;
	}

	@DeleteMapping("/tasks/{id}")
	public void deleteTask(@PathVariable Long id) {
		service.deleteById(id);
	}

	@PostMapping("/tasks")
	public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {

		Task savedTask = service.save(task);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedTask.getTaskId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
}