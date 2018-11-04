import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class MainReactive {

    public static void main(String[] args) throws Exception {
        Map<String, Integer> countWords = new ConcurrentHashMap<>(getVocabulary().stream()
                .collect(Collectors.toMap(Function.identity(), s -> 0)));

        List<String> urls = EngadgetAddresses.load();

        ExecutorService executor = Executors.newFixedThreadPool(25);

        countWords(countWords, urls, executor, new MyUrlReader())
                .subscribe(e -> System.out.println(e.getT1() + " " + e.getT2() + ", "));

        executor.awaitTermination(5L, TimeUnit.MINUTES);

        //TODO: Find the number of occurrences of each word in the vocabulary and print the result.
    }

    static Flux<Tuple2<String,Long>> countWords(Map<String, Integer> wordCount, List<String> urls,
                                                   ExecutorService urlExecutor, MyReader mr) throws Exception {
        return Flux.defer(() -> Flux.fromIterable(urls))
                .flatMap(url -> Flux.fromStream(mr.readLines(url)))
                .subscribeOn(Schedulers.fromExecutor(urlExecutor))
                .flatMap(line -> Flux.fromArray(line.split(" ")))
                .filter(wordCount::containsKey)
                .map(s -> Tuples.of(s, 1))
                .groupBy(Tuple2::getT1, Tuple2::getT2)
                .flatMap(group -> Mono.zip(Mono.just(group.key()), group.count()));
    }

    private static Set<String> getVocabulary() {
        try {
            return EnDictionary.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
