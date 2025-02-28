package auth;

import io.vertx.ext.web.RoutingContext;
import utils.VertxConstants;

public class AuthenticateService {

  public void handleAuth(RoutingContext ctx) {
     String header = ctx.request().getHeader(VertxConstants.X_AUTHENTICATE);
     if(header == null || !header.equalsIgnoreCase(VertxConstants.X_AUTHENTICATE_KEY)) {
       ctx.request().response().setStatusCode(403).end(VertxConstants.UNAUTHORIZED_REQUEST);
       return;
     }
     ctx.next();
  }
}
