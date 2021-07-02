package edu.fpt.swp391.g2.imageexp.config;

import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.BaseConfigPath;
import me.hsgamer.hscore.config.PathableConfig;
import me.hsgamer.hscore.config.path.BooleanConfigPath;
import me.hsgamer.hscore.config.path.IntegerConfigPath;
import me.hsgamer.hscore.config.path.StringConfigPath;
import me.hsgamer.hscore.config.simpleconfiguration.SimpleConfig;
import org.simpleyaml.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * The main config, which creates the "config.yml" file
 */
public class MainConfig extends PathableConfig {
    public static final IntegerConfigPath SERVER_PORT = new IntegerConfigPath("server.port", 8080);
    public static final StringConfigPath SERVER_SECRET_KEY = new StringConfigPath("server.secret-key", "d7sTPQBxmSv8OmHdgjS5");
    public static final BooleanConfigPath DATABASE_MYSQL = new BooleanConfigPath("database.use-mysql", false);
    public static final BooleanConfigPath DATABASE_FIRST_LOAD = new BooleanConfigPath("database.first-load", true);
    public static final StringConfigPath DATABASE_HOST = new StringConfigPath("database.host", "localhost");
    public static final StringConfigPath DATABASE_PORT = new StringConfigPath("database.port", "3306");
    public static final StringConfigPath DATABASE_DB_NAME = new StringConfigPath("database.db-name", "imageexp");
    public static final StringConfigPath DATABASE_USERNAME = new StringConfigPath("database.username", "root");
    public static final StringConfigPath DATABASE_PASSWORD = new StringConfigPath("database.password", "");
    public static final BooleanConfigPath OPTION_AUTO_ASSIGN_NAME = new BooleanConfigPath("option.auto-assign-name-to-new-user", false);
    public static final StringConfigPath EMAIL_USERNAME = new StringConfigPath("email.username", "");
    public static final StringConfigPath EMAIL_PASSWORD = new StringConfigPath("email.password", "");
    public static final BooleanConfigPath EMAIL_VERIFICATION_TITLE_SEND_ON_REGISTER = new BooleanConfigPath("email.verification.send-on-register", false);
    public static final StringConfigPath EMAIL_VERIFICATION_TITLE = new StringConfigPath("email.verification.title", "Verify Code For ImageExp");
    public static final BaseConfigPath<List<String>> EMAIL_VERIFICATION_BODY = new BaseConfigPath<>(
            "email.verification.body",
            Collections.singletonList("Registered successfully. Please verify your account using this code: {code}"),
            o -> CollectionUtils.createStringListFromObject(o, false)
    );

    public MainConfig() {
        super(new SimpleConfig(new File("config.yml"), YamlConfiguration::loadConfiguration));
    }
}