package com.andi.reactivespring;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {

    @Test
    public void infiniteSequence() {
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200))
                .log();

        infiniteFlux.subscribe(s -> System.out.println("value: " + s));
    }
}
