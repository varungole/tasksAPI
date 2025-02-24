package router;

import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.RoutingContext;
import utils.VertxConstants;

public class ResponseHandler {

  public void success(AsyncResult<Message<Object>> messageAsyncResult, RoutingContext ctx) {
    ctx.response().setStatusCode(VertxConstants.SUCCESSFUL).putHeader(VertxConstants.CONTENT_TYPE, VertxConstants.APPLICATION_JSON).end((String)messageAsyncResult.result().body());
  }

  public void failure(RoutingContext ctx) {
    ctx.response().setStatusCode(VertxConstants.SERVER_ERROR).end(VertxConstants.INTERNAL_SERVER_ERROR);
  }

  public void invalid(RoutingContext ctx) {
    ctx.response().setStatusCode(VertxConstants.CLIENT_ERROR).end(VertxConstants.INVALID_JSON_PAYLOAD);
  }

}
