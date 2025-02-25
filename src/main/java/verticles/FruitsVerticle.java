package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import services.FruitsService;
import verticles.handlers.FruitsVerticleHandlers;

public class FruitsVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {

    FruitsService fruitsService = new FruitsService();
    FruitsVerticleHandlers fruitsVerticleHandlers = new FruitsVerticleHandlers(vertx, fruitsService);
    fruitsVerticleHandlers.startHandlers()
      .onSuccess(server -> startPromise.complete())
      .onFailure(startPromise::fail);
  }
}
