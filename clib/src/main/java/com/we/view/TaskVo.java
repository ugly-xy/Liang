package com.we.view;

import com.we.models.userTask.Task;

public class TaskVo {
	
	private Task task;
	private int status = 0;
	private Long id;
	
	public TaskVo() {
		
	}
	
	public TaskVo(Task task) {
		this.task = task;
	}
	
	public TaskVo(Task task,int status,Long id) {
		this.task = task;
		this.status = status;
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
