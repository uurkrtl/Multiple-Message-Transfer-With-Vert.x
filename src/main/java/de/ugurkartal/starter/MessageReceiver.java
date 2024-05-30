package de.ugurkartal.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.bson.json.JsonObject;

public class MessageReceiver extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.eventBus().<JsonObject>consumer(MongoFetchVerticle.MONGODB_ADDRESS, message -> System.out.println("Received data from DB: " + message.body()));
    vertx.eventBus().<JsonObject>consumer(ApiFetchVerticle.API_ADDRESS, message -> System.out.println("Received data from API: " + message.body()));
    vertx.eventBus().<JsonObject>consumer(ConstantFetchVerticle.CONSTANT_ADDRESS, message -> System.out.println("Received data from Constant: " + message.body()));
  }
}
