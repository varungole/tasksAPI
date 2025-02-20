package com.taskManagerBackend.backend;

import Utils.VertxConstants;
import config.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import router.RouterFactory;
import verticles.WebVerticle;

public class MainVerticle extends AbstractVerticle {

  final JsonObject loadedConfig = new JsonObject();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    ConfigLoader configLoader = new ConfigLoader(vertx);
    RouterFactory routerFactory = new RouterFactory(vertx);

    Future<JsonObject> configFuture = configLoader.loadConfig().compose(this::storeConfig);
    Future<Router> routerFuture = routerFactory.setupRouter();

    Future.all(configFuture, routerFuture).compose(result -> {
      Router router = result.resultAt(VertxConstants.INDEX_ONE);
      return deployVerticles(router);
    }).onSuccess(v -> startPromise.complete())
      .onFailure(startPromise::fail);
  }

  public Future<JsonObject> storeConfig(JsonObject config) {
    loadedConfig.mergeIn(config);
    return Future.succeededFuture(config);
  }

  public Future<Void> deployVerticles(Router router) {
    Promise<Void> promise = Promise.promise();

    vertx.deployVerticle(new WebVerticle(loadedConfig, router))
      .onSuccess(id -> promise.complete())
      .onFailure(promise::fail);

    return promise.future();
  }

}

