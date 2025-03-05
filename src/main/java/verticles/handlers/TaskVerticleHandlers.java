package verticles.handlers;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import models.Task;
import services.TaskService;
import utils.Util.*;

import static utils.Util.toJson;
import static utils.Util.toJsonArray;
import static utils.VertxConstants.*;

import java.util.*;
import java.util.function.Consumer;

public class TaskVerticleHandlers {

  private final Vertx vertx;
  private final TaskService taskService;
  public final HashMap<String, Consumer<Message<Object>>> hmap;

  public TaskVerticleHandlers(Vertx vertx, TaskService taskService) {
    this.vertx = vertx;
    this.taskService = taskService;
    this.hmap = new HashMap<>();
  }

  public Future<Void> startHandlers() {
    Promise<Void> promise = Promise.promise();
    startHandler();
    return promise.future();
  }

  public void startHandler() {
    hmap.put(GET_ALL_TASKS,this::handleAllTask);
    hmap.put(GET_SINGLE_TASK, this::handleSingleTask);
    hmap.put(CREATE_TASK, this::handleCreateTask);
    hmap.put(SORT_TASKS, this::sortTasks);
    hmap.forEach((event,handler) -> vertx.eventBus().consumer(event, handler::accept));
  }

  public void sortTasks(Message<Object> message) {
    JsonArray allTasks = taskService.getAllTasks();
    message.reply(allTasks.encode());
  }

  public void handleAllTask(Message<Object> message) {
    JsonArray allTasks = taskService.getAllTasks();
    message.reply(allTasks.encode());
  }

  public void handleSingleTask(Message<Object> message) {
    String taskName = (String)message.body();
    Task task = taskService.getTask(taskName);
    message.reply(task != null ? toJson(task).encode() : TASK_NOT_FOUND);
  }

  public void handleCreateTask(Message<Object> message) {
    JsonArray taskJson = (JsonArray) message.body();

    List<Task> tasks = new ArrayList<>();
    for(Object obj : taskJson) {
      if(obj instanceof JsonObject jsonObject) {
        Task task = new Task(UUID.randomUUID(),
          jsonObject.getString(TASK_NAME),
          jsonObject.getBoolean(COMPLETED),
          Priority.valueOf(jsonObject.getString(PRIORITY).toUpperCase()));
        tasks.add(task);
      } else {
        message.fail(400, "Invalid JSON format");
        return;
      }
    }
    taskService.createTask(tasks);
    message.reply(toJsonArray(tasks).encode());

  }
}
