package com.taskManagerBackend.backend;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class MainApp {

  public static void main(String[] args) {
      Vertx vertx = Vertx.vertx();
      vertx.deployVerticle(new MainVerticle(), stringAsyncResult -> {
        if(stringAsyncResult.succeeded()) {
          System.out.println("Started vertx successfully!");
        } else {
          System.err.println("Failed to start vertx");
        }
      });

  }
}
