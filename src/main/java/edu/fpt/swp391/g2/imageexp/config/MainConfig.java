package edu.fpt.swp391.g2.imageexp.config;

import me.hsgamer.hscore.config.PathableConfig;
import me.hsgamer.hscore.config.path.IntegerConfigPath;
import me.hsgamer.hscore.config.path.StringConfigPath;
import me.hsgamer.hscore.config.simpleconfiguration.SimpleConfig;
import org.simpleyaml.configuration.file.YamlConfiguration;

import java.io.File;

public class MainConfig extends PathableConfig {
    public static final StringConfigPath SERVER_IP = new StringConfigPath("server.ip", "");
    public static final IntegerConfigPath SERVER_PORT = new IntegerConfigPath("server.port", 8000);

    public MainConfig() {
        super(new SimpleConfig(new File("config.yml"), YamlConfiguration::loadConfiguration));
    }
}
