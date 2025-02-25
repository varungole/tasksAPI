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

    router.get("/tasks").handler(routerHandler::getAllTasks);
    router.post("/fruits").handler(routerHandler::createFruits);
    router.get("/fruits").handler(routerHandler::getFruits);
    router.get("/:taskName").handler(routerHandler::getSingleTask);
    router.post("/tasks").handler(routerHandler::createTask);
    System.out.println("Routes registered:");
    router.getRoutes().forEach(route -> System.out.println(route.getPath()));


    return Future.succeededFuture(router);
  }
}
