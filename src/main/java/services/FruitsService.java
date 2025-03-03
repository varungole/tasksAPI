package services;

import io.vertx.core.json.JsonArray;
import models.Fruit;
import utils.Util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FruitsService {

  Map<String, Fruit> fruitsMap = new ConcurrentHashMap<>();

  public JsonArray getAllFruits() {
    JsonArray fruits = new JsonArray();
    for(Fruit fruit : fruitsMap.values()) {
      fruits.add(Util.toJson(fruit));
    }
    return fruits;
  }

  public Fruit getSingleFruit(String fruitName) {
    return fruitsMap.get(fruitName);
  }

  public void createFruit(Fruit fruit) {
    fruitsMap.put(fruit.name(), fruit);
  }


}
