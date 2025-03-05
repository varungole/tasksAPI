package router.RouterLogic;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.JsonEventType;
import io.vertx.core.parsetools.JsonParser;
import io.vertx.ext.web.RoutingContext;
import router.RouterInterface.IRouterHandler;
import router.RouterUtility.ResponseHandler;

import java.util.concurrent.atomic.AtomicReference;

import static java.beans.Beans.isInstanceOf;
import static utils.VertxConstants.*;


public class RouterHandler implements IRouterHandler {

  private final Vertx vertx;
  private final ResponseHandler responseHandler;

  public RouterHandler(Vertx vertx, ResponseHandler responseHandler) {
    this.vertx = vertx;
    this.responseHandler = responseHandler;
  }

  public void sort(RoutingContext ctx) {
    sendRequest(ctx, SORT_TASKS, "");
  }

  @Override
  public void sortTasks(RoutingContext ctx) {
    System.out.println("Inside Sort Tasks");
    MultiMap params = ctx.request().params();
    String sorting = params.get("sorting");
    sort(ctx);
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

}
