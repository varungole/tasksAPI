package router;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RouterFactory {
  private final Vertx vertx;
  private final RouterHandler routerHandler;

  public RouterFactory(Vertx vertx) {
    this.vertx = vertx;
    this.routerHandler = new RouterHandler(vertx);
  }

  public Future<Router> setupRouter() {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/").handler(routerHandler::getAllTasks);
    router.get("/:taskName").handler(routerHandler::getSingleTask);
    router.post("/tasks").handler(routerHandler::createTask);
    router.post("/fruits").handler(routerHandler::createFruits);

    return Future.succeededFuture(router);
  }
}
