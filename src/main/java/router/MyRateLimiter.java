package router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;


public class MyRateLimiter {

  private final Vertx vertx;

  public MyRateLimiter(Vertx vertx) {
    this.vertx = vertx;
  }

  public void handle(RoutingContext ctx) {

    ctx.next();
  }
}


