package router.RouterUtility;

import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.RoutingContext;
import static utils.VertxConstants.*;

public class ResponseHandler {

  public void success(AsyncResult<Message<Object>> messageAsyncResult, RoutingContext ctx) {
    ctx.response().setStatusCode(SUCCESSFUL).putHeader(CONTENT_TYPE, APPLICATION_JSON).end((String)messageAsyncResult.result().body());
  }

  public void failure(RoutingContext ctx) {
    ctx.response().setStatusCode(SERVER_ERROR).end(INTERNAL_SERVER_ERROR);
  }

  public void invalid(RoutingContext ctx) {
    ctx.response().setStatusCode(CLIENT_ERROR).end(INVALID_JSON_PAYLOAD);
  }

}
