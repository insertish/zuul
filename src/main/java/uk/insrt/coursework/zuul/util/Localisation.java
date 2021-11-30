package uk.insrt.coursework.zuul.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.moandjiezana.toml.Toml;

/**
 * This class provides localisation capabilities for the game.
 */
public class Localisation {
    private Map<String, Object> map;

    /**
     * Construct a new instance of Localisation.
     */
    public Localisation() {
        this.map = new HashMap<>();
    }

    /**
     * Load a certain locale by name.
     * @param locale The target locale to load
     * @throws IOException if the locale does not exist in resources
     */
    public void loadLocale(String locale) throws IOException {
        InputStream stream = this
            .getClass()
            .getResourceAsStream("/locale/" + locale + ".toml");
        
        this.map = new Toml().read(stream).toMap();
    }

    /**
     * Given a path of keys, find the value at the end of the path.
     * 
     * Unchecked errors are supressed as they would only occur if the
     * developer provides an incorrect data structure, in that case the
     * error will be emitted from within this method. It is not a critical
     * error but it should be handled immediately.
     * @param path Path to value we want
     * @return The value at the given path
     */
    @SuppressWarnings("unchecked")
    public String from(String... path) {
        if (path.length == 0) return "<empty string>";

        try {
            var index = 1;
            var node = this.map.get(path[0]);
            while (index != path.length) {
                Map<String, Object> map = (Map<String, Object>) node;
                node = map.get(path[index++]);
            }

            if (node != null) {
                return (String) node;
            }
        } catch (Exception e) {
            // We don't want this to be a fatal error,
            // we instead return the original template.
        }

        return "<" + Arrays.asList(path).stream().collect(Collectors.joining(".")) + ">";
    }

    /**
     * Given a path, find the value at the end of the path.
     * @param path Path to value we want, keys separated by period
     * @return The value at the given path
     */
    public String get(String path) {
        return this.from(path.split("\\."));
    }
}
