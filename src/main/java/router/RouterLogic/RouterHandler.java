package router.RouterLogic;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import router.RouterInterface.IRouterHandler;
import router.RouterUtility.ResponseHandler;

import static utils.VertxConstants.*;


public class RouterHandler implements IRouterHandler {

  private final Vertx vertx;
  private final ResponseHandler responseHandler;

  public RouterHandler(Vertx vertx, ResponseHandler responseHandler) {
    this.vertx = vertx;
    this.responseHandler = responseHandler;
  }

  @Override
  public void sortTasks(RoutingContext ctx) {
    sendRequest(ctx, SORT_TASKS, "");
  }

  @Override
  public void getAllTasks(RoutingContext ctx) {
    sendRequest(ctx, GET_ALL_TASKS, "");
  }

  @Override
  public void getSingleTask(RoutingContext ctx) {
    String taskName = ctx.pathParam(TASK_NAME);
    sendRequest(ctx,GET_SINGLE_TASK, taskName);
  }

  @Override
  public void createTask(RoutingContext ctx) {
    if(invalidJsonCheck(ctx.body(), ctx)) {
      return;
    }

    JsonArray taskJson = ctx.body().asJsonArray();
    if(checkEmptyBody(ctx, taskJson)) {
      return;
    }

    sendRequest(ctx,CREATE_TASK, taskJson);
  }

  @Override
  public void createFruits(RoutingContext ctx) {
    JsonArray fruitsJson = ctx.body().asJsonArray();
    if(checkEmptyBody(ctx, fruitsJson)) {
      return;
    }
    sendRequest(ctx, CREATE_FRUITS, fruitsJson);
  }

  @Override
  public void getFruits(RoutingContext ctx) {
    sendRequest(ctx,GET_ALL_FRUITS, "");
  }

  private void sendRequest(RoutingContext ctx, String action, Object message) {
    vertx.eventBus().request(action, message, messageAsyncResult -> {
      if(messageAsyncResult.succeeded()) {
        responseHandler.success(messageAsyncResult, ctx);
      } else {
        responseHandler.failure(ctx);
      }
    });
  }

  private Boolean checkEmptyBody(RoutingContext ctx, JsonArray body) {
    if(body.isEmpty()) {
      responseHandler.invalid(ctx);
      return true;
    }
    return false;
  }

  private boolean invalidJsonCheck(RequestBody body, RoutingContext ctx) {
    try {
      Buffer buffer = body.buffer();
      new JsonObject(buffer);
      responseHandler.invalid(ctx);
      return true;
    } catch (DecodeException e) {
      return false;
    }
  }

}
