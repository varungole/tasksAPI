package config;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import utils.VertxConstants;

public class ConfigLoader {
  private final Vertx vertx;
  private final JsonObject loadedConfig;

  public ConfigLoader(Vertx vertx) {
    this.vertx = vertx;
    loadedConfig = new JsonObject();
  }

  public Future<JsonObject> loadConfig() {
    return vertx.fileSystem().readFile(VertxConstants.CONFIG_JSON)
      .map(Buffer::toJsonObject)
      .onSuccess(loadedConfig::mergeIn);
  }
}
