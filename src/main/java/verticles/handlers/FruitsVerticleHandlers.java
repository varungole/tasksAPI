package verticles.handlers;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import models.Fruit;
import services.FruitsService;
import utils.Util;
import static utils.VertxConstants.*;


import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class FruitsVerticleHandlers {

  private final Vertx vertx;
  private final FruitsService fruitsService;
  public final HashMap<String, Consumer<Message<Object>>> hmap;

  public FruitsVerticleHandlers(Vertx vertx, FruitsService fruitsService) {
    this.vertx = vertx;
    this.fruitsService = fruitsService;
    this.hmap = new HashMap<>();
  }

  public Future<Void> startHandlers() {
    Promise<Void> promise = Promise.promise();
    startHandler();
    return promise.future();
  }

  public void startHandler() {
    hmap.put(CREATE_FRUITS, this::handleCreateFruits);
    hmap.put(GET_ALL_FRUITS,this::handleGetAllFruits);
    hmap.forEach((event, handler) -> vertx.eventBus().consumer(event, handler::accept));
  }

  public void handleCreateFruits(Message<Object> message) {
    JsonObject fruitsJson = (JsonObject) message.body();
    Fruit fruit = new Fruit(UUID.randomUUID(), fruitsJson.getString(FRUIT_NAME), fruitsJson.getBoolean(IS_SWEET));
    fruitsService.createFruit(fruit);
    message.reply(Util.toJson(fruit).encode());
  }

  public void handleGetAllFruits(Message<Object> message) {
    JsonArray allTasks = fruitsService.getAllFruits();
    message.reply(allTasks.encode());
  }
}
