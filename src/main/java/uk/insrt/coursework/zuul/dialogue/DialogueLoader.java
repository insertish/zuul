package uk.insrt.coursework.zuul.dialogue;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.moandjiezana.toml.Toml;

import uk.insrt.coursework.zuul.content.campaign.Localisation;

public class DialogueLoader {
    private Localisation locale;
    private Map<String, Object> data;

    public DialogueLoader(Localisation locale) {
        this.locale = locale;
        this.data = new HashMap<>();
    }

    public void load(String path) throws IOException {
        InputStream stream = DialogueLoader.class.getResourceAsStream(path);
        this.data = new Toml().read(stream).toMap();
    }

    @SuppressWarnings("unchecked")
    public void populate(Dialogue<String> dialogue, String key) {
        Map<String, Object> nodes = (Map<String, Object>) this.data.get(key);

        // We can't do string equality with Map keys so
        // instead we scan all the keys to find the key.
        String prefix = "";
        for (String nodeKey : nodes.keySet()) {
            if (nodeKey.equals("_prefix")) {
                prefix = (String) nodes.get(nodeKey);
            } else if (nodeKey.equals("_start")) {
                dialogue.setNode((String) nodes.get(nodeKey));
            }
        }

        for (Entry<String, Object> node : nodes.entrySet()) {
            String nodeKey = node.getKey();
            if (nodeKey.startsWith("_")) continue;

            Map<String, Object> values = (Map<String, Object>) node.getValue();

            String description = this.locale.get(prefix + (String) values.get("description"));
            List<Map<String, Object>> options = (List<Map<String, Object>>) values.get("options");

            DialogueNode<String> dialogueNode = new DialogueNode<>(description);
            for (Map<String, Object> object : options) {
                String desc = this.locale.get(prefix + (String) object.get("description"));
                String to = (String) object.get("to");
                Boolean mustExit = (Boolean) object.get("mustExit");

                if (mustExit == null) {
                    dialogueNode.addOption(desc, to);
                } else {
                    dialogueNode.addOption(desc, to, mustExit);
                }
            }

            dialogue.addPart(nodeKey, dialogueNode);
        }
    }
}
