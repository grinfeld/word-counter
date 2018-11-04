import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void countWordsOnce() {

        Set<String> voc = new HashSet<>(Arrays.asList("world", "life"));

        Map<String, Integer> result = Main.countWords(voc, Arrays.asList("stam"),
                path -> Stream.of("Hello world"));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(new Integer(1), result.get("world"));
        assertNull(result.get("hello"));
        assertNull(result.get("other"));
    }

    @Test
    void countWordsExistMany() {

        Set<String> voc = new HashSet<>(Arrays.asList("world", "life"));

        Map<String, Integer> result = Main.countWords(voc, Arrays.asList("stam"),
                path -> Stream.of("Hello world", "The big world", "The last world life"));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(new Integer(3), result.get("world"));
        assertEquals(new Integer(1), result.get("life"));
        assertNull(result.get("hello"));
        assertNull(result.get("other"));
    }

}