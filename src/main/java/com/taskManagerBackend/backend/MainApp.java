package com.taskManagerBackend.backend;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class MainApp {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(new VertxOptions().setBlockedThreadCheckInterval(1000));

    vertx.deployVerticle(MainVerticle.class.getName(), stringAsyncResult -> {
      if(stringAsyncResult.succeeded()) {
        System.out.println("Succesfully deployed all verticles");
      } else {
        System.out.println("Failed to deploy verticles " + stringAsyncResult.cause());
      }
    });
  }
}
