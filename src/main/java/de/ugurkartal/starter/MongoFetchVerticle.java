package de.ugurkartal.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoFetchVerticle extends AbstractVerticle {
  private MongoClient mongoClient;
  public static final String MONGODB_ADDRESS = "mongo.address";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    JsonObject dbConfig = new JsonObject()
      .put("connection_string", System.getenv("MONGODB_URI"))
      .put("db_name", System.getenv("MONGODB_NAME"));

    mongoClient = MongoClient.createShared(vertx, dbConfig);

    vertx.setPeriodic(5000, id -> fetchDataFromDbAndSend());

    startPromise.complete();
  }

  private void fetchDataFromDbAndSend() {
    mongoClient.find("customer", new JsonObject())
      .onSuccess(res -> res.forEach(doc -> vertx.eventBus().publish(MONGODB_ADDRESS, doc)))
      .onFailure(Throwable::printStackTrace);
  }
}
