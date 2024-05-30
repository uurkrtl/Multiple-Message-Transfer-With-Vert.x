package de.ugurkartal.starter;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
class ConstantFetchVerticleTest {

  private Vertx vertx;

  @BeforeEach
  void deploy_verticle(VertxTestContext testContext) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new ConstantFetchVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void should_publish_constant_data(VertxTestContext testContext) {
    vertx.eventBus().<JsonObject>consumer(ConstantFetchVerticle.CONSTANT_ADDRESS, message -> {
      JsonObject data = message.body();
      assertNotNull(data);
      assertEquals("Ugur", data.getString("firstName"));
      assertEquals("Kartal", data.getString("lastName"));
      assertNotNull(data.getString("id"));
      testContext.completeNow();
    });
  }

}
