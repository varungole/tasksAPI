package router.RouterInterface;

import io.vertx.core.Future;
import io.vertx.ext.web.Router;

public interface IRouterFactory {
  Future<Router> setupRouter();
}
