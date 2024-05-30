package de.ugurkartal.starter;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.bson.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
class ApiFetchVerticleTest {

  private Vertx vertx;

  @BeforeEach
  void deploy_verticle(VertxTestContext testContext) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new ApiFetchVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void fetch_data_from_api_and_send(VertxTestContext testContext) {
      vertx.eventBus().<JsonObject>consumer(ApiFetchVerticle.API_ADDRESS, message -> {
        assertNotNull(message.body());
        testContext.completeNow();
      });
  }
}
