package uk.insrt.coursework.zuul.content.campaign;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.moandjiezana.toml.Toml;

public class Dialogue {
    private Map<String, Object> map;

    public Dialogue() {
        this.map = new HashMap<>();

        try {
            InputStream stream = this.getClass().getResourceAsStream("/dialogue.toml");
            this.map = new Toml().read(stream).toMap();
        } catch (Exception e) {
            System.err.println("Failed to load any resources for terminal view!");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public String get(String ns, String key) {
        var map = (Map<String, String>) this.map.get(ns);
        if (map != null) {
            var value = map.get(key);
            if (value != null) {
                return value;
            }
        }

        return "<" + ns + "." + key + ">";
    }
}
