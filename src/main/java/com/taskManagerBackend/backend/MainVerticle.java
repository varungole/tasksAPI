package com.taskManagerBackend.backend;

import auth.AuthenticateService;
import router.MyRateLimiter;
import router.RouterUtility.ResponseHandler;
import router.RouterLogic.RouterHandler;
import config.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import router.RouterLogic.RouterFactory;
import static utils.VertxConstants.*;


public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    ConfigLoader configLoader = new ConfigLoader(vertx);
    ResponseHandler responseHandler = new ResponseHandler();
    AuthenticateService authenticateService = new AuthenticateService();
    MyRateLimiter myRateLimiter = new MyRateLimiter(vertx);

    RouterHandler routerHandler = new RouterHandler(vertx, responseHandler);
    RouterFactory routerFactory = new RouterFactory(vertx, routerHandler, authenticateService, myRateLimiter);

    Future<JsonObject> configFuture = configLoader.loadConfig();
    Future<Router> routerFuture = routerFactory.setupRouter();

    Future.all(configFuture, routerFuture).compose(result -> {
      JsonObject config = result.resultAt(ZERO);
      Router router = result.resultAt(ONE);
      return VerticleDeployer.deployVerticle(vertx, router, config);
    }).onSuccess(v -> startPromise.complete())
      .onFailure(startPromise::fail);
  }

}

