import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public class MyUrlReader implements MyReader {

    static BufferedReader getBufferedReader(URL url) {
        try {
            return new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stream from " + url.getPath());
        }
    }

    static URL createUrl(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<String> readLines(String path) {
        URL url = createUrl(path);
        return getBufferedReader(url).lines();
    }
}
