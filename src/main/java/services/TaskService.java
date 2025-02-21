package services;

import io.vertx.core.json.JsonArray;
import models.Task;
import utils.TaskUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskService {

  Map<String, Task> taskMap = new ConcurrentHashMap<>();

  public JsonArray getAllTasks() {
    JsonArray tasks = new JsonArray();
    for(Task task : taskMap.values()) {
        tasks.add(TaskUtil.toJson(task));
    }
    return tasks;
  }

  public Task getTask(String taskName) {
      return taskMap.get(taskName);
  }

  public void createTask(Task task) {
    taskMap.put(task.taskName(), task);
  }

}
