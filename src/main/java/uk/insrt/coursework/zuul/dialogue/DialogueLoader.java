package uk.insrt.coursework.zuul.dialogue;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.moandjiezana.toml.Toml;

/**
 * This is a helper class for loading and populating {@link Dialogue}.
 * This DialogueLoader assumes that i18n is being used in the dialogue data.
 */
public class DialogueLoader {
    private Map<String, Object> data;

    /**
     * Construct a new DialogueLoader
     */
    public DialogueLoader() {
        this.data = new HashMap<>();
    }

    /**
     * Load all necessary data for populating Dialogue.
     * @param path Path to the dialogue resource file
     * @throws IOException if we can't read the dialogue file
     */
    public void load(String path) throws IOException {
        InputStream stream = DialogueLoader.class.getResourceAsStream(path);
        this.data = new Toml().read(stream).toMap();
    }

    /**
     * Populate a Dialogue system using a specific dialog definition represented by a given key.
     * 
     * This method is unchecked as we expect a valid data structure to have
     * been loaded from the resource, this should be verified by the developer.
     * @param dialogue Dialogue system
     * @param key Key to lookup
     */
    @SuppressWarnings("unchecked")
    public void populate(Dialogue<String> dialogue, String key) {
        Map<String, Object> nodes = (Map<String, Object>) this.data.get(key);

        // Process any special keys first before we continue.
        String prefix = "";
        for (String nodeKey : nodes.keySet()) {
            if (nodeKey.equals("_prefix")) {
                prefix = (String) nodes.get(nodeKey);
            } else if (nodeKey.equals("_start")) {
                dialogue.setNode((String) nodes.get(nodeKey));
            }
        }

        for (Entry<String, Object> node : nodes.entrySet()) {
            // Ignore any keys starting with _, as they are used above.
            String nodeKey = node.getKey();
            if (nodeKey.startsWith("_")) continue;

            // Read each node's values and find the description and options.
            Map<String, Object> values = (Map<String, Object>) node.getValue();

            // Description strings are assumed to be i18n paths.
            String description = "<" + prefix + (String) values.get("description") + ">";
            List<Map<String, Object>> options = (List<Map<String, Object>>) values.get("options");

            // Construct a new Dialogue Node with the given data.
            DialogueNode<String> dialogueNode = new DialogueNode<>(description);
            for (Map<String, Object> object : options) {
                String desc = "<" + prefix + (String) object.get("description") + ">";
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
