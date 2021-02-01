package com.itskool;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;

public class Main {

    public static void main(String[] args) {
        VertxOptions vertxOptions = new VertxOptions();
        Vertx vertx = Vertx.vertx();
        VertxPrometheusOptions prometheusOptions = new VertxPrometheusOptions()
                .setPublishQuantiles(true)
                .setEnabled(true);
        MicrometerMetricsOptions metricsOptions = new MicrometerMetricsOptions()
                .setPrometheusOptions(prometheusOptions).setEnabled(true);

        vertxOptions.setMetricsOptions(metricsOptions);

        Vertx.vertx(vertxOptions).deployVerticle(new MetricsVerticle());
    }
}
