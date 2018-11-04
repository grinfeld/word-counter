import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
class MainReactiveTest {


    @Test
    void countWordsTestOnce() throws Exception {
        Map<String, Integer> voc =
                new HashSet<>(Arrays.asList("world", "life")).stream()
                        .collect(Collectors.toMap(Function.identity(), s -> 0));


        //Flux<Tuple2<String, Integer>> result =
        Flux<Tuple2<String, Long>> result = MainReactive.countWords(voc, Arrays.asList("1"),
                Executors.newSingleThreadExecutor(), path -> Stream.of("Hello world"));

        StepVerifier.create(result)
                .expectNext(Tuples.of("world", 1L))
                .verifyComplete();

        System.out.println();

    }

    @Test
    void countWordsTestMany() throws Exception {
        Map<String, Integer> voc =
                new HashSet<>(Arrays.asList("world", "life")).stream()
                        .collect(Collectors.toMap(Function.identity(), s -> 0));


        //Flux<Tuple2<String, Integer>> result =
        Flux<Tuple2<String, Long>> result = MainReactive.countWords(voc, Arrays.asList("1"),
                Executors.newSingleThreadExecutor(), path -> Stream.of("Hello world", "The big world", "The last world life"));

        StepVerifier.create(result)
                .expectNext(Tuples.of("world", 3L))
                .expectNext(Tuples.of("life", 1L))
                .verifyComplete();

        System.out.println();

    }

}