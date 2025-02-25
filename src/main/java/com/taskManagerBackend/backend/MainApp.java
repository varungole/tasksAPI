package com.taskManagerBackend.backend;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class MainApp {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    int instances = Runtime.getRuntime().availableProcessors();

    vertx.deployVerticle(MainVerticle.class.getName(), stringAsyncResult -> {
      if(stringAsyncResult.succeeded()) {
        System.out.println("Succesfully deployed all verticles");
      } else {
        System.out.println("Failed to deploy verticles " + stringAsyncResult.cause());
      }
    });
  }
}
