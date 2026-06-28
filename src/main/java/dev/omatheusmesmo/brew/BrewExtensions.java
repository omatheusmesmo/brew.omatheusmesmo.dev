package dev.omatheusmesmo.brew;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.quarkus.qute.TemplateExtension;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@TemplateExtension
public class BrewExtensions {

    private static final Set<String> VALID_TYPES = Set.of("highlight", "link", "nerd");

    private static final Map<String, String> VALID_NAMES = Map.ofEntries(
        Map.entry("Highlight", "🔥"),
        Map.entry("Articles", "📚"),
        Map.entry("Tools", "🛠"),
        Map.entry("Platforms", "☁️"),
        Map.entry("Languages & Frameworks", "💻"),
        Map.entry("Books Insights", "📖"),
        Map.entry("Videos", "🎬"),
        Map.entry("Nerd Corner", "🧠")
    );

    static List<JsonObject> validateSections(JsonObject data) {
        JsonArray sections = data.getJsonArray("sections");
        if (sections == null || sections.isEmpty()) {
            throw new IllegalArgumentException("data/brews: must have at least one section");
        }

        List<JsonObject> result = new ArrayList<>();

        for (int i = 0; i < sections.size(); i++) {
            JsonObject section = sections.getJsonObject(i);
            String name = section.getString("name", "section[" + i + "]");
            String type = section.getString("type");

            if (!VALID_NAMES.containsKey(name)) {
                throw new IllegalArgumentException(
                    "\"" + name + "\" is not a recognized section name. Must be one of: " + VALID_NAMES.keySet()
                    + ". Add it to BrewExtensions.java and web/app.css if it is new.");
            }

            if (type == null) {
                throw new IllegalArgumentException(
                    "\"" + name + "\" is missing required field 'type'. Must be one of: " + VALID_TYPES);
            }
            if (!VALID_TYPES.contains(type)) {
                throw new IllegalArgumentException(
                    "\"" + name + "\" has invalid type \"" + type + "\". Must be one of: " + VALID_TYPES);
            }

            JsonArray rawCards = section.getJsonArray("cards");
            if (rawCards == null || rawCards.isEmpty()) {
                throw new IllegalArgumentException(
                    "\"" + name + "\" must have at least one card");
            }

            for (int j = 0; j < rawCards.size(); j++) {
                JsonObject card = rawCards.getJsonObject(j);
                String label = card.getString("title", "card[" + j + "]");

                if (!card.containsKey("title")) {
                    throw new IllegalArgumentException(
                        "card[" + j + "] in \"" + name + "\" is missing required field 'title'");
                }

                if ("nerd".equals(type) && !card.containsKey("text")) {
                    throw new IllegalArgumentException(
                        "\"" + label + "\" in \"" + name + "\" (type=nerd) is missing required field 'text'");
                }
                if (!"nerd".equals(type) && !card.containsKey("description")) {
                    throw new IllegalArgumentException(
                        "\"" + label + "\" in \"" + name + "\" is missing required field 'description'");
                }
            }

            result.add(section);
        }

        return result;
    }

    static List<JsonObject> cards(JsonObject section) {
        JsonArray arr = section.getJsonArray("cards");
        List<JsonObject> result = new ArrayList<>();
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                result.add(arr.getJsonObject(i));
            }
        }
        return result;
    }
}
