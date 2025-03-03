package utils;

import io.vertx.core.json.JsonObject;

import java.lang.reflect.RecordComponent;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static utils.VertxConstants.*;

public class Util {

  public static class RateUsage {
    public AtomicInteger requests;
    public Long validTime;

    public RateUsage() {
      requests = new AtomicInteger(ONE);
      validTime = System.currentTimeMillis() + RATE_LIMIT_WINDOW_MS;
    }
  }

  public static <T extends Record> JsonObject toJson(T record) {
    JsonObject jsonObject = new JsonObject().put(ID, UUID.randomUUID());
    for(RecordComponent component : record.getClass().getRecordComponents()) {
      try {
        jsonObject.put(component.getName(),component.getAccessor().invoke(record));
      } catch (Exception e) {
        throw new RuntimeException("Failed to serialize JSON Object");
      }
    }

    return jsonObject;
  }
}
