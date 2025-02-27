package router.RouterLogic;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
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
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
    router.route().handler(CorsHandler.create("localhost"));

    router.route("/api/*").handler(this::checkHeader);
    router.get("/api/tasks").handler(routerHandler::getAllTasks);
    router.post("/api/fruits").handler(routerHandler::createFruits);
    router.get("/api/fruits").handler(routerHandler::getFruits);
    router.get("/api/:taskName").handler(routerHandler::getSingleTask);
    router.post("/api/tasks").handler(routerHandler::createTask);

    return Future.succeededFuture(router);
  }

  private void checkHeader(RoutingContext ctx) {
   String header = ctx.request().getHeader("X-AUTHENTICATION");
   if(header == null || !header.equalsIgnoreCase("Varun")) {
      ctx.response().setStatusCode(403).putHeader("content-type", "application-json; charset=utf-8").end("{\"message\":\"Not authorized\"}");
   } else {
      ctx.next();
   }
  }

}
