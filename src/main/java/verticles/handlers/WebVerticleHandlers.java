package verticles.handlers;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import utils.VertxConstants;

public class WebVerticleHandlers {

  private final Vertx vertx;
  private final JsonObject loadedConfig;

  public WebVerticleHandlers(Vertx vertx, JsonObject loadedConfig) {
    this.vertx = vertx;
    this.loadedConfig = loadedConfig;
  }

  public Future<HttpServer> startHttpServer(Router router) {
    Promise<HttpServer> promise = Promise.promise();
    JsonObject http = loadedConfig.getJsonObject(VertxConstants.HTTP);
    int httpPort = http != null ? http.getInteger(VertxConstants.PORT) : VertxConstants.DEFAULT_PORT;
    HttpServer server = vertx.createHttpServer().requestHandler(router);

    server.listen(httpPort, result -> {
      if(result.succeeded()) {
        promise.complete(result.result());
      } else {
        promise.fail(result.cause());
      }
    });
    return promise.future();
  }
}
