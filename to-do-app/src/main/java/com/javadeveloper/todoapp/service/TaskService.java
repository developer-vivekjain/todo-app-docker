package com.javadeveloper.todoapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.javadeveloper.todoapp.dto.Task;

@Service
public class TaskService {
	
	private static List<Task> tasks = new ArrayList<>();
	
	private static Long tasksCount = 0L;
	
	public List<Task> findAll() {
		return tasks;
	}
	
	public Task save(Task task) {
		task.setTaskId(++tasksCount);
		tasks.add(task);
		return task;
	}

	public Task findOne(Long id) {
		Predicate<? super Task> predicate = task -> task.getTaskId().equals(id); 
		return tasks.stream().filter(predicate).findFirst().orElse(null);
	}

	public void deleteById(Long id) {
		Predicate<? super Task> predicate = task -> task.getTaskId().equals(id);
		tasks.removeIf(predicate);
	}}
