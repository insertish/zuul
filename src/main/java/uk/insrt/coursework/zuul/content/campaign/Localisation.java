package uk.insrt.coursework.zuul.content.campaign;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.moandjiezana.toml.Toml;

public class Localisation {
    private Map<String, Object> map;

    public Localisation() {
        this.map = new HashMap<>();
    }

    public void loadLocale(String locale) throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/locale/" + locale + ".toml");
        this.map = new Toml().read(stream).toMap();
    }

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
            e.printStackTrace();
            // We don't want this to be a fatal error
            // but it should be something that the developer
            // should handle immediately.
        }

        return "<" + Arrays.asList(path).stream().collect(Collectors.joining(".")) + ">";
    }

    public String get(String path) {
        return this.from(path.split("\\."));
    }
}
