package utils;

import io.vertx.core.json.JsonObject;
import models.Task;

import java.util.UUID;


public class TaskUtil {

  public static JsonObject toJson(Task task) {
    return new JsonObject()
      .put("id", UUID.randomUUID())
      .put(VertxConstants.TASK_NAME, task.taskName())
      .put(VertxConstants.COMPLETED, task.completed()
      );
  }
}
