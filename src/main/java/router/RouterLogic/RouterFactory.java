package router.RouterLogic;

import auth.AuthenticateService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import router.MyRateLimiter;
import router.RouterInterface.IRouterFactory;


public class RouterFactory implements IRouterFactory {
  private final Vertx vertx;
  private final RouterHandler routerHandler;
  private final AuthenticateService authenticateService;
  private final MyRateLimiter myRateLimiter;

  public RouterFactory(Vertx vertx, RouterHandler routerHandler, AuthenticateService authenticateService, MyRateLimiter myRateLimiter) {
    this.vertx = vertx;
    this.routerHandler = routerHandler;
    this.authenticateService = authenticateService;
    this.myRateLimiter = myRateLimiter;
  }

  @Override
  public Future<Router> setupRouter() {
    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
    router.route().handler(CorsHandler.create().allowedHeader("localhost"));
    router.get("/api/*").handler(authenticateService::handleAuth);
    router.route().handler(myRateLimiter::handle);

    router.get("/api/tasks/*").handler(routerHandler::sortTasks);
    router.get("/api/tasks").handler(routerHandler::getAllTasks);
    router.post("/api/tasks").handler(routerHandler::createTask);

    router.post("/api/fruits").handler(routerHandler::createFruits);
    router.get("/api/fruits").handler(routerHandler::getFruits);
    router.get("/api/:taskName").handler(routerHandler::getSingleTask);

    return Future.succeededFuture(router);
  }

}
