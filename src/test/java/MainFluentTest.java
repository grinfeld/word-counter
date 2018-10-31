import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainFluentTest {

    @Test
    void getStringIntegerMapExistsOnce() {

        Set<String> voc = new HashSet<>(Arrays.asList("world", "life"));

        Map<String, Integer> result = Main.getStringIntegerMap(voc, Arrays.asList("stam"),
                path -> Stream.of("Hello world"));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(new Integer(1), result.get("world"));
        assertNull(result.get("hello"));
        assertNull(result.get("other"));
    }

    @Test
    void getStringIntegerMapExistMany() {

        Set<String> voc = new HashSet<>(Arrays.asList("world", "life"));

        Map<String, Integer> result = Main.getStringIntegerMap(voc, Arrays.asList("stam"),
                path -> Stream.of("Hello world", "The big world", "The last world"));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(new Integer(3), result.get("world"));
        assertNull(result.get("hello"));
        assertNull(result.get("other"));
    }

}