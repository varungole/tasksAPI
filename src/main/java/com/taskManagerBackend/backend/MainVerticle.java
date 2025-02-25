package com.taskManagerBackend.backend;

import router.ResponseHandler;
import router.RouterHandler;
import utils.VertxConstants;
import config.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import router.RouterFactory;


public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    ConfigLoader configLoader = new ConfigLoader(vertx);
    ResponseHandler responseHandler = new ResponseHandler();
    RouterHandler routerHandler = new RouterHandler(vertx, responseHandler);
    RouterFactory routerFactory = new RouterFactory(vertx, routerHandler);

    Future<JsonObject> configFuture = configLoader.loadConfig();
    Future<Router> routerFuture = routerFactory.setupRouter();

    Future.all(configFuture, routerFuture).compose(result -> {
      JsonObject config = result.resultAt(VertxConstants.ZERO);
      Router router = result.resultAt(VertxConstants.ONE);
      return VerticleDeployer.deployVerticle(vertx, router, config);
    }).onSuccess(v -> startPromise.complete())
      .onFailure(startPromise::fail);
  }

}

