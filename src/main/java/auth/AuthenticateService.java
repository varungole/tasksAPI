package auth;

import io.vertx.ext.web.RoutingContext;
import static utils.VertxConstants.*;


public class AuthenticateService {

  public boolean handleAuthentication(RoutingContext ctx) {
    String header = ctx.request().getHeader(X_AUTHENTICATE);
    if(header == null || !header.equalsIgnoreCase(X_AUTHENTICATE_KEY)) {
      ctx.request().response().setStatusCode(403).end(UNAUTHORIZED_REQUEST);
      return false;
    }
    return true;
  }

  public boolean handleUsername(RoutingContext ctx) {
    String userName = ctx.request().getHeader(USER_NAME);
    if(userName == null) {
      ctx.request().response().setStatusCode(403).end(MISSING_USERNAME);
      return false;
    }
    return true;
  }

  public void handleAuth(RoutingContext ctx) {
     if(!handleAuthentication(ctx) || !handleUsername(ctx)) {
       return;
     }
     ctx.next();
  }
}
