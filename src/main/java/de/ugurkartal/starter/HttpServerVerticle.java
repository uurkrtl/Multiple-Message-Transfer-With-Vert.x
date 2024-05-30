package de.ugurkartal.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HttpServerVerticle extends AbstractVerticle {
  private JsonObject dataFromDb = new JsonObject();
  private JsonObject dataFromApi = new JsonObject();
  private JsonObject dataFromConstant = new JsonObject();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);

    router.get("/from-db").handler(this::getDataFromDb);
    router.get("/from-api").handler(this::getDataFromApi);
    router.get("/from-constant").handler(this::getDataFromConstant);

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .onSuccess(httpServer -> {
        System.out.println("Http server started on port 8080");
        startPromise.complete();
      })
      .onFailure(Throwable::printStackTrace);
  }

  private void getDataFromConstant(RoutingContext context) {
    vertx.eventBus().<JsonObject>consumer(ConstantFetchVerticle.CONSTANT_ADDRESS, message -> dataFromConstant = message.body());
    context.response()
      .putHeader("Content-Type", "application/json")
      .setStatusCode(200)
      .end(dataFromConstant.encodePrettily());
    System.out.println("Received data fron Constant: " + dataFromConstant);
  }

  private void getDataFromApi(RoutingContext context) {
    vertx.eventBus().<JsonObject>consumer(ApiFetchVerticle.API_ADDRESS, message -> dataFromApi = message.body());
    context.response()
      .putHeader("Content-Type", "application/json")
      .setStatusCode(200)
      .end(dataFromApi.encodePrettily());
    System.out.println("Received data from API: " + dataFromApi);
  }

  private void getDataFromDb(RoutingContext context) {
    vertx.eventBus().<JsonObject>consumer(MongoFetchVerticle.MONGODB_ADDRESS, message -> dataFromDb = message.body());
    context.response()
      .putHeader("Content-Type", "application/json")
      .setStatusCode(200)
      .end(dataFromDb.encodePrettily());
    System.out.println("Received data from DB: " + dataFromDb);
  }
}
