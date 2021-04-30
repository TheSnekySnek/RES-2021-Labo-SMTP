package ch.heigvd.res.lab4.loader;

import ch.heigvd.res.lab4.model.Config;
import ch.heigvd.res.lab4.model.Group;
import ch.heigvd.res.lab4.model.Message;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Loads the config from the file to a Config object
 */
public class ConfigLoader {

    /**
     * Loads the config from the file to a Config object
     * @param path Path of the config file
     * @return Config object
     * @throws IOException If the file doesn't exist
     */
    public static Config loadConfig(String path) throws IOException{
        // Open the config file as a Json object
        String settingsString = new String(Files.readAllBytes(Paths.get(path)));
        JsonObject settings = new JsonParser().parse(settingsString).getAsJsonObject();

        // Create a new Config instance
        Config config = new Config(
                settings.get("addressFile").getAsString(),
                settings.get("smtpHost").getAsString(),
                settings.get("smtpPort").getAsInt(),
                settings.get("useAuth").getAsBoolean(),
                settings.get("username").getAsString(),
                settings.get("password").getAsString()
        );

        // Add the groups to the config
        JsonArray groups = settings.getAsJsonArray("groups");
        for(int i = 0; i < groups.size(); i++){
            JsonObject group = groups.get(i).getAsJsonObject();
            config.addGroup(new Group(group.get("sender").getAsString(), group.get("senderName").getAsString(), group.get("size").getAsInt()));
        }

        // Add the messages to the config
        JsonArray messages = settings.getAsJsonArray("messages");
        for(int i = 0; i < messages.size(); i++){
            JsonObject message = messages.get(i).getAsJsonObject();
            String subject = message.get("subject").getAsString();
            String body = message.get("body").getAsString();
            config.addMessage(new Message(subject, body));
        }

        return config;
    }
}
