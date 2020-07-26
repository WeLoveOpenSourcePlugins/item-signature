package de.paul2708.signature.configuration;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public final class PluginConfiguration {

    private static final String CONFIG_FILE = "config.yml";

    private final JavaPlugin plugin;
    private YamlConfiguration configuration;

    public PluginConfiguration(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.saveResource(CONFIG_FILE, false);

        configuration = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), CONFIG_FILE));
    }

    public String getPermission(String key) {
        return configuration.getString("permissions." + key);
    }

    public String getMessage(String key) {
        String configMessage = configuration.getString("messages.prefix") + configuration.getString("messages." + key);
        return translateColorCodes(configMessage);
    }

    public List<String> getFormat() {
        return configuration.getStringList("format")
                .stream()
                .map(this::translateColorCodes)
                .collect(Collectors.toUnmodifiableList());
    }

    private String translateColorCodes(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}