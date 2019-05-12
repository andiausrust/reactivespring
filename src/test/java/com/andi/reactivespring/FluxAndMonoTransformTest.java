package com.andi.reactivespring;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformTest {

    List<String> namesList = Arrays.asList("anna", "lang", "jenny", "johnr");

    @Test
    public void transformUsingFlatMap(){
        Flux<String> names = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .flatMap(s -> Flux.fromIterable(convertToList(s))).log();

        StepVerifier.create(names)
                .expectNextCount(10)
                .verifyComplete();


    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }

    @Test
    public void transformUsingParallel(){
        Flux<String> names = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .window(2)
                .flatMap((s) ->
                        s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(names)
                .expectNextCount(10)
                .verifyComplete();


    }

}
