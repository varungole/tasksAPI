package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import verticles.handlers.WebVerticleHandlers;

public class WebVerticle extends AbstractVerticle {

  private final Router router;
  private final JsonObject loadedConfig;

  public WebVerticle(JsonObject loadedConfig, Router router) {
    this.router = router;
    this.loadedConfig = loadedConfig;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    WebVerticleHandlers webVerticleHandlers = new WebVerticleHandlers(vertx, loadedConfig);

    webVerticleHandlers.startHttpServer(router)
      .onSuccess(server -> startPromise.complete())
      .onFailure(startPromise::fail);
  }
}
