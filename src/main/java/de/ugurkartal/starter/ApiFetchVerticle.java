package de.ugurkartal.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

public class ApiFetchVerticle extends AbstractVerticle {
  private HttpClient httpClient;
  private final String apiUrl = "jsonplaceholder.typicode.com";
  private final String pathVariable = "/users/1";
  public static final String API_ADDRESS = "api.address";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    httpClient = vertx.createHttpClient();
    vertx.setPeriodic(3000, id -> fetchDataFromApiAndSend());
    startPromise.complete();
  }

  private void fetchDataFromApiAndSend() {
    httpClient.request(HttpMethod.GET, apiUrl, pathVariable)
      .compose(req -> req.send().compose(HttpClientResponse::body))
      .onSuccess(buffer -> vertx.eventBus().publish(API_ADDRESS, buffer.toJsonObject()))
      .onFailure(Throwable::printStackTrace);
  }
}
