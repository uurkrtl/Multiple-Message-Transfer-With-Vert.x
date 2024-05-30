package de.ugurkartal.starter;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
class HttpServerVerticleTest {

  private Vertx vertx;

  @BeforeEach
  void deploy_verticle(VertxTestContext testContext) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new HttpServerVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void should_return_data_from_db(VertxTestContext testContext) {
    vertx.createHttpClient().request(HttpMethod.GET, 8080, "localhost", "/from-db")
      .compose(req -> req.send().compose(HttpClientResponse::body))
      .onSuccess(body -> {
        JsonObject data = new JsonObject(body.toString());
        assertNotNull(data);
        testContext.completeNow();
      })
      .onFailure(testContext::failNow);
  }

  @Test
  void should_return_data_from_api(VertxTestContext testContext) {
    vertx.createHttpClient().request(HttpMethod.GET, 8080, "localhost", "/from-api")
      .compose(req -> req.send().compose(HttpClientResponse::body))
      .onSuccess(body -> {
        JsonObject data = new JsonObject(body.toString());
        assertNotNull(data);
        testContext.completeNow();
      })
      .onFailure(testContext::failNow);
  }

  @Test
  void should_return_data_from_constant(VertxTestContext testContext) {
    vertx.createHttpClient().request(HttpMethod.GET, 8080, "localhost", "/from-constant")
      .compose(req -> req.send().compose(HttpClientResponse::body))
      .onSuccess(body -> {
        JsonObject data = new JsonObject(body.toString());
        assertNotNull(data);
        testContext.completeNow();
      })
      .onFailure(testContext::failNow);
  }
}
