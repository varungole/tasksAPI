package router;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import utils.VertxConstants;

public class RouterHandler {

  private final Vertx vertx;

  public RouterHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  public void getAllTasks(RoutingContext ctx) {
    vertx.eventBus().request(VertxConstants.GET_ALL_TASKS, "", messageAsyncResult -> {
      if(messageAsyncResult.succeeded()) {
        ctx.response().end((String) messageAsyncResult.result().body());
      } else {
        ctx.response().setStatusCode(500).end(VertxConstants.INTERNAL_SERVER_ERROR);
      }
    });
  }

  public void getSingleTask(RoutingContext ctx) {
    String taskName = ctx.pathParam(VertxConstants.TASK_NAME);
    vertx.eventBus().request(VertxConstants.GET_SINGLE_TASK, taskName, messageAsyncResult -> {
      if(messageAsyncResult.succeeded()) {
        ctx.response().end((String) messageAsyncResult.result().body());
      } else {
        ctx.response().setStatusCode(500).end(VertxConstants.INTERNAL_SERVER_ERROR);
      }
    });
  }

  public void createTask(RoutingContext ctx) {
    JsonObject taskJson = ctx.body().asJsonObject();
    if(taskJson == null) {
      ctx.response().setStatusCode(400).end(VertxConstants.INVALID_JSON_PAYLOAD);
      return;
    }
    vertx.eventBus().request(VertxConstants.CREATE_TASK, taskJson, messageAsyncResult -> {
      if(messageAsyncResult.succeeded()) {
        ctx.response().setStatusCode(201).putHeader(VertxConstants.CONTENT_TYPE, VertxConstants.APPLICATION_JSON).end((String)messageAsyncResult.result().body());
      } else {
        ctx.response().setStatusCode(500).end(VertxConstants.INTERNAL_SERVER_ERROR);
      }
    });
  }
}
