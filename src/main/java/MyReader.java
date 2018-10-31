import java.util.stream.Stream;

public interface MyReader {

    Stream<String> readLines(String path);

}
