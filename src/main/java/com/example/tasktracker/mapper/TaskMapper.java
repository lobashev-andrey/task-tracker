package com.example.tasktracker.mapper;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.model.TaskModel;
import com.example.tasktracker.model.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

     public Task taskModelToTask(TaskModel taskModel) {


        return new Task(
                taskModel.getId(),
                taskModel.getName(),
                taskModel.getDescription(),
                taskModel.getAuthorId(),
                taskModel.getAssigneeId(),
                null,
                null,
                taskModel.getStatus(),
                taskModel.getObserverIds()
                );
    }

     public Task taskModelToTask(String id, TaskModel taskModel) {
        Task task = taskModelToTask(taskModel);
        task.setId(id);
        return task;
    }

    public TaskModel taskToTaskModel(Task task){
        return new TaskModel(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getAuthorId(),
                task.getAssigneeId(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getStatus(),
                task.getObserverIds()
        );
    }
     public TaskResponse taskToTaskResponse(Task task){
        return new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getStatus(),
                task.getObserverIds()
        );
    }
}
