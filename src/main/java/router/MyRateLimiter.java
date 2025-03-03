package router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import utils.Util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static utils.VertxConstants.*;


public class MyRateLimiter {

  Map<String, Util.RateUsage> timeMap = new ConcurrentHashMap<>();

  private final Vertx vertx;

  public MyRateLimiter(Vertx vertx) {
    this.vertx = vertx;
    startCleanUpTask();
  }

  private void startCleanUpTask() {
    vertx.setPeriodic(TimeUnit.MINUTES.toMillis(FIVE), id -> {
      long now = System.currentTimeMillis();
      timeMap.entrySet().removeIf(entry -> entry.getValue().validTime < now);
    });

  }

  public void handle(RoutingContext ctx) {
    String userName = ctx.request().getHeader(USER_NAME);

    Util.RateUsage rateUsage = timeMap.computeIfAbsent(userName, key -> new Util.RateUsage());

    if (rateUsage.validTime <= System.currentTimeMillis()) {
      rateUsage = new Util.RateUsage();
      timeMap.put(userName, rateUsage);
    }

    if (rateUsage.requests.incrementAndGet() > RATE_LIMIT) {
      ctx.response().setStatusCode(RATE_ERROR).end(TOO_MANY_REQUESTS);
      return;
    }

    ctx.next();
  }
}


