package de.ugurkartal.starter;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.bson.json.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
class MongoFetchVerticleTest {

  /*private Vertx vertx;

  @BeforeEach
  void deploy_verticle(VertxTestContext testContext) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new MongoFetchVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }*/

  @Test
  void fetch_data_from_db_and_send(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MongoFetchVerticle(), testContext.succeeding(id -> {
      vertx.eventBus().<JsonObject>consumer(MongoFetchVerticle.MONGODB_ADDRESS, message -> {
        assertNotNull(message.body());
        testContext.completeNow();
      });
    }));
  }
}
