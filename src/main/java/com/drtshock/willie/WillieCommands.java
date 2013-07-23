package com.drtshock.willie;

import com.drtshock.willie.util.YamlHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

public class WillieCommands {

    private static final Logger logger = Logger.getLogger(WillieConfig.class.getName());
    private LinkedHashMap<String, Object> commandsMap = new LinkedHashMap<>();

    public LinkedHashMap<String, Object> getCommandsMap() {
        return commandsMap;
    }

    public WillieCommands update() {
        return this;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save(String fileName) {
        update();
        try {

            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            try (PrintWriter printWriter = new PrintWriter(file)) {
                Yaml yml = new Yaml();
                printWriter.write(yml.dump(commandsMap));
            }
        } catch (FileNotFoundException ignored) {
            logger.log(Level.WARNING, "Could not create commands file at ''{0}''", fileName);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not write commands to file ''{0}''", fileName);
        }
    }

    public static WillieCommands loadFromFile(String fileName) {
        WillieCommands willieCommands = new WillieCommands();
        LinkedHashMap<String, Object> config = willieCommands.getCommandsMap();
        try {
            YamlHelper yml = new YamlHelper(fileName);

            for (Map.Entry<String, Object> entry : yml.getMap("").entrySet()) {
                config.put(entry.getKey(), entry.getValue());
            }
            willieCommands.update();

        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "Sorry, could not find commands file at: {0}", fileName);
            logger.info("Saving default commands file...");
        }
        willieCommands.save(fileName);
        return willieCommands;
    }

    public WillieCommands setCommand(String command, String output) {
        commandsMap.put(command, output);
        return this;
    }

    public WillieCommands delCommand(String command) {
        commandsMap.remove(command);
        return this;
    }

    public int countCommands() {
        return commandsMap.size();
    }
}
