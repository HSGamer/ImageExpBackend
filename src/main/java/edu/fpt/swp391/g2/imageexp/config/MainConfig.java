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
    public static final StringConfigPath DATABASE_HOST = new StringConfigPath("database.host", "localhost");
    public static final StringConfigPath DATABASE_PORT = new StringConfigPath("database.port", "3306");
    public static final StringConfigPath DATABASE_DB_NAME = new StringConfigPath("database.db-name", "imageexp");
    public static final StringConfigPath DATABASE_USERNAME = new StringConfigPath("database.username", "root");
    public static final StringConfigPath DATABASE_PASSWORD = new StringConfigPath("database.password", "");

    public MainConfig() {
        super(new SimpleConfig(new File("config.yml"), YamlConfiguration::loadConfiguration));
    }
}
