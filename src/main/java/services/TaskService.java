package services;

import io.vertx.core.json.JsonArray;
import models.Task;
import utils.Util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskService {

  Map<String, Task> taskMap = new ConcurrentHashMap<>();

  public JsonArray getAllTasks() {
    JsonArray tasks = new JsonArray();
    for(Task task : taskMap.values()) {
        tasks.add(Util.toJson(task));
    }
    return tasks;
  }

  public Task getTask(String taskName) {
      return taskMap.get(taskName);
  }

  public void createTask(List<Task> tasks) {
    for(Task task : tasks) {
      taskMap.put(task.taskName(), task);
    }
  }

}
