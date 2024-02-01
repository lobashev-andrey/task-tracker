package com.example.tasktracker.mapper;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {


    Task taskModelToTask(TaskModel taskModel);



    default Task taskModelToTask(String id, TaskModel taskModel) {
        Task task = taskModelToTask(taskModel);
        task.setId(id);
        return task;
    }

    TaskModel taskToTaskModel(Task task);
}
