package router.RouterInterface;

import io.vertx.ext.web.RoutingContext;

public interface IRouterHandler {
  void sortTasks(RoutingContext context);

  void getAllTasks(RoutingContext ctx);
  void getSingleTask(RoutingContext ctx);
  void createTask(RoutingContext ctx);
  void createFruits(RoutingContext ctx);
  void getFruits(RoutingContext ctx);
}
