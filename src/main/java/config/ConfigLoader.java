package config;

import utils.VertxConstants;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class ConfigLoader {

  private final Vertx vertx;

  public ConfigLoader(Vertx vertx) {
    this.vertx = vertx;
  }

  public Future<JsonObject> loadConfig() {
    Promise<JsonObject> promise = Promise.promise();
    ConfigStoreOptions httpStore = new ConfigStoreOptions()
      .setType(VertxConstants.FILE)
      .setType(VertxConstants.JSON)
      .setConfig(new JsonObject().put(VertxConstants.PATH, VertxConstants.CONFIG_JSON));

    ConfigRetrieverOptions options = new ConfigRetrieverOptions()
      .addStore(httpStore);

    ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

    retriever.getConfig(result -> {
      if(result.succeeded()) {
        promise.complete(result.result());
      } else {
        promise.fail(result.cause());
      }
    });

    return promise.future();
  }
}
