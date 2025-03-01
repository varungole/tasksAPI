package router;

import io.vertx.ext.web.RoutingContext;
import static utils.VertxConstants.*;

import java.util.HashMap;

public class MyRateLimiter {

  HashMap<String, Integer> hmap = new HashMap<>();

  public void handle(RoutingContext ctx) {
    String userName = ctx.request().getHeader(USER_NAME);
    int requestCount = hmap.merge(userName,ONE, Integer::sum);
    if(requestCount > RATE_LIMIT) {
      ctx.response().setStatusCode(UNAUTHORIZED).end(TOO_MANY_REQUESTS);
      return;
    }
    ctx.next();
  }
}
