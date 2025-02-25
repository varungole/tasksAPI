package router;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import utils.VertxConstants;

public class RouterHandler {

  private final Vertx vertx;
  private final ResponseHandler responseHandler;

  public RouterHandler(Vertx vertx) {
    this.vertx = vertx;
    this.responseHandler = new ResponseHandler();
  }

  public void getAllTasks(RoutingContext ctx) {
    sendRequest(ctx, VertxConstants.GET_ALL_TASKS, "");
  }

  public void getSingleTask(RoutingContext ctx) {
    String taskName = ctx.pathParam(VertxConstants.TASK_NAME);
    sendRequest(ctx, VertxConstants.GET_SINGLE_TASK, taskName);
  }

  public void createTask(RoutingContext ctx) {
    JsonObject taskJson = ctx.body().asJsonObject();
    if(checkEmptyBody(ctx, taskJson)) {
      return;
    }
    sendRequest(ctx, VertxConstants.CREATE_TASK, taskJson);
  }

  public void createFruits(RoutingContext ctx) {
    JsonObject fruitsJson = ctx.body().asJsonObject();
    if(checkEmptyBody(ctx, fruitsJson)) {
      return;
    }
    sendRequest(ctx, VertxConstants.CREATE_FRUITS, fruitsJson);
  }

  public void getFruits(RoutingContext ctx) {
    sendRequest(ctx, VertxConstants.GET_ALL_FRUITS, "");
  }

  private <T> void sendRequest(RoutingContext ctx, String action, T message) {
    vertx.eventBus().request(action,message, messageAsyncResult -> {
      if(messageAsyncResult.succeeded()) {
        responseHandler.success(messageAsyncResult, ctx);
      } else {
        responseHandler.failure(ctx);
      }
    });
  }

  private Boolean checkEmptyBody(RoutingContext ctx, JsonObject body) {
    if(body.isEmpty()) {
      responseHandler.invalid(ctx);
      return true;
    }
    return false;
  }

}
