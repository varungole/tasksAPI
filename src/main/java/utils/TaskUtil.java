package utils;

import io.vertx.core.json.JsonObject;

import java.lang.reflect.RecordComponent;
import java.util.UUID;
import static utils.VertxConstants.*;

public class TaskUtil {

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
