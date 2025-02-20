package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class WebVerticle extends AbstractVerticle {

  private final JsonObject loadedConfig;
  private final Router router;

  public WebVerticle(JsonObject loadedConfig, Router router) {
    this.loadedConfig = loadedConfig;
    this.router = router;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startHttpServer(router)
      .onSuccess(server -> startPromise.complete())
      .onFailure(startPromise::fail);
  }

  Future<HttpServer> startHttpServer(Router router) {
    Promise<HttpServer> promise = Promise.promise();
    JsonObject http = loadedConfig.getJsonObject("http");
    int httpPort = http != null ? http.getInteger("port") : 8080;
    HttpServer server = vertx.createHttpServer().requestHandler(router);

    server.listen(httpPort, result -> {
      if(result.succeeded()) {
        System.out.println("Server started at port " + httpPort);
        promise.complete(result.result());
      } else {
        System.out.println("Failed to start the server");
        promise.fail(result.cause());
      }
    });
    return promise.future();
  }
}
