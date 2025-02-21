package verticles.handlers;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import models.Task;
import services.TaskService;
import utils.TaskUtil;
import utils.VertxConstants;

import java.util.UUID;

public class TaskVerticleHandlers {

  private final Vertx vertx;
  private final TaskService taskService;

  public TaskVerticleHandlers(Vertx vertx, TaskService taskService) {
    this.vertx = vertx;
    this.taskService = taskService;
  }

  public Future<Void> startHandlers() {
    Promise<Void> promise = Promise.promise();
    getAllTaskHandler();
    getSingleTaskHandler();
    createTaskHandler();
    return promise.future();
  }

  public void getAllTaskHandler() {
    vertx.eventBus().consumer(VertxConstants.GET_ALL_TASKS, message -> {
      JsonArray allTasks = taskService.getAllTasks();
      message.reply(allTasks.encode());
    });
  }

  public void getSingleTaskHandler() {
    vertx.eventBus().consumer(VertxConstants.GET_SINGLE_TASK, message -> {
      String taskName = (String)message.body();
      Task task = taskService.getTask(taskName);
      message.reply(task != null ? TaskUtil.toJson(task).encode() : VertxConstants.TASK_NOT_FOUND);
    });
  }

  public void createTaskHandler() {
    vertx.eventBus().consumer(VertxConstants.CREATE_TASK, message -> {
      JsonObject taskJson = (JsonObject) message.body();
      Task task = new Task(UUID.randomUUID(), taskJson.getString(VertxConstants.TASK_NAME), taskJson.getBoolean(VertxConstants.COMPLETED));
      taskService.createTask(task);
      message.reply(TaskUtil.toJson(task).encode());
    });
  }

}
