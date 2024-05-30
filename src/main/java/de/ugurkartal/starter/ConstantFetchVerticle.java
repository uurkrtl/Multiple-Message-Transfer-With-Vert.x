package de.ugurkartal.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

public class ConstantFetchVerticle extends AbstractVerticle {
  public static final String CONSTANT_ADDRESS = "constant.address";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    JsonObject data = new JsonObject()
      .put("id", UUID.randomUUID().toString())
      .put("firstName", "Ugur")
      .put("lastName", "Kartal");

    vertx.setPeriodic(3000, id -> vertx.eventBus().publish(CONSTANT_ADDRESS, data));
    startPromise.complete();
  }
}
