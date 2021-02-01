package com.itskool;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.micrometer.PrometheusScrapingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricsVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(MetricsVerticle.class);


    @Override
    public void start(Promise<Void> promise) throws Exception {
        Router router = Router.router(vertx);
        router.get("/data")
                .handler((RoutingContext context) -> this.handleRequest(context));

        router.route("/metrics")
                .handler(context -> {
                    logger.info("Collecting metrics");
                    context.next();
                })
                .handler(event -> PrometheusScrapingHandler.create());

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, ar -> {
                    if (ar.succeeded()) {
                        logger.info("Server running on port 8080");
                    } else {
                        logger.error("Error starting the server");
                    }
                });
    }

    private void handleRequest(RoutingContext context) {
        JsonObject payload = new JsonObject().put("greeting", "Hello World");
        context.response()
                .putHeader("Content-Type", "application/json")
                .end(payload.encode());

    }
}
