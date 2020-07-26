package de.paul2708.signature;

import de.paul2708.signature.configuration.PluginConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignaturePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginConfiguration configuration = new PluginConfiguration(this);
        configuration.load();
    }
}