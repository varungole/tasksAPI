package com.taskManagerBackend.backend;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import verticles.FruitsVerticle;
import verticles.TaskVerticle;
import verticles.WebVerticle;

import java.util.Arrays;

public class VerticleDeployer {

  public static Future<Void> deployVerticle(Vertx vertx, Router router, JsonObject loadedConfig) {
    Promise<Void> promise = Promise.promise();

    Future<String> webVerticleDeployment = vertx.deployVerticle(new WebVerticle(loadedConfig, router));
    Future<String> taskVerticleDeployment = vertx.deployVerticle(new TaskVerticle());
    Future<String> fruitsVerticleDeployment = vertx.deployVerticle(new FruitsVerticle());

    Future.all(Arrays.asList(webVerticleDeployment, taskVerticleDeployment, fruitsVerticleDeployment))
      .onSuccess(result -> {
        promise.complete();
      })
      .onFailure(promise::fail);

    return promise.future();
  }
}
