package router.RouterLogic;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import router.RouterInterface.IRouterFactory;

public class RouterFactory implements IRouterFactory {
  private final Vertx vertx;
  private final RouterHandler routerHandler;

  public RouterFactory(Vertx vertx, RouterHandler routerHandler) {
    this.vertx = vertx;
    this.routerHandler = routerHandler;
  }

  @Override
  public Future<Router> setupRouter() {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/tasks").handler(routerHandler::getAllTasks);
    router.post("/fruits").handler(routerHandler::createFruits);
    router.get("/fruits").handler(routerHandler::getFruits);
    router.get("/:taskName").handler(routerHandler::getSingleTask);
    router.post("/tasks").handler(routerHandler::createTask);

    return Future.succeededFuture(router);
  }
}
