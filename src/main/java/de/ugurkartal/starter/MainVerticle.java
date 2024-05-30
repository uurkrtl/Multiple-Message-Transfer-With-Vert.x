package de.ugurkartal.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Vertx.clusteredVertx(new VertxOptions())
      .onSuccess(vertx -> {
        vertx.deployVerticle(new MongoFetchVerticle());
        vertx.deployVerticle(new ApiFetchVerticle());
        vertx.deployVerticle(new ConstantFetchVerticle());
        vertx.deployVerticle(new MessageReceiver());
        vertx.deployVerticle(new HttpServerVerticle());
        System.out.println("Deployed success: " + AbstractVerticle.class.getName());
      })
      .onFailure(failure -> System.out.println("Deploy failure: " + failure));
  }
}
