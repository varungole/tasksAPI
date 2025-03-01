package router;

import io.vertx.ext.web.RoutingContext;

public class MyRateLimiter {


  public void handle(RoutingContext ctx) {
    System.out.println(ctx.request().remoteAddress());
    ctx.next();
  }
}
