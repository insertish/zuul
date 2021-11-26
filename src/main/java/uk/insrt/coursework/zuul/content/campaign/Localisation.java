package uk.insrt.coursework.zuul.content.campaign;

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

    public void loadLocale(String locale) {
        try {
            InputStream stream = this.getClass().getResourceAsStream("/locale/" + locale + ".toml");
            this.map = new Toml().read(stream).toMap();
        } catch (Exception e) {
            System.err.println("Failed to load any resources for terminal view!");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public String get(String... path) {
        if (path.length == 0) return "<empty string>";

        var index = 1;
        var node = this.map.get(path[0]);
        while (index != path.length) {
            Map<String, Object> map = (Map<String, Object>) node;
            node = map.get(path[index++]);
        }

        if (node != null) {
            return (String) node;
        }

        return "<" + Arrays.asList(path).stream().collect(Collectors.joining(".")) + ">";
    }
}
