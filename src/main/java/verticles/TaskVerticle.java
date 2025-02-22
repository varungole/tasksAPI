package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import services.TaskService;
import verticles.handlers.TaskVerticleHandlers;

public class TaskVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    TaskService taskService = new TaskService();

    TaskVerticleHandlers taskVerticleHandlers = new TaskVerticleHandlers(vertx, taskService);
    taskVerticleHandlers.startHandlers()
      .onSuccess(server -> startPromise.complete())
      .onFailure(startPromise::fail);
  }
}
