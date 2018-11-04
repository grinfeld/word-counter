import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        // load vocabulary and urls from file
        Set<String> vocabulary = EnDictionary.load();
        List<String> urls = EngadgetAddresses.load();

        Map<String, Integer> wordCount = countWords(vocabulary, urls, new MyUrlReader());

        wordCount.entrySet().stream().filter(e -> e.getValue() > 0).forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
        //TODO: Find the number of occurrences of each word in the vocabulary and print the result.
    }

    static Map<String, Integer> countWords(Set<String> vocabulary,
                                           List<String> urls, MyReader mr) {
        Map<String, Integer> wordCount = new ConcurrentHashMap<>(vocabulary.stream()
                .collect(Collectors.toMap(Function.identity(), s -> 0)));

        urls.stream()
                .parallel()
                .flatMap(mr::readLines)
                .flatMap(l -> Stream.of(l.split(" ")))
                .forEach(w -> wordCount.computeIfPresent(w, (s, i) -> i + 1))
        ;
        return wordCount;
    }
}
