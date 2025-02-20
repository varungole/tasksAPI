package router;

import Utils.VertxConstants;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class RouterFactory {
  private final Vertx vertx;

  public RouterFactory(Vertx vertx) {
    this.vertx = vertx;
  }

  public Future<Router> setupRouter() {
    Router router = Router.router(vertx);

    router.get("/").handler(ctx -> {
      ctx.response().end(VertxConstants.OK_RESPONSE);
    });

    return Future.succeededFuture(router);
  }
}
