package com.muhdfdeen.junction.config;

import java.io.File;
import com.muhdfdeen.junction.config.MainConfig.MainConfiguration;
import com.muhdfdeen.junction.config.MessageConfig.MessageConfiguration;

public class ConfigManager {
    private final File dataFolder;

    private MainConfiguration mainConfig;
    private MessageConfiguration messageConfig;

    public ConfigManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void load() {
        this.mainConfig = MainConfig.load(dataFolder);
        this.messageConfig = MessageConfig.load(dataFolder);
    }

    public MainConfiguration getMainConfig() {
        return mainConfig;
    }

    public MessageConfiguration getMessageConfig() {
        return messageConfig;
    }
}
