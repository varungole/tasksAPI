package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import models.Task;
import services.TaskService;
import utils.TaskUtil;
import utils.VertxConstants;

import java.util.UUID;

public class TaskVerticle extends AbstractVerticle {
  TaskService taskService = new TaskService();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    getAllTaskHandler();
    getSingleTaskHandler();
    createTaskHandler();

    startPromise.complete();
  }

  private void getAllTaskHandler() {
    vertx.eventBus().consumer(VertxConstants.GET_ALL_TASKS, message -> {
      JsonArray allTasks = taskService.getAllTasks();
      message.reply(allTasks.encode());
    });
  }

  private void getSingleTaskHandler() {
    vertx.eventBus().consumer(VertxConstants.GET_SINGLE_TASK, message -> {
      String taskName = (String)message.body();
      Task task = taskService.getTask(taskName);
      message.reply(task != null ? TaskUtil.toJson(task).encode() : VertxConstants.TASK_NOT_FOUND);
    });
  }

  private void createTaskHandler() {
    vertx.eventBus().consumer(VertxConstants.CREATE_TASK, message -> {
      JsonObject taskJson = (JsonObject) message.body();
      Task task = new Task(UUID.randomUUID(), taskJson.getString(VertxConstants.TASK_NAME), taskJson.getBoolean(VertxConstants.COMPLETED));
      taskService.createTask(task);
      message.reply(TaskUtil.toJson(task).encode());
    });
  }

}
