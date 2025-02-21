package utils;

import io.vertx.core.json.JsonObject;
import models.Task;

public class TaskUtil {

  public static JsonObject toJson(Task task) {
    return new JsonObject()
      .put("taskName", task.taskName())
      .put("completed", task.completed()
      );
  }
}
